package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import it.ingsw.cinemates20_mobile.model.Review;

public interface ReviewDAO {

    public Boolean publishNewMovieReview(Review newReview, Context context);

}
