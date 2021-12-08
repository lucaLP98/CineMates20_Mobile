package it.ingsw.cinemates20_mobile.presenters;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbGenre;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.core.NamedIdElement;
import info.movito.themoviedbapi.model.people.Person;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.MovieFilter;
import it.ingsw.cinemates20_mobile.views.fragments.MovieFilterFragment;

public class MovieFilterPresenter extends FragmentPresenter{
    private MovieFilter movieFilter;

    private final Spinner directorSpinner;
    private final Spinner genreSpinner;
    private final Spinner actorSpinner;
    private final EditText durationEditText;
    private final EditText yearEditText;

    private final String SUCCEDED = "Succeded";
    private final String FAIL = "Fail";

    public MovieFilterPresenter(MovieFilterFragment fragment, @NonNull View inflate, MovieFilter filter){
        super(fragment);

        movieFilter = filter;

        directorSpinner = inflate.findViewById(R.id.director_spinner);
        genreSpinner = inflate.findViewById(R.id.genre_spinner);
        yearEditText = inflate.findViewById(R.id.year_EditText);
        durationEditText = inflate.findViewById(R.id.duration_EditText);
        actorSpinner = inflate.findViewById(R.id.actor_spinner);

        new getGenresListTask().execute();
        new getPeopleListTask().execute();
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }

    private <T extends NamedIdElement> void setSpinnerResult(@NonNull Spinner spinner, List<T> items){
        ArrayAdapter<T> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @SuppressLint("StaticFieldLeak")
    private class getGenresListTask extends AsyncTask<Void, Void, String> {
        private List<Genre> genres;

        @Override
        protected String doInBackground(@NonNull Void... param){
            final String[] result = new String[1];

            TmdbGenre tmdbGenre = new TmdbApi(getContext().getResources().getString(R.string.APIkey_the_movie_database)).getGenre();
            genres = tmdbGenre.getGenreList(Locale.getDefault().getLanguage());

            if(genres != null){ result[0] = SUCCEDED; }
            else{ result[0] = FAIL; }

            return result[0];
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals(SUCCEDED)){
                setSpinnerResult(genreSpinner, genres);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getDirectorListTask extends AsyncTask<Void, Void, String> {
        private List<Genre> genres;

        @Override
        protected String doInBackground(@NonNull Void... param){
            final String[] result = new String[1];

            TmdbGenre tmdbGenre = new TmdbApi(getContext().getResources().getString(R.string.APIkey_the_movie_database)).getGenre();
            genres = tmdbGenre.getGenreList(Locale.getDefault().getLanguage());

            if(genres != null){ result[0] = SUCCEDED; }
            else{ result[0] = FAIL; }

            return result[0];
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals(SUCCEDED)){
                setSpinnerResult(genreSpinner, genres);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getPeopleListTask extends AsyncTask<Void, Void, String> {
        private TmdbPeople.PersonResultsPage search;

        @Override
        protected String doInBackground(@NonNull Void... param){
            final String[] result = new String[1];

            TmdbSearch tmdbSearch = new TmdbApi(getContext().getResources().getString(R.string.APIkey_the_movie_database)).getSearch();
            search = tmdbSearch.searchPerson("sylvester stallone", false, 10);

            if(search != null){ result[0] = SUCCEDED; }
            else{ result[0] = FAIL; }

            return result[0];
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals(SUCCEDED)){
                Log.d("testtest", "successo");

                search.forEach(new Consumer<Person>() {
                    @Override
                    public void accept(Person person) {
                        Log.d("testtest", "sono dentro");
                        Log.d("testtest", person.getName() + " " + person.getId());
                    }
                });
            }else{
                Log.d("testtest", "fail");
            }
        }
    }
}
