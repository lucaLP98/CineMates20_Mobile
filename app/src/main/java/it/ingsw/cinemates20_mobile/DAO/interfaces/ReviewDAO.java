package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import it.ingsw.cinemates20_mobile.model.Review;

public interface ReviewDAO {

    Integer publishNewMovieReview(Review newReview, Context context);

    void getMovieReviews(Context context, int movieID, RecyclerView reviewsRecyclerView);

    void getUserReviews(Context context, String userID, RecyclerView reviewsRecyclerView);

    void deleteUserReviews(Context context, int reviewID);
}
