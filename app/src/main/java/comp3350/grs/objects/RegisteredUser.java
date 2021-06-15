package comp3350.grs.objects;
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

    public RegisteredUser(String userID,String password) throws Exception {
        super(userID);
        //if userid is not valid, set it to null
        if (!checkUserID(userID)){
            super.changeUserID(null);
        }
        //password can't contain space
        if (!checkPassword(password)){
            this.password=null;
        }
        else{
            this.password=password;
        }

    }

    public RegisteredUser(String userID) throws Exception {
        super(userID);
        if (!checkUserID(userID)){
            super.changeUserID(null);
        }
        password=null;
    }

    public RegisteredUser(){
        super();
        password=null;
    }

    //------------------------------------------------------
    // checkUserID
    //
    // PURPOSE:    check if the user id is valid
    // PARAMETERS:
    //     userID: the userid to be checked
    // Returns: boolean to indicate whether the userid is valid or not
    //------------------------------------------------------
    public boolean checkUserID(String userID){
        //registered userid shouldn't be "Guest"
        if (userID.equals("Guest")){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkPassword(String password){
        if (password.contains(" ")){
            return false;
        }
        else {
            return true;
        }
    }

    //check if the given password is the same as the password signed up
    public boolean validPass(String password){
        if (this.password!=null){
            return this.password.equals(password);
        }
        else{
            return false;
        }
    }

    public String getPassword(){
        return this.password;
    }

    @Override
    public String toString() {
        return "type:registered user,"+super.toString()+","+"password:"+this.password;
    }
}
