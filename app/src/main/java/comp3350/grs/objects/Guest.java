package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;

// CLASS: Guest
//
// Author: Shiqing Guo
//
// REMARKS: domain object of the user who will use this app, and don't want
// to sign up, referred to as "guest"
//
//-----------------------------------------
public class Guest extends User{

    //user id of a guest is "Guest"
    public Guest() throws IncorrectFormat {
        super("Guest");
    }

    @Override
    public String toString() {
        return "type:guest,"+ super.toString();
    }

    //a guest should not be able to change user id
    @Override
    public void changeUserID(String newID) throws IncorrectFormat {
        throw new IncorrectFormat("can't change userID of guest.");
    }
}
