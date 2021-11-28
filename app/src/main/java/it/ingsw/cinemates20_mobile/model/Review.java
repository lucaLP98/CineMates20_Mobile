package it.ingsw.cinemates20_mobile.model;

public class Review {
    private final String userID;
    private final String reviewText;
    private final Integer reviewVote;
    private final Integer movieID;

    public Review(String userID, Integer movieID, String reviewText, Integer reviewVote){
        this.reviewText = reviewText;
        this.reviewVote = reviewVote;
        this.userID = userID;
        this.movieID = movieID;
    }

    public String getUserOwner(){
        return userID;
    }

    public Integer getMovieID(){
        return movieID;
    }

    public Integer getReviewVote(){
        return reviewVote;
    }

    public String getReviewText(){
        return reviewText;
    }
}
