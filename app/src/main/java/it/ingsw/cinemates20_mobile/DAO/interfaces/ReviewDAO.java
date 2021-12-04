package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;

public interface ReviewDAO {

    void publishNewMovieReview(Review newReview, Context context, RequestCallback<?> requestCallback);

    void getMovieReviews(Context context, int movieID, RecyclerView reviewsRecyclerView);

    void getUserReviews(Context context, String userID, RecyclerView reviewsRecyclerView);

    void deleteUserReviews(Context context, int reviewID);

    void editUserReviews(Context context, int reviewID, int vote, String body);
}
