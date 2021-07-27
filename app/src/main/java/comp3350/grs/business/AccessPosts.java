package comp3350.grs.business;

import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.objects.Post;
import comp3350.grs.persistence.DataAccessI;
//business object of post
public class AccessPosts {

    private DataAccessI dataAccessI;
    private List<Post> postList;
    private Post currentPost;
    private int currentPostIndex;

    public AccessPosts() {
        dataAccessI = Services.getDataAccess(Main.dbName);
        postList = null;
        currentPost = null;
        currentPostIndex = 0;
    }

    public void clear(){
        dataAccessI.clearPosts();
    }

    public List<Post> getAllPosts() {
        postList = dataAccessI.getAllPosts();
        return postList;
    }

    public List<Post> getPostsByUser(String userID) {
        return dataAccessI.getPostsByUser(userID);
    }

    public Post getPostById(int id) {
        return dataAccessI.getPostByID(id);
    }

    public boolean insertPost(Post post) {
        return dataAccessI.insertPost(post);
    }

    public boolean updatePost(Post post) {
        return dataAccessI.updatePost(post);
    }

    public boolean deletePost(Post post) {
        return dataAccessI.deletePost(post);
    }
}
