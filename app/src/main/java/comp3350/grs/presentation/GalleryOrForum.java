package comp3350.grs.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import comp3350.grs.R;

public class GalleryOrForum extends AppCompatActivity {
    private TextView gallery_or_forum_gallery;
    private TextView gallery_or_forum_forum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_or_forum);

        initiate();
        setTopBar();
        setChoice();
    }

    private void initiate(){
        gallery_or_forum_gallery=findViewById(R.id.gallery_or_forum_gallery);
        gallery_or_forum_forum=findViewById(R.id.gallery_or_forum_forum);
    }

    private void setTopBar(){
        TextView top_bar_text;
        top_bar_text=findViewById(R.id.top_bar_text);
        top_bar_text.setText("Enter Choice");
    }

    private void setChoice(){
        Utilities.setOnTouchEffect(gallery_or_forum_gallery);
        Utilities.setOnTouchEffect(gallery_or_forum_forum);
        gallery_or_forum_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GalleryOrForum.this, GameGallery.class);
                //open the game gallery page
                startActivity(intent);
            }
        });

        gallery_or_forum_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GalleryOrForum.this, ForumPage.class);
                //open the forum page
                startActivity(intent);
            }
        });
    }
}