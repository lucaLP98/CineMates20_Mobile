package it.ingsw.cinemates20_mobile.presenters;

import androidx.annotation.NonNull;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.views.fragments.MovieReviewsListFragment;

public class MovieReviewsPresenter extends FragmentPresenter{
    private final Movie movie;
    private final MovieReviewsListFragment fragment;

    public MovieReviewsPresenter(@NonNull MovieReviewsListFragment fragment, Movie movie) {
        super(fragment);

        this.fragment = fragment;
        this.movie = movie;

        setReviewsRecyclerView();
    }

    private void setReviewsRecyclerView(){
        DAOFactory.getReviewDao().getMovieReviews(getContext(), movie.getMovieID(), fragment.getReviewMovieList());
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }
}
