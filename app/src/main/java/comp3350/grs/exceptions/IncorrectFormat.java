package comp3350.grs.exceptions;
//the format is incorrect(userid,password,etc)
public class IncorrectFormat extends Exception{

    public IncorrectFormat(String message){
        super(message);
    }
}
