package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;


//domain object of the user who will use this app, and don't want
// to sign up, referred to as "guest"
public class Guest extends User{
    private final static String GUEST_ID="Guest";
    //user id of a guest is "Guest"
    public Guest() throws IncorrectFormat {
        super(GUEST_ID);
    }

    @Override
    public String toString() {
        return "type:guest\n"+ super.toString();
    }

    //a guest should not be able to change user id
    @Override
    public void changeUserID(String newID) throws IncorrectFormat {
        throw new IncorrectFormat("can't change userID of guest.");
    }
}
