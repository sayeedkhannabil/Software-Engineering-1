package comp3350.grs.presentation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import comp3350.grs.R;
import comp3350.grs.business.AccessPosts;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Post;

public class ForumPage extends AppCompatActivity {
    private View single_post_layout;
    private TextView single_post;
    private LayoutInflater inflater;
    private LinearLayout forum_page_post_wrapper;
    private AccessPosts accessPosts;
    private TextView write_post_button;
    private LinearLayout write_post_wrapper;
    private EditText post_title;
    private EditText post_content;
    private boolean isWritingPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_page);

        initiate();
        setTopBar();
        generatePosts();
        setWrite_post_button();
    }

    private void initiate(){
        forum_page_post_wrapper=findViewById(R.id.forum_page_post_wrapper);
        accessPosts =new AccessPosts();
        write_post_button=findViewById(R.id.write_post_button);
        write_post_wrapper=findViewById(R.id.write_post_wrapper);
        post_title=findViewById(R.id.post_title);
        post_content=findViewById(R.id.post_content);
        isWritingPost=false;
    }

    private void setClickPost(TextView postView,Post postObj){
        Utilities.setOnTouchEffect(postView);
        postView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForumPage.this, PostPage.class);

                intent.putExtra("postID",postObj.getID());//pass the game
                // name to game detail page
                startActivity(intent);
            }
        });
    }

    private void setTopBar(){
        TextView top_bar_text;
        top_bar_text=findViewById(R.id.top_bar_text);
        top_bar_text.setText("Discussion Forum");
    }

    private void generatePosts(){
        ViewGroup viewGroup;
        List<Post> postList= accessPosts.getAllPosts();
        Post currPost;

        forum_page_post_wrapper.removeAllViews();
        for (int i = 0; i < postList.size(); i++) {
            currPost=postList.get(i);
            inflater = getLayoutInflater();
            single_post_layout = inflater.inflate(R.layout.singe_post,
                    null,
                    false);
            viewGroup=(ViewGroup) single_post_layout;
            single_post= single_post_layout.findViewById(R.id.single_post);
            single_post.setText(currPost.getTitle());
            setClickPost(single_post,currPost);
            viewGroup.removeAllViews();
            forum_page_post_wrapper.addView(single_post);
        }
    }

    private void setWrite_post_button(){
        write_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isWritingPost){
                    write_post_button.setText("Submit Post");
                    write_post_wrapper.setVisibility(View.VISIBLE);
                    isWritingPost=true;

                }
                else {
                    String title;
                    String content;
                    String userID;
                    Post post;

                    post=null;
                    title=post_title.getText().toString();
                    content=post_content.getText().toString();
                    userID= AccessUsers.getActiveUser().getUserID();
                    try {
                        post=new Post(title,content,userID);
                        accessPosts.insertPost(post);
                    } catch (IncorrectFormat incorrectFormat) {
                        AlertDialog alertDialog;
                        alertDialog=Utilities.createAlertDialog(incorrectFormat.getMessage(),
                                ForumPage.this);
                        alertDialog.show();
                    }

                    write_post_button.setText("Write Post");
                    write_post_wrapper.setVisibility(View.GONE);
                    isWritingPost=false;
                    generatePosts();
                }
            }
        });
    }
}