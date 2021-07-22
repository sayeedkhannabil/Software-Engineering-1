package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.exceptions.IncorrectPassword;


// domain object of the user who will use this app, and willing to
// signup

public class RegisteredUser extends User{
    private String password;

    public RegisteredUser(String userID,String password) throws IncorrectFormat {
        super(userID);
        //password can't contain space
        checkPasswordFormat(password);
        this.password=password;
    }

    public RegisteredUser(String userID) throws IncorrectFormat {
        super(userID);
        this.password=null;
    }


    public RegisteredUser(){
        super();
        password=null;
    }


    // check if the user id is of correct format
    @Override
    protected void checkUseridFormat(String userID) throws IncorrectFormat {
        super.checkUseridFormat(userID);
        //registered userid shouldn't be "Guest"
        if (userID.equals("Guest")){
            throw new IncorrectFormat("user id should not be Guest");
        }
    }


    //check if the password is of correct format
    private void checkPasswordFormat(String password) throws IncorrectFormat {
        final int MIN_LENGTH=3;
        final int MAX_LENGTH=20;
        if (password.contains(" ")){
            throw new IncorrectFormat("password should not contain space");
        }
        else if(password.length()<MIN_LENGTH||password.length()>MAX_LENGTH){
            throw new IncorrectFormat("length of password should >="+MIN_LENGTH+" and <="+MAX_LENGTH);
        }
    }


    //check if the given password is the same as the password signed up
    public void checkPassMatch(String password) throws IncorrectPassword {
        if (!this.password.equals(password)){
            throw new IncorrectPassword("incorrect password");
        }
    }

    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean valid(){
        return super.valid()&&password!=null;
    }

    @Override
    public String toString() {
        return "type:registered user\n"+super.toString()+"\npassword:"+ this.password;
    }
}
