package comp3350.grs.objects;

public class Feedback {
    private Rating rating;
    private Review review;
    private boolean isReview;

    // default feedback
    public Feedback() {
        this.rating = null;
        this.review = null;
    }

    //only leaving a rating
    public Feedback(Rating newRating)
    {
        rating = newRating;
        review = null;
        isReview = false;
    }

    //leaving a rating and review
    public Feedback(Rating newRating, Review newReview) {
        this.rating = newRating;
        this.review = newReview;
        isReview = true;
    }


    public boolean validFeedback() {
        if (this.rating.getRating() > 0 && this.rating.getRating() <= 5) {
            return true;
        }

        return false;
    }

    public boolean isReview()
    {
        return isReview;
    }

    public Rating getRating() {
        return rating;
    }

    public Review getReview() {
        return review;
    }

    public String toString() {
        String toReturn = rating.toString();
        if(isReview)
        {
            toReturn = rating.toString() + "\n" + review.toString();
        }
        return toReturn;
    }
}
