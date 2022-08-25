package it.ingsw.cinemates20_mobile.presenters;

import android.net.Uri;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

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
    public static MovieFilter filter;

    private static final String SUCCEDED = "Succeded";
    private static final String FAIL = "Fail";

    private final FilmFragment fragment;

    public FilmPresenter(@NonNull FilmFragment fragment) {
        super(fragment);

        this.fragment = fragment;

        fragment.getSearchMovieButton().setOnClickListener( v -> pressSearchButton() );
        fragment.getFilterButton().setOnClickListener( v -> pressSetFilterButton() );

        getPopularMovies();

        fragment.getFilmSearchEditText().getText().clear();
    }

    private  void pressSearchButton(){
        if(!isEmptyEditText(fragment.getFilmSearchEditText())){
            String movieName = String.valueOf(fragment.getFilmSearchEditText().getText());
            new SearchFilmTask(fragment).execute(movieName);
        }else if(filter != null){
            searchFilmByFilter();
        }
    }

    private void pressSetFilterButton(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container , new MovieFilterFragment()).commit();
    }

    private void getPopularMovies(){
        new PopularFilmSearchTask(fragment).execute();
    }

    private static class PopularFilmSearchTask extends AsyncTask<Void, Void, String> {
        private MovieResultsPage movieResultsPage;
        private final int NUMBER_OF_RESULT = 20;

        private final FilmFragment fragment;

        public PopularFilmSearchTask(FilmFragment fragment){
            super();
            this.fragment = fragment;
        }

        @Override
        protected String doInBackground(Void... param){
            final String[] result = new String[1];

            TmdbApi tmdbApi = new TmdbApi(fragment.getContext().getResources().getString(R.string.APIkey_the_movie_database));
            movieResultsPage = tmdbApi.getMovies().getPopularMovies(Locale.getDefault().getLanguage(), NUMBER_OF_RESULT);

            if(movieResultsPage != null){ result[0] = SUCCEDED; }
            else{ result[0] = FAIL; }

            return result[0];
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals(SUCCEDED))
                setFilmSearchResutlRecycleView(movieResultsPage, fragment);
        }
    }

    private static class SearchFilmTask extends AsyncTask<String, Void, String> {
        private MovieResultsPage movieResultsPage;

        private final FilmFragment fragment;

        public SearchFilmTask(FilmFragment fragment){
            super();
            this.fragment = fragment;
        }

        @Override
        protected String doInBackground(@NonNull String... filmName){
            final String[] result = new String[1];

            TmdbApi tmdbApi = new TmdbApi(fragment.getContext().getResources().getString(R.string.APIkey_the_movie_database));
            movieResultsPage = tmdbApi.getSearch().searchMovie(filmName[0], null, Locale.getDefault().getLanguage(), false, 0);
            if(movieResultsPage != null){ result[0] = SUCCEDED; }
            else{ result[0] = FAIL; }

            return result[0];
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals(SUCCEDED)){
                setFilmSearchResutlRecycleView(movieResultsPage, fragment);
            }
        }
    }

    private static void setFilmSearchResutlRecycleView(@NonNull MovieResultsPage movieResultsPage, @NonNull FilmFragment fragment){
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
        fragment.getFilmSearchResutlRecycleView().setAdapter(new MovieAdapter(fragment.getContext(), fragment.getParentFragmentManager(), movies));
        fragment.getFilmSearchResutlRecycleView().setLayoutManager(new LinearLayoutManager(fragment.getContext()));
    }

    private void searchFilmByFilter(){
        RequestCallback<List<Movie>> callback = new RequestCallback<List<Movie>>() {
            @Override
            public void onSuccess(@NonNull List<Movie> result) {
                fragment.getFilmSearchResutlRecycleView().setAdapter(new MovieAdapter(getContext(), getFragmentManager(), result));
                fragment.getFilmSearchResutlRecycleView().setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                error.printStackTrace();
            }
        };

        DAOFactory.getMovieDAO().searchFilmByFilter(getContext(), filter, callback);
    }
}