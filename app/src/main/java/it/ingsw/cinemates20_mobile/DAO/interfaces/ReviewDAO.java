package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ingsw.cinemates20_mobile.model.Review;

public interface ReviewDAO {

    public Integer publishNewMovieReview(Review newReview, Context context);

    public void getMovieReviews(Context context, int movieID, RecyclerView reviewsRecyclerView);

}
