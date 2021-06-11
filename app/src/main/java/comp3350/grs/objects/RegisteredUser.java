package comp3350.grs.objects;

public class RegisteredUser extends User{
    private String password;

    public RegisteredUser(String userID,String password) throws Exception {
        super(userID);
        if (!checkUserID(userID)){
            super.changeUserID(null);
        }
        if (password.contains(" ")){
            this.password=null;
        }
        else{
            this.password=password;
        }

    }

    public RegisteredUser(String userID){
        super(userID);
        password=null;
    }

    public RegisteredUser(){
        super();
        password=null;
    }

    public boolean checkUserID(String userID){
        if (userID.equals("Guest")){
            return false;
        }
        else{
            return true;
        }
    }

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
