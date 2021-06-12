package comp3350.grs.objects;

import org.junit.Test;

import comp3350.grs.R;

import static org.junit.Assert.*;

public class RegisteredUserTest {

    @Test
    public void testTypical() throws Exception {
        RegisteredUser user=new RegisteredUser("myUserID","myPass");
        assert(user.getUserID().equals("myUserID"));
        assert (user.toString().equals("type:registered user,userID:myUserID," +
                "password:myPass"));
        assert (user.equals(new RegisteredUser("myUserID")));
        assert (user.equals(new RegisteredUser("myUserID","anotherPass")));
        assertFalse(user.equals(new RegisteredUser("anotherUserID")));
        assert (user.validPass("myPass"));
        assertFalse(user.validPass("anotherPass"));
        assert (user.getPassword().equals("myPass"));


        user.changeUserID("anotherUserID");
        assert(user.getUserID().equals("anotherUserID"));
        assert (user.toString().equals("type:registered user,userID:anotherUserID," +
                "password:myPass"));
        assert (user.equals(new RegisteredUser("myUserID")));
        assert (user.equals(new RegisteredUser("anotherUserID","anotherPass")));
        assertFalse(user.equals(new RegisteredUser("myUserID")));
        assert (user.validPass("myPass"));
        assertFalse(user.validPass("anotherPass"));
        assert (user.getPassword().equals("myPass"));
    }

    @Test
    public void testNull(){
        RegisteredUser user=new RegisteredUser();
        assertNull(user.getUserID());
        assertFalse(user.equals(new RegisteredUser()));
        assert (user.toString().equals("type:registered user,userID:null," +
                "password:null"));
        assertFalse (user.validPass(null));
        assertFalse(user.validPass("myPass"));
        assertNull (user.getPassword());
    }

    @Test
    public void testOneLetter() throws Exception {
        RegisteredUser user=new RegisteredUser("u","p");
        assert (user.getUserID().equals("u"));
        assert (user.toString().equals("type:registered user,userID:u," +
                "password:p"));
        assert (user.equals(new RegisteredUser("u")));
        assertFalse(user.equals(new RegisteredUser("p")));
        assert (user.validPass("p"));
        assertFalse(user.validPass("u"));;
        assert (user.getPassword().equals("p"));
    }

    @Test
    public void testSpace() throws Exception {
        RegisteredUser user=new RegisteredUser(" "," ");
        RegisteredUser user2=new RegisteredUser("my user id","my pass");
        assertNull (user.getUserID());
        assertNull(user2.getUserID());
        assertFalse(user.equals(new RegisteredUser(" "," ")));
        assertFalse(user2.equals(new RegisteredUser("my user id","my pass")));
        assertFalse (user.validPass(" "));
        assertFalse(user2.validPass("my pass"));
        assertNull(user.getPassword());
        assertNull(user2.getPassword());
    }

    @Test
    public void testGuest(){
        RegisteredUser user= null;
        try {
            user = new RegisteredUser("Guest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNull(user.getUserID());
    }

}