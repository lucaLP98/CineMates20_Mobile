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
import it.ingsw.cinemates20_mobile.model.MovieFilter;
import it.ingsw.cinemates20_mobile.presenters.MovieFilterPresenter;

public class MovieFilterFragment extends Fragment {
    private MovieFilter filter;
    private MovieFilterPresenter movieFilterPresenter;

    public MovieFilterFragment(MovieFilter filter){
        this.filter = filter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_movie_filter, container, false);

        movieFilterPresenter = new MovieFilterPresenter(this, inflate);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_movie_filter_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.set_movie_filter);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener( v -> movieFilterPresenter.pressBackButton() );

        inflate.findViewById(R.id.set_filter_button).setOnClickListener( v -> {
            filter = movieFilterPresenter.pressSetFilter();
            getActivity().getSupportFragmentManager().popBackStack();
        });

        return inflate;
    }
}