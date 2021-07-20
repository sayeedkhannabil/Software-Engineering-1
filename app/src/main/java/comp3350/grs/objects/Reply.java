package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;

public class Reply extends Feedback{
    private int replyID;
    private String comment;
    private int upvote;
    private int downvote;

    public Reply(){
        this.replyID=-1;
        this.comment=null;
    }

    public Reply(String comment) throws IncorrectFormat{
        checkComment(comment);
        this.replyID=-1;
        this.comment=comment;
        this.upvote=0;
        this.downvote=0;
    }

    public Reply(int replyID,String comment,int upvote,int downvote,String userID) throws IncorrectFormat{
        super(userID);
        checkComment(comment);
        this.replyID=replyID;
        this.comment=comment;
        this.upvote=upvote;
        this.downvote=downvote;
    }

    public Reply(String comment,int upvote,int downvote,String userID) throws IncorrectFormat{
        super(userID);
        checkComment(comment);
        this.comment=comment;
        this.upvote=upvote;
        this.downvote=downvote;
    }

    public boolean validReply(){
        return replyID>=0 && comment!=null && userID!=null;
    }
    public int getReplyID(){
        return replyID;
    }
    public String getComment(){
        return comment;
    }
    public String getUserID(){
        return userID;
    }
    public int getUpvote(){
        return upvote;
    }
    public int getDownvote(){
        return downvote;
    }

    private void checkComment(String comment) throws IncorrectFormat{
        final int MAX_LENGTH=500;
        final int MIN_LENGTH=0;
        if (comment.length()>MAX_LENGTH||comment.length()<=MIN_LENGTH){
            throw new IncorrectFormat("letters of review content should be " +
                    "between 1 and 500");
        }
    }
    
    public boolean equals(Object object){
        boolean result=false;
        Reply r;
        if (object!=null && validReply() && object instanceof Reply){
            r=(Reply) object;
            result=this.getReplyID() == r.getReplyID();
        }
        return result;
    }

}