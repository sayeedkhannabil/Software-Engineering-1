package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;
//parent class of post and reply(which are content in discussion forum)
public class ForumContent {
    private int ID;//id of the object
    private String content;//content of the object
    private String userID;//the user who write the content

    public ForumContent() {
        this.ID = -1;
        this.content = null;
        this.userID = null;
    }

    //does not specify the id, want the database generate for it
    public ForumContent(String content, String userID) throws IncorrectFormat {
        checkContent(content);
        this.ID = -1 ;//default value
        this.content = content;
        this.userID = userID;
    }

    public ForumContent(int ID, String content, String userID) throws IncorrectFormat {
        checkContent(content);
        this.ID = ID ;
        this.content = content;
        this.userID = userID;
    }

    //check the format of content is correct
    private void checkContent(String content) throws IncorrectFormat {
        final int MAX_LENGTH = 500 ;
        final int MIN_LENGTH = 0 ;
        if (content.length()>MAX_LENGTH || content.length()<=MIN_LENGTH) {
            throw new IncorrectFormat("Letters of content should be " +
                    "between 1 to 500");
        }
    }

    // //check if the ForumContent is valid(important info is not null)
    public boolean valid() {
        return this.ID>=-1 && this.content!=null && this.userID!=null;
    }

    public int getID(){
        return this.ID;
    }

    public String getContent(){
        return this.content;
    }

    public String getUserID(){
        return this.userID;
    }

    public boolean equals (Object objet) {
        boolean result;
        result = false;
        ForumContent forumContent;

        if(objet!=null && valid() && objet instanceof ForumContent) {
            forumContent = (ForumContent) objet;
            result = this.ID ==forumContent.ID;
        }
        return result;
    }

    public String toString() {
        return "UserID: " + this.userID + "\ncontent: " + this.content + "\n";
    }
}
