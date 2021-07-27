package comp3350.grs.objects;
//combination of vote and reply, refers to a vote on a reply
public class VoteReply {
    private VoteI voteI;
    private int replyID;//the reply referred to by the vote

    public VoteReply(VoteI voteI,int replyID){
        this.voteI=voteI;
        this.replyID=replyID;
    }


    public VoteReply(){
        this.voteI=null;
        this.replyID=-1;
    }

    public boolean valid(){
        return this.voteI!=null&& voteI.valid()&&replyID>=-1;
    }

    public VoteI getVoteI(){
        return this.voteI;
    }

    public int getReplyID(){
        return this.replyID;
    }

    @Override
    public String toString(){
        return voteI.toString()+",ReplyID:"+replyID;
    }

    @Override
    public boolean equals(Object object){
        boolean result;
        result = false;
        VoteReply voteReply;

        if (object!=null&& valid()&& object instanceof VoteReply){
            voteReply=(VoteReply) object;
            result=
                    this.voteI.getUserID().equals(voteReply.voteI.getUserID())&&replyID==voteReply.replyID;
        }
        return result;
    }
}
