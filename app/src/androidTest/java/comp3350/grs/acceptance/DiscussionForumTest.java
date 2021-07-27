package comp3350.grs.acceptance;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.grs.R;
import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.business.AccessPosts;
import comp3350.grs.business.AccessReplys;
import comp3350.grs.business.AccessUsers;
import comp3350.grs.business.AccessVoteReplys;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Post;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.Reply;
import comp3350.grs.objects.Upvote;
import comp3350.grs.objects.VoteReply;
import comp3350.grs.persistence.DataAccessI;
import comp3350.grs.persistence.DataAccessObject;
import comp3350.grs.presentation.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
@MediumTest
public class DiscussionForumTest {
    private static DataAccessI dataAccessI;
    private AccessReplys accessReplys;
    private AccessPosts accessPosts;
    private AccessVoteReplys accessVoteReplys;
    private AccessUsers accessUsers;

    @Rule
    public ActivityTestRule<MainActivity> homeActivity =
            new ActivityTestRule<>(MainActivity.class);


    @Before
    public void before(){
        dataAccessI=new DataAccessObject(Main.testDbName);
        Services.closeDataAccess();
        Services.createDataAccess(dataAccessI);
        dataAccessI.clearAllData();
        accessReplys=new AccessReplys();
        accessPosts =new AccessPosts();
        accessVoteReplys=new AccessVoteReplys();
        accessUsers=new AccessUsers();
    }


    @AfterClass
    public static void afterClass(){
        dataAccessI.close();
    }


    @Test
    public void testWritePost() {
        //a user continue as a guest and write a post
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Discussion Forum")).perform(click());
        onView(withText("Write Post")).perform(click());
        onView(withHint("Title")).perform(typeText("Hello"));
        onView(withHint("content")).perform(typeText("World!"));
        onView(withText("Submit Post")).perform(click());
        onView(allOf(withText("Hello"),isDisplayed())).check(matches(isDisplayed()));
        onView(allOf(withText("Hello"),isDisplayed())).perform(click());
        onView(withId(R.id.post_page_post_title)).check(matches(withText(
                "Hello")));
        onView(withId(R.id.post_page_post_content)).check(matches(withText(
                "World!")));
        onView(withId(R.id.post_page_post_user)).check(matches(withText(
                "Guest")));
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());
        onView(withId(R.id.back_to_prev)).perform(click());
        //a user signup and write a post
        onView(withText("SIGNUP")).perform(click());
        onView(withHint("userID")).perform(typeText("user1"));
        onView(withHint("password")).perform(typeText("pass"));
        Espresso.closeSoftKeyboard();
        onView(withText("CONFIRM AND LOGIN")).perform(click());
        onView(withText("Enter Discussion Forum")).perform(click());
        onView(withText("Write Post")).perform(click());
        onView(withHint("Title")).perform(typeText("Weather"));
        onView(withHint("content")).perform(typeText("Today is a good day!"));
        onView(withText("Submit Post")).perform(click());
        onView(allOf(withText("Hello"),isDisplayed())).check(matches(isDisplayed()));
        onView(allOf(withText("Weather"),isDisplayed())).check(matches(isDisplayed()));
        onView(allOf(withText("Weather"),isDisplayed())).perform(click());
        onView(withId(R.id.post_page_post_title)).check(matches(withText(
                "Weather")));
        onView(withId(R.id.post_page_post_content)).check(matches(withText(
                "Today is a good day!")));
        onView(withId(R.id.post_page_post_user)).check(matches(withText(
                "user1")));
    }

    @Test
    public void testWriteInvalidPost() {
        //a user continue as a guest and write a post
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Discussion Forum")).perform(click());
        onView(withText("Write Post")).perform(click());
        onView(withHint("Title")).perform(typeText("title"));
        onView(withHint("content")).perform(typeText(""));
        onView(withText("Submit Post")).perform(click());
        onView(withText("Letters of content should be between 1 to 500")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
        onView(withText("Write Post")).perform(click());
        onView(withHint("Title")).perform(replaceText("title"));
        String content;
        content="";
        for (int i = 0; i < 600; i++) {
            content+="c";
        }
        onView(withHint("content")).perform(replaceText(content));
        onView(withText("Submit Post")).perform(click());
        onView(withText("Letters of content should be between 1 to 500")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
    }

    @Test
    public void testWriteManyPost() {
        onView(withText("CONTINUE AS GUEST")).perform(click());
        onView(withText("Enter Discussion Forum")).perform(click());
        for (int i = 0; i < 15; i++) {
            onView(withText("Write Post")).perform(click());
            onView(withHint("Title")).perform(replaceText("post"+i));
            onView(withHint("content")).perform(replaceText("content"+i));
            onView(withText("Submit Post")).perform(click());
        }

        for (int i = 0; i < 15; i++) {
            if (i>10){
                Espresso.onView(ViewMatchers.withId(R.id.forum_page_post_scroll_wrapper)).perform(ViewActions.swipeUp());
            }

            onView(allOf(withText("post"+i),isDisplayed()) ).check(matches(isDisplayed()));
            onView(allOf(withText("post"+i),isDisplayed()) ).perform(scrollTo(),click());
            onView(withId(R.id.post_page_post_title)).check(matches(withText(
                    "post"+i)));
            onView(withId(R.id.post_page_post_content)).check(matches(withText(
                    "content"+i)));
            onView(withId(R.id.post_page_post_user)).check(matches(withText(
                    "Guest")));
            onView(withId(R.id.back_to_prev)).perform(click());
        }
    }

    @Test
    public void testReplyOwnPost() {
        RegisteredUser user1;
        Post post;
        user1=null;
        post=null;

        try {
            user1=new RegisteredUser("user1","pass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessUsers.insertUser(user1);
        try {
            post=new Post("Weather","Today is a good day!","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessPosts.insertPost(post);

        onView(withText("LOGIN")).perform(click());
        onView(withHint("userID")).perform(typeText("user1"));
        onView(withHint("password")).perform(typeText("pass"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button3)).perform(click());
        onView(withText("Enter Discussion Forum")).perform(click());

        onView(allOf(withText("Weather"),isDisplayed())).perform(click());
        onView(withText("Write Reply")).perform(click());
        onView(withHint("Content")).perform(typeText("I'm correct"));
        onView(withText("Submit Reply")).perform(click());
        onView(allOf(withId(R.id.single_reply_content),isDisplayed())).check(matches(withText("I'm correct")));
        onView(withId(R.id.single_reply_user)).check(matches(withText("user1")));
        onView(withId(R.id.single_reply_upvote_num)).check(matches(withText(
                "0")));
        onView(withId(R.id.single_reply_downvote_num)).check(matches(withText(
                "0")));
    }

    @Test
    public void testReplyOtherPost() {
        RegisteredUser user1;
        Post post;
        user1=null;
        post=null;

        try {
            user1=new RegisteredUser("user1","pass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessUsers.insertUser(user1);
        try {
            post=new Post("Weather","Today is a good day!","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessPosts.insertPost(post);

        onView(withText("SIGNUP")).perform(click());
        onView(withHint("userID")).perform(typeText("user2"));
        onView(withHint("password")).perform(typeText("pass"));
        Espresso.closeSoftKeyboard();
        onView(withText("CONFIRM AND LOGIN")).perform(click());
        onView(withText("Enter Discussion Forum")).perform(click());
        onView(allOf(withText("Weather"),isDisplayed())).perform(click());
        onView(withText("Write Reply")).perform(click());
        onView(withHint("Content")).perform(typeText("you are not correct"));
        onView(withText("Submit Reply")).perform(click());
        onView(allOf(withId(R.id.single_reply_content),
                withText("you are not correct"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.single_reply_user),withText("user2"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.single_reply_upvote_num),
                hasSibling(withText("user2")))).check(matches(withText(
                "0")));
        onView(allOf(withId(R.id.single_reply_downvote_num),
                hasSibling(withText("user2")))).check(matches(withText(
                "0")));
    }

    @Test
    public void testInvalidReply() {
        RegisteredUser user1;
        Post post;
        user1=null;
        post=null;
        String content;

        content="";
        for (int i = 0; i < 600; i++) {
            content+="c";
        }
        try {
            user1=new RegisteredUser("user1","pass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessUsers.insertUser(user1);
        try {
            post=new Post("Weather","Today is a good day!","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessPosts.insertPost(post);

        onView(withText("LOGIN")).perform(click());
        onView(withHint("userID")).perform(typeText("user1"));
        onView(withHint("password")).perform(typeText("pass"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button3)).perform(click());
        onView(withText("Enter Discussion Forum")).perform(click());

        onView(allOf(withText("Weather"),isDisplayed())).perform(click());
        onView(withText("Write Reply")).perform(click());
        onView(withHint("Content")).perform(typeText(""));
        onView(withText("Submit Reply")).perform(click());
        onView(withText("Letters of content should be between 1 to 500")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
        onView(withText("Write Reply")).perform(click());
        onView(withHint("Content")).perform(typeText(content));
        onView(withText("Submit Reply")).perform(click());
        onView(withText("Letters of content should be between 1 to 500")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
    }

    @Test
    public void testWriteManyReply() {
        RegisteredUser user1;
        Post post;
        user1=null;
        post=null;

        try {
            user1=new RegisteredUser("user1","pass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessUsers.insertUser(user1);
        try {
            post=new Post("Weather","Today is a good day!","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessPosts.insertPost(post);

        onView(withText("LOGIN")).perform(click());
        onView(withHint("userID")).perform(replaceText("user1"));
        onView(withHint("password")).perform(replaceText("pass"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button3)).perform(click());
        onView(withText("Enter Discussion Forum")).perform(click());
        onView(allOf(withText("Weather"),isDisplayed())).perform(click());

        for (int i = 0; i < 15; i++) {
            onView(withText("Write Reply")).perform(click());
            onView(withHint("Content")).perform(replaceText("content"+i));
            onView(withText("Submit Reply")).perform(click());
        }
        for (int i = 0; i < 15; i++) {
            if (i>10){
                Espresso.onView(ViewMatchers.withId(R.id.post_page_scroll_view)).perform(ViewActions.swipeUp());
            }
            onView(allOf(withText("content"+i),isDisplayed())).check(matches(isDisplayed()));
        }
    }

    @Test
    public void testVoteOwnReply(){
        RegisteredUser user1;
        Post post;
        Reply reply;

        user1=null;
        post=null;
        reply=null;

        try {
            user1=new RegisteredUser("user1","pass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessUsers.insertUser(user1);
        try {
            post=new Post(10,"Weather","Today is a good day!","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessPosts.insertPost(post);
        try {
            reply=new Reply(10,"I'm correct","user1",10);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessReplys.insertReply(reply);

        onView(withText("LOGIN")).perform(click());
        onView(withHint("userID")).perform(typeText("user1"));
        onView(withHint("password")).perform(typeText("pass"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button3)).perform(click());
        onView(withText("Enter Discussion Forum")).perform(click());
        onView(withText("Weather")).perform(click());
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_upvote_icon))).perform(click());
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_upvote_num))).check(matches(withText("1")));
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_downvote_num))).check(matches(withText("0")));
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_downvote_icon))).perform(click());
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_upvote_num))).check(matches(withText("0")));
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_downvote_num))).check(matches(withText("1")));
    }

    @Test
    public void testVoteOtherReply(){
        RegisteredUser user1,user2;
        Post post;
        Reply reply;
        VoteReply voteReply;

        user1=null;
        user2=null;
        post=null;
        reply=null;
        voteReply=null;

        try {
            user1=new RegisteredUser("user1","pass");
            user2=new RegisteredUser("user2","pass");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessUsers.insertUser(user1);
        accessUsers.insertUser(user2);
        try {
            post=new Post(10,"Weather","Today is a good day!","user1");
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessPosts.insertPost(post);
        try {
            reply=new Reply(10,"I'm correct","user1",10);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        accessReplys.insertReply(reply);
        voteReply=new VoteReply(new Upvote("user1"),10);
        accessVoteReplys.insertVoteReply(voteReply);

        onView(withText("LOGIN")).perform(click());
        onView(withHint("userID")).perform(typeText("user2"));
        onView(withHint("password")).perform(typeText("pass"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button3)).perform(click());
        onView(withText("Enter Discussion Forum")).perform(click());
        onView(withText("Weather")).perform(click());
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_upvote_icon))).perform(click());
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_upvote_num))).check(matches(withText("2")));
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_downvote_num))).check(matches(withText("0")));
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_downvote_icon))).perform(click());
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_upvote_num))).check(matches(withText("1")));
        onView(allOf(hasSibling(withText("user1")),
                withId(R.id.single_reply_downvote_num))).check(matches(withText("1")));
    }

}