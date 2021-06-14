package comp3350.grs.objects;

import comp3350.grs.business.AccessUsers;

public class Review {
    private String comment;
    private User user;

    public Review() {
        this.comment = null;
    }

    public  Review(String comment) {
        user = AccessUsers.getActiveUser();
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public String toString() {
        return "UserID: "+user.getUserID()+"Review: " + comment;
    }
}
