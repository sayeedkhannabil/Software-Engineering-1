package comp3350.grs.objects;

public class VoteReply {
    private VoteI voteI;
    private int replyID;

    public VoteReply(VoteI voteI,int replyID){
        this.voteI=voteI;
        this.replyID=replyID;
    }

    public VoteReply(){
        this.voteI=null;
        this.replyID=-1;
    }

    public boolean valid(){
        return this.voteI!=null;
    }

    public VoteI getVoteI(){
        return this.voteI;
    }

    public int getReplyID(){
        return this.replyID;
    }
}
