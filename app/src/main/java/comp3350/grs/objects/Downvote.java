package comp3350.grs.objects;

public class Downvote extends Vote implements VoteI{

    public Downvote(){
        super();
    }

    public Downvote(String userID){
        super(userID,DOWN_VALUE);
    }
}
