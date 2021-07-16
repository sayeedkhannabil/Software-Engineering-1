// CLASS: AccessReviews
//
// Author: Abu Sayeed Khan
//
// REMARKS: This is the business object for the Review class. It allows for access to the databases.
//-----------------------------------------


package comp3350.grs.business;

import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.objects.Review;
import comp3350.grs.persistence.DataAccessI;
//business object of review
public class AccessReviews {
    private DataAccessI dataAccessI;
    private List<Review> reviewList;
    private Review currentReview;
    private int currentReviewIndex;

    public AccessReviews() {
        dataAccessI = Services.getDataAccess(Main.dbName);
        reviewList = null;
        currentReview = null;
        currentReviewIndex = 0;
    }

    public void clear(){
        dataAccessI.clearReviews();
    }

    //get a list of all reviews
    public List<Review> getAllReviews() {
        reviewList = dataAccessI.getAllReviews();
        return reviewList;
    }

    // get next review sequentially
    public Review getSequential() {
        if (reviewList == null) {
            getAllReviews();
            currentReviewIndex = 0;
        }

        if (currentReviewIndex < reviewList.size()) {
            currentReview = reviewList.get(currentReviewIndex);
            currentReviewIndex++;
        }

        else {
            reviewList = null;
            currentReview = null;
            currentReviewIndex = 0;
        }

        return currentReview;
    }

    public List<Review> getReviewsByGame(String gameName) {
        return dataAccessI.getReviewsByGame(gameName);
    }

    public List<Review> getReviewsByUser(String userId) {
        return dataAccessI.getReviewsByUser(userId);
    }

    public Review getReviewById(int id) {
        return dataAccessI.getReviewByID(id);
    }

    public boolean insertReview(Review newReview) {
        return dataAccessI.insertReview(newReview);
    }

    // get the number of reviews in each game
    public int getReviewNumByGame(String gameName) {
        return dataAccessI.getReviewsByGame(gameName).size();
    }

    public boolean updateReview(Review uReview) {
        return dataAccessI.updateReview(uReview);
    }

    public boolean deleteReview(Review dReview) {
        return dataAccessI.deleteReview(dReview);
    }

}
