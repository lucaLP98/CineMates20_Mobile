package it.ingsw.cinemates20_mobile.presenters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.Locale;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.ProductionCountry;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.model.builder.MovieBuilder;
import it.ingsw.cinemates20_mobile.views.fragments.MovieInformationFragment;

public class MovieInformationPresenter extends FragmentPresenter{
    private final ImageView posterImage;
    private final TextView movieNameTextView;
    private final TextView movieDurationTextView;
    private final TextView movieYearTextView;
    private final TextView movieGenresTextView;
    private final TextView moviePlotTextView;
    private final TextView productionCountryTextView;
    private final TextView castTextView;

    private Movie movieFounded;

    public MovieInformationPresenter(@NonNull MovieInformationFragment fragment, @NonNull View inflate) {
        super(fragment);

        posterImage = inflate.findViewById(R.id.moviePosterImageView);
        movieNameTextView = inflate.findViewById(R.id.movietitleTextView);
        movieDurationTextView = inflate.findViewById(R.id.durationMovieTextView);
        movieYearTextView = inflate.findViewById(R.id.yearMovieTextView);
        movieGenresTextView = inflate.findViewById(R.id.genresMovieTextView);
        moviePlotTextView = inflate.findViewById(R.id.plotMovieTextView);
        productionCountryTextView = inflate.findViewById(R.id.countryMovieTextView);
        castTextView = inflate.findViewById(R.id.castTextView);
    }

    public void showMovieDetails(@NonNull Movie movie){
        new getMovieByIDTask().execute(movie.getMovieID());
    }

    private void setDataField(){
        if(movieFounded != null){
            String genres = "";
            for(Genre g : movieFounded.getGenres()){
                genres += g.getName() + " ";
            }

            String country = "";
            for(ProductionCountry c : movieFounded.getProductionCountry()){
                country += c.getName() + " ";
            }

            Glide
                    .with(getContext())
                    .load(movieFounded.getPosterUri())
                    .centerCrop()
                    .into(posterImage);

            movieNameTextView.setText(movieFounded.getTitle());
            movieDurationTextView.setText(String.valueOf(movieFounded.getDuration()));
            movieYearTextView.setText(movieFounded.getYears());
            movieGenresTextView.setText(genres);
            productionCountryTextView.setText(country);
            moviePlotTextView.setText(movieFounded.getPlot());
            castTextView.setText("");
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getMovieByIDTask extends AsyncTask<Integer, Void, String> {
        private final String SUCCEDED = "Succeded";
        private final String FAIL = "Fail";

        private MovieDb movieDetails;

        @Override
        protected String doInBackground(@NonNull Integer... param){
            final String[] result = new String[1];

            TmdbApi tmdbApi = new TmdbApi(getContext().getResources().getString(R.string.APIkey_the_movie_database));
            movieDetails = tmdbApi.getMovies().getMovie(param[0], Locale.getDefault().getLanguage());

            if(movieDetails != null){ result[0] = SUCCEDED; }
            else{ result[0] = FAIL; }

            return result[0];
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals(SUCCEDED)){
                Uri posterUri;

                if(movieDetails.getPosterPath() == null){
                    posterUri = null;
                }else{
                    posterUri = Uri.parse("http://image.tmdb.org/t/p/w185" + movieDetails.getPosterPath());
                }

                movieFounded = MovieBuilder.getBuilder(movieDetails.getId())
                        .title(movieDetails.getTitle())
                        .poster(posterUri)
                        .cast(movieDetails.getCast())
                        .crew(movieDetails.getCrew())
                        .years(movieDetails.getReleaseDate())
                        .duration(movieDetails.getRuntime())
                        .plot(movieDetails.getOverview())
                        .productionCountru(movieDetails.getProductionCountries())
                        .genres(movieDetails.getGenres())
                        .build();

                setDataField();
            }
        }
    }
}
