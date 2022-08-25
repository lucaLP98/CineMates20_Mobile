package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.FilmPresenter;

public class FilmFragment extends Fragment {
    public static final String filmFragmentLabel = "film_fragment";

    private RecyclerView filmSearchResutlRecycleView;
    private EditText filmSearchEditText;
    private Button searchMovieButton;
    private Button filterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View inflate = inflater.inflate(R.layout.fragment_film, container, false);

        filmSearchResutlRecycleView = inflate.findViewById(R.id.film_search_result_Recycle_view);
        filmSearchEditText = inflate.findViewById(R.id.searchFilmEditText);
        searchMovieButton = inflate.findViewById(R.id.search_movie_button);
        filterButton = inflate.findViewById(R.id.filter_button);

        FilmPresenter filmPresenter = new FilmPresenter(this);

        return inflate;
    }

    public RecyclerView getFilmSearchResutlRecycleView() {
        return filmSearchResutlRecycleView;
    }

    public EditText getFilmSearchEditText() {
        return filmSearchEditText;
    }

    public Button getSearchMovieButton() {
        return searchMovieButton;
    }

    public Button getFilterButton() {
        return filterButton;
    }
}