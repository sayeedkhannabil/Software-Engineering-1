package comp3350.grs.business;
// CLASS: AccessUsers...
//
// Author: Shiqing
//
// REMARKS: What is the purpose of this class?
// access users
//-----------------------------------------
import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessStub;

public class AccessUsers {

    private DataAccessI dataAccess;
    private List<User> users;
    private static User activeUser;//the user logged in to the app
    private User currentUser;//used for return sequantial user
    private int currentUserIndex;//index of current user

    public AccessUsers()
    {
        dataAccess = Services.getDataAccess(Main.dbName);
        users = null;
        activeUser = null;
        currentUser=null;
        currentUserIndex = 0;
    }

    public void clear(){
        dataAccess.clearUsers();
    }

    public static User getActiveUser(){
        return activeUser;
    }

    //set the user who logged in to be the given user
    public static void setActiveUser(User user){
        if (user==null){
            throw new NullPointerException("can't set active user to be null");
        }
        activeUser=user;
    }

    //get a list of all the users
    public List<User> getAllUsers()
    {
        users=dataAccess.getAllUsers();
        return users;
    }

    //get users one by one
    public User getSequential()
    {
        if (users == null)
        {
            users = dataAccess.getAllUsers();
            currentUserIndex = 0;
        }
        if (currentUserIndex < users.size())
        {
            currentUser =  users.get(currentUserIndex);
            currentUserIndex++;
        }
        else
        {
            users = null;
            currentUser = null;
            currentUserIndex = 0;
        }
        return currentUser;
    }

    //get a specific user according to userID
    public User getUserByID(String userID){
        currentUser = dataAccess.getUserByID(userID);
        return currentUser;
    }

    public boolean insertUser(User newUser)
    {
        return dataAccess.insertUser(newUser);
    }

    public boolean updateUser(User user)
    {
        return dataAccess.updateUser(user);
    }

    public boolean deleteUser(User user)
    {
        return dataAccess.deleteUser(user);
    }
}
