package comp3350.grs.objects;

public class Review {
    private String comment;

    public Review() {
        this.comment = null;
    }

    public  Review(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String toString() {
        return "Review: " + comment;
    }
}
