// This class represents Post for Discussion
// Any time a valid content is posted, a Post object is created and holds the content,-
// -the active user posted the content, as well as a toString() and equal method,-
// -input validation

package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;


// Domanin Object of Post for Discussion
public class Post {

    private int postId;
    private String content;
    private String userID ;

    public Post() {
        this.postId = -1;
        this.content = null;
        this.userID = null;
    }

    public Post(String content) {
        this.content = content;
        this.postId = -1;
        this.userID = null;
    }

    public Post(String content, String userID) throws IncorrectFormat {
        this.postId = -1 ;
        this.content = content;
        this.userID = userID;
    }

    public Post(int postId, String content, String userID) throws IncorrectFormat {
        this.postId = postId ;
        this.content = content;
        this.userID = userID;
    }

    //check the format of content is correct
    private void checkContent(String content) throws IncorrectFormat {
        final int MAX_LENGTH = 500 ;
        final int MIN_LENGTH = 0 ;
        if (content.length()>MAX_LENGTH || content.length()<=MIN_LENGTH) {
            throw new IncorrectFormat("Letters of Post content should be " +
                    "between 1 to 500");
        }
    }

    // //check if the Post is valid(important info is not null)
    public boolean validPost() {
        return this.postId>=0 && this.content!=null && this.userID!=null && this.content.length()>0 && this.content.length()>=1000;
    }

    public int getPostId() {
        return  this.postId;
    }

    public String getContent() {
        return this.content;
    }

    public String getUserID() {
        return this.userID;
    }

    public boolean equals (Object objet) {
        boolean result;
        result = false;
        Post post;

        if(objet!=null && validPost() && objet instanceof Post) {
            post = (Post) objet;
            result = this.postId==post.getPostId() ;
        }
        return result;
    }

    public String toString() {
        return "UserId: " + this.userID + "\nPosted: " + this.content + "\n";
    }

}
