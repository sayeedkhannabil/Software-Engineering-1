package comp3350.grs.objects;

public class Guest extends User{

    public Guest(){
        super("Guest");
    }

    @Override
    public String toString() {
        return "type:guest,"+ super.toString();
    }

    @Override
    public void changeUserID(String newID) throws Exception {
        throw new Exception("can't change userID of guest.");
    }
}
