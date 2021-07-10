package comp3350.grs.objects;

import junit.framework.TestCase;

import org.junit.Test;
import static org.junit.Assert.*;
import comp3350.grs.exceptions.IncorrectFormat;

public class RegisteredUserTest extends TestCase {

    @Test
    public void testTypical() throws Exception {
        RegisteredUser user=new RegisteredUser("myUserID","myPass");
        assert (user.validUser());
        assert(user.getUserID().equals("myUserID"));
        assert (user.equals(new RegisteredUser("myUserID")));
        assert (user.equals(new RegisteredUser("myUserID","anotherPass")));
        assertFalse(user.equals(new RegisteredUser("anotherUserID")));
        try {
            user.checkPassMatch("myPass");
            assert (true);
        }catch (Exception e){
            fail();
        }
        try {
            user.checkPassMatch("anotherPass");
            fail();
        }catch (Exception e){
            assert (true);
        }
        assert (user.getPassword().equals("myPass"));

        user.changeUserID("anotherUserID");
        assert (user.validUser());
        assert(user.getUserID().equals("anotherUserID"));
        assert (user.equals(new RegisteredUser("anotherUserID","anotherPass")));
        assertFalse(user.equals(new RegisteredUser("myUserID")));
        try {
            user.checkPassMatch("myPass");
            assert (true);
        }catch (Exception e){
            fail();
        }
        try {
            user.checkPassMatch("anotherPass");
            fail();
        }catch (Exception e){
            assert (true);
        }
        assert (user.getPassword().equals("myPass"));
    }

    @Test
    public void testNull(){
        RegisteredUser user=new RegisteredUser();
        assertFalse (user.validUser());
        assertNull(user.getUserID());
        assertFalse(user.equals(new RegisteredUser()));
        try {
            user.checkPassMatch(null);
            fail();
        }catch (Exception e){
            assert (true);
        }
        try {
            user.checkPassMatch("myPass");
            fail();
        }catch (Exception e){
            assert (true);
        }
        assertNull (user.getPassword());
    }

    @Test
    public void testOneLetter() throws Exception {
        RegisteredUser user=new RegisteredUser("u","p");
        assert (user.validUser());
        assert (user.getUserID().equals("u"));
        assert (user.equals(new RegisteredUser("u")));
        assertFalse(user.equals(new RegisteredUser("p")));
        try {
            user.checkPassMatch("p");
            assert (true);
        }catch (Exception e){
            fail();
        }
        try {
            user.checkPassMatch("u");
            fail();
        }catch (Exception e){
            assert (true);
        }
        assert (user.getPassword().equals("p"));
    }

    @Test
    public void testSpace(){
        RegisteredUser user=null;
        RegisteredUser user2=null;
        try {
            user=new RegisteredUser(" "," ");
            fail();
        }catch (Exception e){
            assert (true);
        }
        try {
            user2=new RegisteredUser("my user id","my pass");
            fail();
        }catch (Exception e){
            assert (true);
        }

        assertNull(user);
        assertNull(user2);
    }

    @Test
    public void testGuest(){
        RegisteredUser user= null;
        try {
            user = new RegisteredUser("Guest");
            fail("should not be able to create with userid guest");
        } catch (IncorrectFormat e) {
            assert true;
        }
    }

}