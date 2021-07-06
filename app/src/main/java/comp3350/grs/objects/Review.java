package comp3350.grs.objects;

import comp3350.grs.business.AccessRatings;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.exceptions.IncorrectOrder;

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
    private int reviewID;//used to uniquely identify a review

    public Review() {
        this.comment = null;
    }

    //create a review, use auto generated reviewID
    public Review(String comment,String gameName,String userID) throws IncorrectFormat {
        this.userID=userID;
        this.gameName=gameName;
        checkComment(comment);
        this.comment = comment;
        reviewID=-1;
    }

    //specify a reviewID
    public Review(int reviewID, String comment,String gameName,String userID
                   ) throws IncorrectFormat {
        this.userID=userID;
        this.gameName=gameName;
        checkComment(comment);
        this.comment = comment;
        this.reviewID=reviewID;
    }

    public Review(String comment) {
        this.comment = comment;
        this.userID=null;
        this.gameName=null;
        reviewID=-1;
    }

    private void checkComment(String comment) throws IncorrectFormat{
        if (comment.length()>140||comment.length()<=0){
            throw new IncorrectFormat("letters of review content should be " +
                    "between 1 and 140");
        }
    }

    public boolean validReview(){
        return comment!=null&&userID!=null&&gameName!=null;
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

    public boolean equals(Object object){
        boolean result;
        result = false;
        Review review;

        if (object instanceof Rating){
            review=(Review) object;
            result=this.reviewID==review.getReviewID();
        }
        return result;
    }

    public String toString() {
        return "UserID: "+userID+"\nReview: " + comment+"\n";
    }
}
