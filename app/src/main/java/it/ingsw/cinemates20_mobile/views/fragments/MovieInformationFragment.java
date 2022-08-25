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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.presenters.MovieInformationPresenter;

public class MovieInformationFragment extends Fragment {
    private final int movieID;
    private MovieInformationPresenter movieInformationPresenter;

    private ImageView posterImage;

    private TextView movieNameTextView;
    private TextView movieDurationTextView;
    private TextView movieYearTextView;
    private TextView movieGenresTextView;
    private TextView moviePlotTextView;
    private TextView productionCountryTextView;
    private TextView castTextView;
    private TextView directorTextView;
    private Toolbar toolbarMovieInformationFragment;
    private Button writeReviewButton;
    private Button viewReviewButton;

    public MovieInformationFragment(int movieID){
        this.movieID = movieID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate =  inflater.inflate(R.layout.fragment_movie_information, container, false);

        posterImage = inflate.findViewById(R.id.moviePosterImageView);
        movieNameTextView = inflate.findViewById(R.id.movietitleTextView);
        movieDurationTextView = inflate.findViewById(R.id.durationMovieTextView);
        movieYearTextView = inflate.findViewById(R.id.yearMovieTextView);
        movieGenresTextView = inflate.findViewById(R.id.genresMovieTextView);
        moviePlotTextView = inflate.findViewById(R.id.plotMovieTextView);
        productionCountryTextView = inflate.findViewById(R.id.countryMovieTextView);
        castTextView = inflate.findViewById(R.id.castTextView);
        directorTextView = inflate.findViewById(R.id.directorMovieTextView);
        writeReviewButton = inflate.findViewById(R.id.writeReviewButton);
        viewReviewButton = inflate.findViewById(R.id.viewReviewButton);
        toolbarMovieInformationFragment = inflate.findViewById(R.id.toolbar_movie_information_fragment);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbarMovieInformationFragment);
        if(ThisUser.getUserAuthenticated()){ setHasOptionsMenu(true); }
        toolbarMovieInformationFragment.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        movieInformationPresenter = new MovieInformationPresenter(this);
        movieInformationPresenter.showMovieDetails(movieID);

        return inflate;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_movie_information_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.share_movie_toolbar_menu) {
            movieInformationPresenter.pressShareMovieWithFriendButton();
            return true;
        }

        return false;
    }

    public int getMovieID() {
        return movieID;
    }

    public ImageView getPosterImage() {
        return posterImage;
    }

    public Button getWriteReviewButton() {
        return writeReviewButton;
    }

    public Button getViewReviewButton() {
        return viewReviewButton;
    }

    public Toolbar getToolbarMovieInformationFragment() {
        return toolbarMovieInformationFragment;
    }

    public void setMovieNameTextView(String movieName){
        movieNameTextView.setText(movieName);
    }

    public void setMovieGenresTextView(String genres){
        movieGenresTextView.setText(genres);
    }

    public void setMovieDurationTextView(String duration){
        movieDurationTextView.setText(duration);
    }

    public void setMovieYearTextView(String movieYear){
        movieYearTextView.setText(movieYear);
    }

    public void setProductionCountryTextView(String productionCountry){
        productionCountryTextView.setText(productionCountry);
    }

    public void setMoviePlotTextView(String moviePlot){
        moviePlotTextView.setText(moviePlot);
    }

    public void setCastTextView(String cast){
        castTextView.setText(cast);
    }

    public void setDirectorTextView(String director){
        directorTextView.setText(director);
    }
}