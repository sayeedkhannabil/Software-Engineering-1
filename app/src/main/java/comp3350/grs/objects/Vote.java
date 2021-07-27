package comp3350.grs.objects;
//parent class of upvote and downvote
public abstract class Vote {
    private String userID;//user who do the vote
    private int value;//value of the vote

    public static int UP_VALUE=1;//value of upvote
    public static int DOWN_VALUE=-1;

    public Vote(){
        userID=null;
        value=0;
    }

    public Vote(String userID,int value){
        this.userID=userID;
        this.value=value;
    }

    //important info is not null
    public boolean valid(){
        return userID!=null;
    }

    public String getUserID(){
        return this.userID;
    }

    public int getValue(){
        return this.value;
    }

    public String toString(){
        return "UserID:"+userID+",value:"+value;
    }

    //create a vote object according to userID and vote value
    public static VoteI createVote(String userID,int value){
        VoteI voteI;
        voteI=null;
        if (value==UP_VALUE){
            voteI=new Upvote(userID);
        }
        else if(value==DOWN_VALUE){
            voteI=new Downvote(userID);
        }
        return voteI;
    }

    @Override
    public boolean equals(Object object){
        boolean result;
        result = false;
        Vote vote;

        if (object!=null&& valid()&& object instanceof Vote){
            vote=(Vote) object;
            result=this.userID.equals(vote.userID)&&value==vote.value;
        }
        return result;
    }

}
