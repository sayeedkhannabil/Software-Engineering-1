package comp3350.grs.objects;

import junit.framework.TestCase;

import org.junit.Test;

import static org.junit.Assert.*;

public class GuestTest extends TestCase {

    @Test
    public void test() {
        Guest guest=new Guest();
        assert (guest.getUserID().equals("Guest"));
        assert (guest.toString().equals("type:guest,userID:Guest"));
        try {
            guest.changeUserID("userID");
            fail("shouldn't change userID");
        }catch (Exception e){
            assert (true);
        }
        assert (guest.equals(new Guest()));
    }
}