package it.ingsw.cinemates20_mobile.presenters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.Locale;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.Credits;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.ProductionCountry;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.model.builder.MovieBuilder;
import it.ingsw.cinemates20_mobile.views.fragments.MovieInformationFragment;
import it.ingsw.cinemates20_mobile.views.fragments.WriteReviewFragment;

public class MovieInformationPresenter extends FragmentPresenter{
    private final ImageView posterImage;
    private final TextView movieNameTextView;
    private final TextView movieDurationTextView;
    private final TextView movieYearTextView;
    private final TextView movieGenresTextView;
    private final TextView moviePlotTextView;
    private final TextView productionCountryTextView;
    private final TextView castTextView;
    private final TextView directorTextView;

    private Movie movie;

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
        directorTextView = inflate.findViewById(R.id.directorMovieTextView);
    }

    public void showMovieDetails(@NonNull Movie movie){
        new getMovieByIDTask().execute(movie.getMovieID());
    }

    public void pressWriteReviewButton(){
        if(User.getUserAuthenticated()){
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container, new WriteReviewFragment(movie)).commit();
        }else{
            showErrorMessage(getContext().getResources().getString(R.string.error_not_authenticated_label), getContext().getResources().getString(R.string.error_not_authenticated_msg));
        }
    }

    private void setDataField(){
        if(movie != null){
            String genres = "";
            for(Genre g : movie.getGenres()){
                genres += g.getName() + " ";
            }

            String country = "";
            for(ProductionCountry c : movie.getProductionCountry()){
                country += c.getName() + " ";
            }

            String cast = "";
            for(PersonCast c : movie.getCast()){
                cast += c.getName() + ", ";
            }
            cast = cast.substring(0, cast.length()-1);
            cast = cast.substring(0, cast.length()-1);

            Glide
                    .with(getContext())
                    .load(movie.getPosterUri())
                    .centerCrop()
                    .into(posterImage);

            int i=0;
            for (PersonCrew c : movie.getCrew()){
                if(c.getJob().equals("Director"))
                    break;
                i++;
            }
            String director = movie.getCrew().get(i).getName();

            movieNameTextView.setText(movie.getTitle());
            movieDurationTextView.setText(String.valueOf(movie.getDuration()));
            movieYearTextView.setText(movie.getYears());
            movieGenresTextView.setText(genres);
            productionCountryTextView.setText(country);
            moviePlotTextView.setText(movie.getPlot());
            castTextView.setText(cast);
            directorTextView.setText(director);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getMovieByIDTask extends AsyncTask<Integer, Void, String> {
        private final String SUCCEDED = "Succeded";
        private final String FAIL = "Fail";

        private MovieDb movieDetails;
        private Credits movieCredits;

        @Override
        protected String doInBackground(@NonNull Integer... param){
            final String[] result = new String[1];

            TmdbMovies tmdbMovies = new TmdbApi(getContext().getResources().getString(R.string.APIkey_the_movie_database)).getMovies();
            movieDetails = tmdbMovies.getMovie(param[0], Locale.getDefault().getLanguage());
            movieCredits = tmdbMovies.getCredits(param[0]);

            if(movieDetails != null && movieCredits != null){ result[0] = SUCCEDED; }
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

                movie = MovieBuilder.getBuilder(movieDetails.getId())
                        .title(movieDetails.getTitle())
                        .poster(posterUri)
                        .cast(movieCredits.getCast())
                        .crew(movieCredits.getCrew())
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
