package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.views.fragments.MovieReviewsListFragment;

public class MovieReviewsPresenter extends FragmentPresenter{
    private Button backButton;
    private RecyclerView reviewMovieList;
    private Movie movie;

    public MovieReviewsPresenter(@NonNull MovieReviewsListFragment fragment, @NonNull View inflate, Movie movie) {
        super(fragment);

        this.reviewMovieList = inflate.findViewById(R.id.movieReviewsRecyclerView);
        this.backButton = inflate.findViewById(R.id.backMovieReviewsFragmentButton);
        this.movie = movie;
    }

    public void setReviewsRecyclerView(){
        DAOFactory.getReviewDao().getMovieReviews(getContext(), movie.getMovieID(), reviewMovieList);
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }
}
