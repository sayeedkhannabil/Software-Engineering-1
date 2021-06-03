package comp3350.grs.objects;

public class Feedback {
    private Rating rate;
    private Review comment;

    public Feedback() {
        this.rate = null;
        this.comment = null;
    }

    public Feedback(Rating rate, Review comment) {
        this.rate = rate;
        this.comment = comment;
    }

    public Rating getRate() {
        return rate;
    }

    public Review getReview() {
        return comment;
    }

    public String toString() {
        return "Rating: " + this.rate + "\nReview: " + this.comment;
    }
}
