package it.ingsw.cinemates20_mobile.presenters;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.model.MovieFilter;
import it.ingsw.cinemates20_mobile.model.builder.MovieBuilder;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.FilmFragment;
import it.ingsw.cinemates20_mobile.views.fragments.MovieFilterFragment;
import it.ingsw.cinemates20_mobile.widgets.adapters.MovieAdapter;

public class FilmPresenter extends FragmentPresenter{
    private final RecyclerView filmSearchResutlRecycleView;
    private final EditText filmSearchEditText;

    public static MovieFilter filter;

    public FilmPresenter(@NonNull FilmFragment fragment, @NonNull View inflate) {
        super(fragment);

        filmSearchResutlRecycleView = inflate.findViewById(R.id.film_search_result_Recycle_view);
        filmSearchEditText = inflate.findViewById(R.id.searchFilmEditText);

        filmSearchEditText.getText().clear();
    }

    public void pressSearchButton(){
        if(!isEmptyEditText(filmSearchEditText)){
            String movieName = String.valueOf(filmSearchEditText.getText());
            new SearchFilmTask().execute(movieName);
        }else if(filter != null){
            searchFilmByFilter();
        }
    }

    public void pressSetFilterButton(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container , new MovieFilterFragment()).commit();
    }

    public void getPopularMovies(){
        new PopularFilmSearchTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class PopularFilmSearchTask extends AsyncTask<Void, Void, String> {
        private final String SUCCEDED = "Succeded";
        private final String FAIL = "Fail";

        private MovieResultsPage movieResultsPage;
        private final int numberOfResult = 20;

        @Override
        protected String doInBackground(Void... param){
            final String[] result = new String[1];

            TmdbApi tmdbApi = new TmdbApi(getContext().getResources().getString(R.string.APIkey_the_movie_database));
            movieResultsPage = tmdbApi.getMovies().getPopularMovies(Locale.getDefault().getLanguage(), numberOfResult);

            if(movieResultsPage != null){ result[0] = SUCCEDED; }
            else{ result[0] = FAIL; }

            return result[0];
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals(SUCCEDED))
                setFilmSearchResutlRecycleView(movieResultsPage);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SearchFilmTask extends AsyncTask<String, Void, String> {
        private final String SUCCEDED = "Succeded";
        private final String FAIL = "Fail";

        private MovieResultsPage movieResultsPage;

        @Override
        protected String doInBackground(@NonNull String... filmName){
            final String[] result = new String[1];

            TmdbApi tmdbApi = new TmdbApi(getContext().getResources().getString(R.string.APIkey_the_movie_database));
            movieResultsPage = tmdbApi.getSearch().searchMovie(filmName[0], null, Locale.getDefault().getLanguage(), false, 0);
            if(movieResultsPage != null){ result[0] = SUCCEDED; }
            else{ result[0] = FAIL; }

            return result[0];
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals(SUCCEDED)){
                setFilmSearchResutlRecycleView(movieResultsPage);
            }
        }
    }

    private void setFilmSearchResutlRecycleView(@NonNull MovieResultsPage movieResultsPage){
        List<Movie> movies = new ArrayList<>();

        movieResultsPage.forEach( movieDb -> {
            Uri posterUri;

            if(movieDb.getPosterPath() == null){
                posterUri = null;
            }else{
                posterUri = Uri.parse("http://image.tmdb.org/t/p/w185" + movieDb.getPosterPath());
            }

            movies.add(MovieBuilder.getBuilder(movieDb.getId())
                    .title(movieDb.getTitle())
                    .poster(posterUri)
                    .build()
            );
        });
        filmSearchResutlRecycleView.setAdapter(new MovieAdapter(getContext(), getFragmentManager(), movies));
        filmSearchResutlRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void searchFilmByFilter(){
        RequestCallback<List<Movie>> callback = new RequestCallback<List<Movie>>() {
            @Override
            public void onSuccess(@NonNull List<Movie> result) {
                filmSearchResutlRecycleView.setAdapter(new MovieAdapter(getContext(), getFragmentManager(), result));
                filmSearchResutlRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                error.printStackTrace();
            }
        };

        DAOFactory.getMovieDAO().searchFilmByFilter(getContext(), filter, callback);
    }
}