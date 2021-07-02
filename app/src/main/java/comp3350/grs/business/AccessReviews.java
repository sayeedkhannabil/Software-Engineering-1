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

public class AccessReviews {
    private DataAccessI dataAccess;
    private List<Review> reviews;
    private Review currentReview;
    private int currentReviewIndex;

    public AccessReviews() {
        dataAccess = (DataAccessI) Services.getDataAccess(Main.dbName);
        reviews = null;
        currentReview = null;
        currentReviewIndex = 0;
    }

    //get a list of all reviews
    public List<Review> getReviews() {
        reviews = dataAccess.getAllReviews();
        return reviews;
    }

    // get next rating sequentially
    public Review getSequential() {
        if (reviews == null) {
            reviews = dataAccess.getAllReviews();
            currentReviewIndex = 0;
        }

        if (currentReviewIndex < reviews.size()) {
            currentReview = reviews.get(currentReviewIndex);
            currentReviewIndex++;
        }

        else {
            reviews = null;
            currentReview = null;
            currentReviewIndex = 0;
        }

        return currentReview;
    }

    public List<Review> getReviewsByGame(String gameName) {
        return dataAccess.getReviewsByGame(gameName);
    }

    public List<Review> getReviewsByUser(String userId) {
        return dataAccess.getReviewsByUser(userId);
    }

    public Review getReviewById(int id) {
        return dataAccess.getReviewByID(id);
    }

    public boolean insertReview(Review newReview) {
        boolean inserted = false;

        if (newReview != null) {
            inserted = dataAccess.insertReview(newReview);
        }

        return inserted;
    }

    // get the number of reviews in each game
    public int getReviewNumByGame(String gameName) {
        return dataAccess.getReviewsByGame(gameName).size();
    }

    public boolean updateReview(Review uReview) {
        boolean update = false;

        if (uReview != null) {
            update = dataAccess.updateReview(uReview);
        }

        return update;
    }

    public boolean deleteReview(Review dReview) {
        boolean del = false;

        if (dReview != null) {
            del = dataAccess.deleteReview(dReview);
        }

        return del;
    }

}
