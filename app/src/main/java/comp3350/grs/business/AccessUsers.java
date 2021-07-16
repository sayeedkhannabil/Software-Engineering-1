package comp3350.grs.business;

// access users business object

import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessI;

public class AccessUsers {

    private DataAccessI dataAccessI;
    private List<User> userList;
    private static User activeUser;//the user logged in to the app
    private User currentUser;//used for return sequantial user
    private int currentUserIndex;//index of current user

    public AccessUsers()
    {
        dataAccessI = Services.getDataAccess(Main.dbName);
        userList = null;
        activeUser = null;
        currentUser=null;
        currentUserIndex = 0;
    }

    public void clear(){
        dataAccessI.clearUsers();
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
        userList = dataAccessI.getAllUsers();
        return userList;
    }

    //get users one by one
    public User getSequential()
    {
        if (userList == null)
        {
            getAllUsers();
            currentUserIndex = 0;
        }
        if (currentUserIndex < userList.size())
        {
            currentUser =  userList.get(currentUserIndex);
            currentUserIndex++;
        }
        else
        {
            userList = null;
            currentUser = null;
            currentUserIndex = 0;
        }
        return currentUser;
    }

    //get a specific user according to userID
    public User getUserByID(String userID){
        currentUser = dataAccessI.getUserByID(userID);
        return currentUser;
    }

    public List<User> getUsersByIDImplicit(String userID){
        String userIDImp="%"+userID+"%";
        return dataAccessI.getUsersByIDImplicit(userIDImp);
    }

    public boolean insertUser(User newUser)
    {
        return dataAccessI.insertUser(newUser);
    }

    public boolean updateUser(User user)
    {
        return dataAccessI.updateUser(user);
    }

    public boolean deleteUser(User user)
    {
        return dataAccessI.deleteUser(user);
    }
}
