package comp3350.grs.objects;

import comp3350.grs.business.AccessRatings;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.exceptions.IncorrectOrder;



// store review of the games
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
        reviewID=-1;//default value
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
        final int MAX_LENGTH=140;
        if (comment.length()>MAX_LENGTH||comment.length()<=0){
            throw new IncorrectFormat("letters of review content should be " +
                    "between 1 and 140");
        }
    }

    //important info is not null
    public boolean validReview(){
        return reviewID>=-1&& comment!=null&&userID!=null&&gameName!=null;
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

    @Override
    public boolean equals(Object object){
        boolean result;
        result = false;
        Review review;

        if (object!=null&& validReview()&& object instanceof Review){
            review=(Review) object;
            result=this.reviewID==review.getReviewID();
        }
        return result;
    }

    public String toString() {
        return "UserID: "+userID+"\nReview: " + comment+"\n";
    }
}
