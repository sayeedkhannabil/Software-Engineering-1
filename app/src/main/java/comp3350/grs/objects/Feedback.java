package comp3350.grs.objects;

public abstract class Feedback {
    protected String userID;
    public Feedback(){
        this.userID=null;
    }
    public Feedback(String userID){
        this.userID=userID;
    }
    public boolean validFeedback(){
        return userID!=null;
    }
}