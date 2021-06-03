package comp3350.grs.objects;

public class RegisteredUser extends User{
    private String password;

    public RegisteredUser(String userID,String password){
        super(userID);
        this.password=password;
    }
}
