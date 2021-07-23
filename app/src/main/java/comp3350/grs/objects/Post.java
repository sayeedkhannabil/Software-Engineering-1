// This class represents Post for Discussion
// Any time a valid content is posted, a Post object is created and holds the content,-
// -the active user posted the content, as well as a toString() and equal method,-
// -input validation

package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;


// Domanin Object of Post for Discussion
public class Post extends ForumContent{

    private String title;

    public Post() {
        super();
        this.title=null;
    }


    public Post(String title, String content, String userID) throws IncorrectFormat {
        super(content,userID);
        this.title=title;
    }

    public Post(int postID,String title, String content, String userID) throws IncorrectFormat {
        super(postID,content,userID);
        this.title=title;

    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean valid(){
        return super.valid()&&title!=null;
    }

    public boolean equals (Object object) {
        return super.equals(object)&&object instanceof Post;
    }


}
