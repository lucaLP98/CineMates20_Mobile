package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ingsw.cinemates20_mobile.model.Review;

public interface ReviewDAO {

    public Integer publishNewMovieReview(Review newReview, Context context);

    public List<Review> getUserReviews(Context context, RecyclerView reviewsRecyclerView);

}
