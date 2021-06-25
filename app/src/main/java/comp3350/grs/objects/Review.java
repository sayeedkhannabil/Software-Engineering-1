package comp3350.grs.objects;

import comp3350.grs.business.AccessUsers;
import comp3350.grs.exceptions.IncorrectFormat;

// CLASS: Review
//
// Author: Abu Sayeed Khan
//
// REMARKS: What is the purpose of this class?
// store review of the games
//-----------------------------------------
public class Review {
    private String comment;
    private String userID;
    private String gameName;
    private static int nextReviewID=1;
    private int reviewID;//used to uniquely identify a review

    public Review() {
        this.comment = null;
    }

    //create a review, use auto generated reviewID
    public Review(String comment,String gameName,String userID) throws IncorrectFormat {
        this.userID=userID;
        checkReview(comment);
        this.comment = comment;
        this.gameName=gameName;
        reviewID=nextReviewID;
        nextReviewID++;
    }

    //specify a reviewID
    public Review(int reviewID, String comment,String gameName,String userID
                   ) throws IncorrectFormat {
        this.userID=userID;
        checkReview(comment);
        this.comment = comment;
        this.gameName=gameName;
        this.reviewID=reviewID;
    }

    private void checkReview(String review) throws IncorrectFormat {
        if (review.length()>140||review.length()<=0){
            throw new IncorrectFormat("letters of review content should be " +
                    "between 1 and 140");
        }
    }

    public String getComment() {
        return comment;
    }

    public int getReviewID(){
        return reviewID;
    }

    public String getGameName(){
        return gameName;
    }

    public String getUserID(){
        return userID;
    }

    public boolean equals(Review review){
        return this.reviewID==review.getReviewID();
    }

    public String toString() {
        return "UserID: "+userID+"\nReview: " + comment+"\n";
    }
}
