package comp3350.grs.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import comp3350.grs.R;

public class ForumPage extends AppCompatActivity {
    private View single_post_layout;
    private TextView single_post;
    private LayoutInflater inflater;
    private LinearLayout forum_page_post_wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_page);

        initiate();
        setTopBar();
        generatePosts();
    }

    private void initiate(){
        forum_page_post_wrapper=findViewById(R.id.forum_page_post_wrapper);
    }

    private void setTopBar(){
        TextView top_bar_text;
        top_bar_text=findViewById(R.id.top_bar_text);
        top_bar_text.setText("Discussion Forum");
    }

    private void generatePosts(){
        ViewGroup viewGroup;
        for (int i = 0; i < 10; i++) {
            inflater = getLayoutInflater();
            single_post_layout = inflater.inflate(R.layout.singe_post,
                    null,
                    false);
            viewGroup=(ViewGroup) single_post_layout;
            single_post= single_post_layout.findViewById(R.id.single_post);
            single_post.setText("post"+i);
            viewGroup.removeAllViews();
            forum_page_post_wrapper.addView(single_post);
        }
    }
}