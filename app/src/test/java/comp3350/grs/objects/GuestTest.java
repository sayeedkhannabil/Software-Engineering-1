package comp3350.grs.objects;

import junit.framework.TestCase;

import org.junit.Test;

import comp3350.grs.exceptions.IncorrectFormat;

import static org.junit.Assert.*;

public class GuestTest{

    @Test
    public void test() {
        Guest guest= null;
        try {
            guest = new Guest();
            assert (guest.getUserID().equals("Guest"));
            try {
                guest.changeUserID("userID");
                fail("shouldn't change userID");
            }catch (Exception e){
                assert (true);
            }
            assert (guest.equals(new Guest()));
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
    }
}