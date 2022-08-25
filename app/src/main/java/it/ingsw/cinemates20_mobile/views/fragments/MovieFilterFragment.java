package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.MovieFilterPresenter;

public class MovieFilterFragment extends Fragment {
    private EditText directorEditText;
    private Spinner genreSpinner;
    private EditText actorEditText;
    private EditText durationEditText;
    private EditText yearEditText;

    private Button setFilterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_movie_filter, container, false);

        directorEditText = inflate.findViewById(R.id.director_EditText);
        genreSpinner = inflate.findViewById(R.id.genre_spinner);
        yearEditText = inflate.findViewById(R.id.year_EditText);
        durationEditText = inflate.findViewById(R.id.duration_EditText);
        actorEditText = inflate.findViewById(R.id.actor_EditText);
        setFilterButton = inflate.findViewById(R.id.set_filter_button);

        MovieFilterPresenter movieFilterPresenter = new MovieFilterPresenter(this);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_movie_filter_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.set_movie_filter);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener( v -> movieFilterPresenter.pressBackButton() );

        return inflate;
    }

    public EditText getDirectorEditText() {
        return directorEditText;
    }

    public Spinner getGenreSpinner() {
        return genreSpinner;
    }

    public EditText getActorEditText() {
        return actorEditText;
    }

    public EditText getDurationEditText() {
        return durationEditText;
    }

    public EditText getYearEditText() {
        return yearEditText;
    }

    public Button getSetFilterButton() {
        return setFilterButton;
    }
}