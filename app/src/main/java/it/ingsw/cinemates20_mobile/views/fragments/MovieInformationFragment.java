package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.presenters.MovieInformationPresenter;

public class MovieInformationFragment extends Fragment {
    private final Movie movie;

    public MovieInformationFragment(Movie movie){
        this.movie = movie;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate =  inflater.inflate(R.layout.fragment_movie_information, container, false);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_movie_information_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(movie.getTitle());
        setHasOptionsMenu(true);

        MovieInformationPresenter movieInformationPresenter = new MovieInformationPresenter(this, inflate);
        movieInformationPresenter.showMovieDetails(movie);
        inflate.findViewById(R.id.writeReviewButton).setOnClickListener( v-> movieInformationPresenter.pressWriteReviewButton() );

        return inflate;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_movie_information_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean ret;

        switch (item.getItemId()) {
            case R.id.share_movie_toolbar_menu:
                ret = true;

                break;

            default:
                ret = false;
                break;
        }

        return ret;
    }
}