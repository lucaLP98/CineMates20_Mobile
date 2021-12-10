package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.FilmPresenter;

public class FilmFragment extends Fragment {
    public static final String filmFragmentLabel = "film_fragment";

    private FilmPresenter filmPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View inflate = inflater.inflate(R.layout.fragment_film, container, false);

        filmPresenter = new FilmPresenter(this, inflate);
        filmPresenter.getPopularMovies();
        inflate.findViewById(R.id.search_movie_button).setOnClickListener( v -> filmPresenter.pressSearchButton() );
        inflate.findViewById(R.id.filter_button).setOnClickListener( v -> filmPresenter.pressSetFilterButton() );

        return inflate;
    }
}