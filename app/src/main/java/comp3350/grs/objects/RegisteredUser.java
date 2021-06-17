package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.exceptions.IncorrectPassword;

// CLASS: RegisteredUser
//
// Author: Shiqing Guo
//
// REMARKS: domain object of the user who will use this app, and willing to
// signup
//
//-----------------------------------------
public class RegisteredUser extends User{
    private String password;

    public RegisteredUser(String userID,String password) throws IncorrectFormat {
        super(userID);
        checkUseridFormat(userID);
        checkPassNotNull(password);
        //password can't contain space
        checkPasswordFormat(password);
        this.password=password;
    }

    public RegisteredUser(String userID) throws IncorrectFormat {
        super(userID);
        checkUseridFormat(userID);
        this.password=null;
    }


    public RegisteredUser(){
        super();
        password=null;
    }

    //------------------------------------------------------
    // checkUseridFormat
    //
    // PURPOSE:    check if the user id is valid
    // PARAMETERS:
    //     userID: the userid to be checked
    //
    //------------------------------------------------------
    private void checkUseridFormat(String userID) throws IncorrectFormat {
        //registered userid shouldn't be "Guest"
        if (userID.equals("Guest")){
            throw new IncorrectFormat("user id should not be Guest");
        }
    }

    private void checkPassNotNull(String password){
        if (password==null){
            throw new NullPointerException("password should not be null.");
        }
    }

    private void checkPasswordFormat(String password) throws IncorrectFormat {
        if (password!=null&& password.contains(" ")){
            throw new IncorrectFormat("password should not contain space");
        }
    }

    //check if the given password is the same as the password signed up
    public void checkPassMatch(String password) throws IncorrectPassword {
        if (this.password!=null){
            if (!this.password.equals(password)){
                throw new IncorrectPassword("incorrect password");
            }
        }
        else{
            throw new NullPointerException("password should not be null");
        }
    }

    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean validUser(){
        return super.validUser()&&password!=null;
    }

    @Override
    public String toString() {
        return "type:registered user,"+super.toString()+","+"password:"+this.password;
    }
}
