package it.ingsw.cinemates20_mobile.presenters;

import android.net.Uri;
import android.os.AsyncTask;

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
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.model.builder.MovieBuilder;
import it.ingsw.cinemates20_mobile.views.fragments.MovieInformationFragment;
import it.ingsw.cinemates20_mobile.views.fragments.MovieReviewsListFragment;
import it.ingsw.cinemates20_mobile.views.fragments.WriteReviewFragment;

public class MovieInformationPresenter extends FragmentPresenter{
    private Movie movie;

    private final MovieInformationFragment fragment;

    public MovieInformationPresenter(@NonNull MovieInformationFragment fragment) {
        super(fragment);

        this.fragment = fragment;

        fragment.getViewReviewButton().setOnClickListener( v-> pressViewReviewsList() );
        fragment.getWriteReviewButton().setOnClickListener( v-> pressWriteReviewButton() );
        fragment.getToolbarMovieInformationFragment().setNavigationOnClickListener( v -> pressBackButton() );
    }

    public void showMovieDetails(int movieID){
        new getMovieByIDTask().execute(movieID);
    }

    private void pressWriteReviewButton(){
        if(ThisUser.getUserAuthenticated()){
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container, new WriteReviewFragment(movie)).commit();
        }else{
            showErrorMessage(getContext().getResources().getString(R.string.error_not_authenticated_label), getContext().getResources().getString(R.string.error_not_authenticated_msg));
        }
    }

    private void pressViewReviewsList(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container, new MovieReviewsListFragment(movie)).commit();
    }

    private void pressBackButton(){
        getFragmentManager().popBackStack();
    }

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

        private void setDataField(){
            if(movie != null){
                fragment.getToolbarMovieInformationFragment().setTitle(movie.getTitle());

                String genres = "";
                for(Genre g : movie.getGenres()){
                    genres = genres.concat(g.getName() + " ");
                }

                String country = "";
                for(ProductionCountry c : movie.getProductionCountry()){
                    country = country.concat(c.getName() + " ");
                }

                String cast = "";
                for(PersonCast c : movie.getCast()){
                    cast = cast.concat(c.getName() + ", ");
                }
                cast = cast.substring(0, cast.length()-1);
                cast = cast.substring(0, cast.length()-1);

                Glide
                        .with(getContext())
                        .load(movie.getPosterUri())
                        .centerCrop()
                        .into(fragment.getPosterImage());

                int i=0;
                for (PersonCrew c : movie.getCrew()){
                    if(c.getJob().equals("Director"))
                        break;
                    i++;
                }
                String director = movie.getCrew().get(i).getName();

                fragment.setMovieNameTextView(movie.getTitle());
                fragment.setMovieDurationTextView(String.valueOf(movie.getDuration()));
                fragment.setMovieYearTextView(movie.getYears());
                fragment.setMovieGenresTextView(genres);
                fragment.setProductionCountryTextView(country);
                fragment.setMoviePlotTextView(movie.getPlot());
                fragment.setCastTextView(cast);
                fragment.setDirectorTextView(director);
            }
        }
    }

    public void pressShareMovieWithFriendButton(){
        if(movie != null){
            new ShareMoviePresenter(getContext(), movie).shareMovie();
        }
    }
}
