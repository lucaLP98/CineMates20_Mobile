package it.ingsw.cinemates20_mobile.model;

public class Review {
    private final int reviewID;
    private final String userID;
    private String reviewText;
    private int reviewVote;
    private final int movieID;
    private final String filmTitle;
    private final String filmPosterUri;

    public Review(int reviewID, String userID, int movieID, String reviewText, int reviewVote, String filmTitle, String filmPosterUri){
        this.reviewID = reviewID;
        this.reviewText = reviewText;
        this.reviewVote = reviewVote;
        this.userID = userID;
        this.movieID = movieID;
        this.filmTitle = filmTitle;
        this.filmPosterUri = filmPosterUri;
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

    public String getFilmPosterUri(){
        return filmPosterUri;
    }

    public String getFilmTitle(){ return filmTitle;}

    public void setReviewVote(int vote){
        reviewVote = vote;
    }

    public void setReviewText(String text){
        reviewText = text;
    }
}
