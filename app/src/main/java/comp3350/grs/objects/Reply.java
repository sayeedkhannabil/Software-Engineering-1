package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;
//domain object of reply(a user reply to a post)
public class Reply extends ForumContent{
    private int postID;//the post referred to by this reply

    public Reply(){
        super();
        postID=-1;
    }

    public Reply(int replyID,String content,String userID,int postID) throws IncorrectFormat{
        super(replyID,content,userID);
        this.postID=postID;
    }

    public Reply(String comment,String userID,int postID) throws IncorrectFormat{
        super(comment,userID);
        this.postID=postID;
    }

    public int getPostID(){
        return postID;
    }

    public boolean equals(Object object){
        return super.equals(object)&&object instanceof Reply;
    }

}