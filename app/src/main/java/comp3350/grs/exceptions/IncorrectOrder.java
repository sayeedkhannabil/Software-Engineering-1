package comp3350.grs.exceptions;
//the order of a process is incorrect(eg.should do sth before sth)
public class IncorrectOrder extends Exception{

    public IncorrectOrder(String message){
        super(message);
    }
}
