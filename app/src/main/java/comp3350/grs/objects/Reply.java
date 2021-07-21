package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;

public class Reply extends ForumContent{

    public Reply(){
        super();
    }


    public Reply(int replyID,String content,String userID) throws IncorrectFormat{
        super(replyID,content,userID);
    }

    public Reply(String comment,String userID) throws IncorrectFormat{
        super(comment,userID);
    }


    
    public boolean equals(Object object){
        return super.equals(object)&&object instanceof Reply;
    }

}