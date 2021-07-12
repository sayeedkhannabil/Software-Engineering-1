package comp3350.grs.business;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;
import comp3350.grs.persistence.DataAccessObject;
import comp3350.grs.persistence.DataAccessStub;


public class AccessUsersTest {
    private static AccessUsers accessUsers;
    private User user,user1,user2,user3;
    private boolean success;
    private List<User> userList;
    private String userID,password;

    @BeforeClass
    public static void beforeClass(){
        Services.closeDataAccess();
        Services.createDataAccess(new DataAccessStub(Main.dbName));
        accessUsers=new AccessUsers();
    }

    @Before
    public void before(){
        accessUsers.clear();
        user=null;
        user1=null;
        user2=null;
        user3=null;
        success=false;
        userList=null;
        userID=null;
        password=null;
    }

    @Test
    public void testTypical(){
        try {
            user1=new Guest();
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        AccessUsers.setActiveUser(user1);
        try {
            user2=new Guest();
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        user1=AccessUsers.getActiveUser();
        assertEquals(user1,user2);

        try {
            user1=new RegisteredUser("user1","password");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        AccessUsers.setActiveUser(user1);
        try {
            user2=new RegisteredUser("user1","password");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        user1=AccessUsers.getActiveUser();
        assertEquals(user1,user2);

        try {
            user1=new RegisteredUser("user1","password1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try {
            user2=new RegisteredUser("user2","password2");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try {
            user3=new RegisteredUser("user3","password3");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= accessUsers.insertUser(user1);
        assertTrue(success);
        userList=accessUsers.getAllUsers();
        assertEquals(1,userList.size());
        success= accessUsers.insertUser(user2);
        assertTrue(success);
        userList=accessUsers.getAllUsers();
        assertEquals(2,userList.size());
        success= accessUsers.insertUser(user3);
        assertTrue(success);
        userList=accessUsers.getAllUsers();
        assertEquals(3,userList.size());
        userList=new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        user= accessUsers.getSequential();
        int counter=0;
        while (user!=null){
            counter++;
            assertTrue(userList.contains(user));
            user= accessUsers.getSequential();
        }
        assertTrue(counter==3);
        user= accessUsers.getUserByID("user2");
        RegisteredUser regUser=(RegisteredUser)user;
        userID= regUser.getUserID();
        password= regUser.getPassword();
        assertEquals(userID,"user2");
        assertEquals(password,"password2");

        try {
            user2=new RegisteredUser("user2","newPassword");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= accessUsers.updateUser(user2);
        assertTrue(success);
        user= accessUsers.getUserByID("user2");
        regUser=(RegisteredUser)user;
        userID= regUser.getUserID();
        password= regUser.getPassword();
        assertEquals(userID,"user2");
        assertEquals(password,"newPassword");

        success= accessUsers.deleteUser(user2);
        assertTrue(success);
        user= accessUsers.getUserByID("user2");
        assertNull(user);
        userList=accessUsers.getAllUsers();
        assertEquals(2,userList.size());
        success= accessUsers.deleteUser(user2);
        assertFalse(success);
        userList=accessUsers.getAllUsers();
        assertEquals(2,userList.size());

        counter=0;
        user= accessUsers.getSequential();
        while (user!=null){
            counter++;
            assertFalse(user.equals(user2));
            user= accessUsers.getSequential();
        }
        assertEquals(counter,2);

        accessUsers.clear();
        userList=accessUsers.getAllUsers();
        assertEquals(userList.size(),0);
    }

    @Test
    public void testNull(){
        try {
            user=new Guest();
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        AccessUsers.setActiveUser(user);
        try {
            AccessUsers.setActiveUser(null);
            fail("should throw NPE");
        }catch (NullPointerException e){
            assertTrue(true);
        }
        user=AccessUsers.getActiveUser();
        assertNotNull(user);
        success= accessUsers.insertUser(null);
        assertFalse(success);
        success= accessUsers.updateUser(null);
        assertFalse(success);
        success= accessUsers.deleteUser(null);
        assertFalse(success);
        user= accessUsers.getUserByID(null);
        assertNull(user);

        try {
            user=new RegisteredUser("user");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        success= accessUsers.insertUser(user);
        assertFalse(success);
        success= accessUsers.updateUser(user);
        assertFalse(success);
        success= accessUsers.deleteUser(user);
        assertFalse(success);
        user= accessUsers.getUserByID("user");
        assertNull(user);

        accessUsers.clear();
        try {
            user1=new RegisteredUser("user1","pass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try {
            user2=new RegisteredUser("user2","pass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        try {
            user3=new RegisteredUser("usea","pass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessUsers.insertUser(user1);
        accessUsers.insertUser(user2);
        accessUsers.insertUser(user3);
        userList=accessUsers.getUsersByIDImplicit("user");
        assertEquals(2,userList.size());
    }

    @Test
    public void testEdge(){
        try {
            user1=new Guest();
            user2=new RegisteredUser("user2","password2");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }


        success= accessUsers.insertUser(user1);
        assertTrue(success);
        success= accessUsers.insertUser(user1);
        assertFalse(success);
        success= accessUsers.deleteUser(user1);
        assertTrue(success);
        success= accessUsers.deleteUser(user1);
        assertFalse(success);
        success= accessUsers.insertUser(user1);
        assertTrue(success);

        accessUsers.clear();
        user= accessUsers.getSequential();
        assertNull(user);
        userList=accessUsers.getAllUsers();
        assertEquals(0,userList.size());
        success= accessUsers.insertUser(user1);
        assertTrue(success);
        accessUsers.getSequential();
        user= accessUsers.getSequential();
        assertEquals(user1,user);
        accessUsers.insertUser(user2);
        user= accessUsers.getSequential();
        assertNull(user);

        accessUsers.clear();
        accessUsers.insertUser(user1);
        user= accessUsers.getSequential();
        while (user!=null){
            user= accessUsers.getSequential();
        }
        user= accessUsers.getSequential();
        assertEquals(user1,user);
    }

    @AfterClass
    public static void AfterClass(){
        Services.closeDataAccess();
    }
}