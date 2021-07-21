package comp3350.grs.objects;

public interface VoteI {

    public boolean valid();

    public String getUserID();

    public int getValue();

    public static VoteI createVote(String userID,int value);
}
