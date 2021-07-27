package comp3350.grs.presentation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import comp3350.grs.R;
import comp3350.grs.business.AccessPosts;
import comp3350.grs.business.AccessReplys;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.business.AccessVoteReplys;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Downvote;
import comp3350.grs.objects.Post;
import comp3350.grs.objects.Reply;
import comp3350.grs.objects.Upvote;
import comp3350.grs.objects.VoteI;
import comp3350.grs.objects.VoteReply;

public class PostPage extends AppCompatActivity {
    private Post post;
    private int postID;
    private TextView post_page_post_title;
    private TextView post_page_post_content;
    private AccessPosts accessPosts;
    private AccessReplys accessReplys;
    private TextView post_page_post_user;
    private LinearLayout post_page_reply_wrapper;
    private boolean isWritingReply;
    private String userID;
    private AccessVoteReplys accessVoteReplys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);

        initiate();
        generateReplys();
        setPost();
        setWrite_reply_button();

    }

    private void initiate(){
        Bundle extras = getIntent().getExtras();
        postID=-1;
        if (extras != null) {
            postID = extras.getInt("postID");//get the game name
        }
        accessPosts =new AccessPosts();
        post= accessPosts.getPostById(postID);
        post_page_post_title=findViewById(R.id.post_page_post_title);
        post_page_post_content=findViewById(R.id.post_page_post_content);
        post_page_post_user=findViewById(R.id.post_page_post_user);
        post_page_reply_wrapper=findViewById(R.id.post_page_reply_wrapper);
        accessReplys=new AccessReplys();
        isWritingReply=false;
        userID= AccessUsers.getActiveUser().getUserID();
        accessVoteReplys=new AccessVoteReplys();
    }

    private void setPost(){
        post_page_post_user.setText(post.getUserID());
        post_page_post_title.setText(post.getTitle());
        post_page_post_content.setText(post.getContent());
    }

    private void setVote(ViewGroup viewGroup,Reply reply){
        TextView single_reply_upvote_num;
        TextView single_reply_downvote_num;
        List<VoteReply> voteReplyList;
        int upvote=0;
        int downvote=0;
        VoteReply voteReply;
        VoteI voteI;


        voteReplyList=accessVoteReplys.getVoteReplysByReply(reply.getID());
        single_reply_upvote_num=
                viewGroup.findViewById(R.id.single_reply_upvote_num);
        single_reply_downvote_num=
                viewGroup.findViewById(R.id.single_reply_downvote_num);

        for (int i = 0; i < voteReplyList.size(); i++) {
            voteReply=voteReplyList.get(i);
            voteI=voteReply.getVoteI();
            if (voteI instanceof Upvote){
                upvote++;
            }
            else if (voteI instanceof Downvote){
                downvote++;
            }
        }
        single_reply_upvote_num.setText(upvote+"");
        single_reply_downvote_num.setText(downvote+"");
    }


    private void generateReplys(){
        ViewGroup viewGroup;
        List<Reply> replyList=accessReplys.getReplyByPost(postID);
        Reply currReply;
        LayoutInflater inflater;
        View single_reply_layout;
        TextView single_reply_content;
        TextView single_reply_user;

        post_page_reply_wrapper.removeAllViews();
        for (int i = 0; i < replyList.size(); i++) {
            currReply=replyList.get(i);
            inflater = getLayoutInflater();
            single_reply_layout = inflater.inflate(R.layout.single_reply,
                    null,
                    false);
            viewGroup=(ViewGroup) single_reply_layout;
            single_reply_content= viewGroup.findViewById(R.id.single_reply_content);
            single_reply_user= viewGroup.findViewById(R.id.single_reply_user);
            single_reply_content.setText(currReply.getContent());
            single_reply_user.setText(currReply.getUserID());
            setVote(viewGroup,currReply);
            setVoteIcon(viewGroup,currReply);
            post_page_reply_wrapper.addView(viewGroup);
        }
    }

    private void setWrite_reply_button(){
        LinearLayout write_reply_wrapper;
        TextView write_reply_button;
        EditText post_page_reply_content;

        write_reply_wrapper=findViewById(R.id.write_reply_wrapper);
        write_reply_button=findViewById(R.id.write_reply_button);
        post_page_reply_content=findViewById(R.id.post_page_reply_content);
        write_reply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isWritingReply){
                    write_reply_wrapper.setVisibility(View.VISIBLE);
                    write_reply_button.setText("Submit Reply");
                    isWritingReply=true;

                }
                else {
                    String content;
                    Reply reply;
                    content=post_page_reply_content.getText().toString();
                    try {
                        reply=new Reply(content,userID,postID);
                        accessReplys.insertReply(reply);
                    } catch (IncorrectFormat incorrectFormat) {
                        AlertDialog alertDialog;
                        alertDialog=
                                Utilities.createAlertDialog(incorrectFormat.getMessage(), PostPage.this);
                        alertDialog.show();
                    }
                    write_reply_wrapper.setVisibility(View.GONE);
                    write_reply_button.setText("Write Reply");
                    isWritingReply=false;
                    generateReplys();
                }
            }
        });
    }

    private void setVoteIcon(ViewGroup viewGroup, Reply reply){
        ImageView single_reply_upvote_icon;
        ImageView single_reply_downvote_icon;
        VoteReply voteReply;
        VoteI voteI=null;

        voteReply=accessVoteReplys.getVoteReply(userID,reply.getID());
        if (voteReply!=null){
            voteI=voteReply.getVoteI();
        }
        single_reply_upvote_icon=
                viewGroup.findViewById(R.id.single_reply_upvote_icon);
        single_reply_downvote_icon=
                viewGroup.findViewById(R.id.single_reply_downvote_icon);
        voteReply=accessVoteReplys.getVoteReply(userID,reply.getID());
        if (voteReply!=null){
            voteI=voteReply.getVoteI();
            if (voteI instanceof Upvote){
                single_reply_upvote_icon.setImageResource(R.drawable.up_arrow);
            }
            else if (voteI instanceof Downvote){
                single_reply_downvote_icon.setImageResource(R.drawable.down_arrow);
            }
        }


        single_reply_upvote_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoteReply voteReply,newVoteReply;
                VoteI voteI=null;

                voteReply=accessVoteReplys.getVoteReply(userID,reply.getID());
                voteI=new Upvote(userID);
                newVoteReply=new VoteReply(voteI,reply.getID());

                if (voteReply==null){
                    accessVoteReplys.insertVoteReply(newVoteReply);
                }
                else {
                    accessVoteReplys.updateVoteReply(newVoteReply);
                }

                generateReplys();
            }
        });

        single_reply_downvote_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoteReply voteReply,newVoteReply;
                VoteI voteI=null;

                voteReply=accessVoteReplys.getVoteReply(userID,reply.getID());
                voteI=new Downvote(userID);
                newVoteReply=new VoteReply(voteI,reply.getID());

                if (voteReply==null){
                    accessVoteReplys.insertVoteReply(newVoteReply);
                }
                else {
                    accessVoteReplys.updateVoteReply(newVoteReply);
                }

                generateReplys();
            }
        });
    }

}