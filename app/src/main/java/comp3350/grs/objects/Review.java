package comp3350.grs.objects;

import comp3350.grs.business.AccessUsers;

// CLASS: Review
//
// Author: Abu Sayeed Khan
//
// REMARKS: What is the purpose of this class?
// store review of the games
//-----------------------------------------
public class Review {
    private String comment;
    private User user;

    public Review() {
        this.comment = null;
    }

    //stores review as string
    public  Review(String comment) {
        user = AccessUsers.getActiveUser();
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String toString() {
        return "UserID: "+user.getUserID()+"\nReview: " + comment+"\n";
    }
}
