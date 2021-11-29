package it.ingsw.cinemates20_mobile.model;

public class Review {
    private final int reviewID;
    private final String userID;
    private final String reviewText;
    private final int reviewVote;
    private final int movieID;

    public Review(int reviewID, String userID, int movieID, String reviewText, int reviewVote){
        this.reviewID = reviewID;
        this.reviewText = reviewText;
        this.reviewVote = reviewVote;
        this.userID = userID;
        this.movieID = movieID;
    }

    public String getUserOwner(){
        return userID;
    }

    public int getMovieID(){
        return movieID;
    }

    public int getReviewVote(){
        return reviewVote;
    }

    public String getReviewText(){
        return reviewText;
    }

    public int getReviewID(){ return reviewID; }
}
