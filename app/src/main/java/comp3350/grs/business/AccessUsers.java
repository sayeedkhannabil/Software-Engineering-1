package comp3350.grs.business;

import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessStub;

public class AccessUsers {

    private DataAccessStub dataAccess;
    private List<User> users;
    private User currentUser;
    private int currentUserIndex;

    public AccessUsers()
    {
        dataAccess = (DataAccessStub) Services.getDataAccess(Main.dbName);
        users = null;
        currentUser = null;
        currentUserIndex = 0;
    }

    public List<User> getUsers()
    {
        users=dataAccess.getAllUsers();
        return users;
    }

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

    public User getRandom(String userID)
    {
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

    public void insertStudent(User newUser)
    {
        dataAccess.insertUser(newUser);
    }

    public void updateStudent(User currentUser)
    {
        dataAccess.updateUser(currentUser);
    }

    public void deleteStudent(User user)
    {
        dataAccess.deleteUser(user);
    }
}
