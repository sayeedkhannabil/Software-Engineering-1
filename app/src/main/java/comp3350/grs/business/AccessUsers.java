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
import comp3350.grs.persistence.DataAccessStub;

public class AccessUsers {

    private DataAccessStub dataAccess;
    private List<User> users;
    private static User activeUser;//the user logged in to the app
    private User currentUser;//used for return sequantial user
    private int currentUserIndex;//index of current user

    public AccessUsers()
    {
        dataAccess = (DataAccessStub) Services.getDataAccess(Main.dbName);
        users = null;
        activeUser = null;
        currentUser=null;
        currentUserIndex = 0;
    }

    public static User getActiveUser(){
        return activeUser;
    }

    //set the user who logged in to be the given user
    public static void setActiveUser(User user){
        activeUser=user;
    }

    //get a list of all the users
    public List<User> getUsers()
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
    public User getRandom(String userID) throws Exception {
        currentUser = null;
        if (userID.trim().equals(""))
        {
            //System.out.println("*** Invalid student number");
        }
        else
        {
            currentUser = dataAccess.getUser(new RegisteredUser(userID));
        }
        return currentUser;
    }

    public void insertUser(User newUser)
    {
        if (newUser!=null&&newUser.validUser()){
            dataAccess.insertUser(newUser);
        }

    }

    public void updateUser(User user)
    {
        if (user!=null&&user.validUser()){
            dataAccess.updateUser(user);
        }

    }

    public void deleteUser(User user)
    {
        if (user!=null&&user.validUser()){
            dataAccess.deleteUser(user);
        }

    }
}
