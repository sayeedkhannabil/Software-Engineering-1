package comp3350.grs.objects;
//domain object of upvote
public class Upvote extends Vote implements VoteI{

    public Upvote(){
        super();
    }

    public Upvote(String userID){
        super(userID,UP_VALUE);
    }
}
