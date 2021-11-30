package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.presenters.MovieReviewsPresenter;

public class MovieReviewsListFragment extends Fragment {
    private Movie movie;
    private MovieReviewsPresenter movieReviewsPresenter;

    public MovieReviewsListFragment(Movie movie){
        this.movie = movie;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_movie_reviews_list, container, false);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_movie_reviews_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(movie.getTitle());

        movieReviewsPresenter = new MovieReviewsPresenter(this, inflate, movie);
        movieReviewsPresenter.setReviewsRecyclerView();
        inflate.findViewById(R.id.backMovieReviewsFragmentButton).setOnClickListener(v->movieReviewsPresenter.pressBackButton());

        return inflate;
    }
}