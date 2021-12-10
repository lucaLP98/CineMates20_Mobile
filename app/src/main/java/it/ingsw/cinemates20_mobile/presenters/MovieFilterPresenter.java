package it.ingsw.cinemates20_mobile.presenters;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbGenre;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.core.NamedIdElement;
import info.movito.themoviedbapi.model.people.Person;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.MovieFilter;
import it.ingsw.cinemates20_mobile.model.builder.MovieFilterBuilder;
import it.ingsw.cinemates20_mobile.views.fragments.MovieFilterFragment;

public class MovieFilterPresenter extends FragmentPresenter{
    private final EditText directorEditText;
    private final Spinner genreSpinner;
    private final EditText actorEditText;
    private final EditText durationEditText;
    private final EditText yearEditText;
    private final EditText movieNameEditTExt;

    private final String SUCCEDED = "Succeded";
    private final String FAIL = "Fail";

    private Integer actorID;
    private Integer directorID;

    public MovieFilterPresenter(MovieFilterFragment fragment, @NonNull View inflate){
        super(fragment);

        directorEditText = inflate.findViewById(R.id.director_EditText);
        genreSpinner = inflate.findViewById(R.id.genre_spinner);
        yearEditText = inflate.findViewById(R.id.year_EditText);
        durationEditText = inflate.findViewById(R.id.duration_EditText);
        actorEditText = inflate.findViewById(R.id.actor_EditText);
        movieNameEditTExt = inflate.findViewById(R.id.movie_name_filterFragment_EditText);

        new getGenresListTask().execute();
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }

    public MovieFilter pressSetFilter(){
        Integer duration = null;
        String actor = null;
        String director = null;
        Integer year = null;
        Integer genre;
        String movieName = null;

        Genre selectedGenre = (Genre)genreSpinner.getSelectedItem();
        genre = selectedGenre.getId();

        if(!isEmptyEditText(durationEditText))
            duration = Integer.parseInt(String.valueOf(durationEditText.getText()));

        if(!isEmptyEditText(actorEditText)){
            actor =  String.valueOf(actorEditText.getText()).toLowerCase();
            new getPeopleListTask().execute(actor, "ACTOR");
        }

        if(!isEmptyEditText(directorEditText)){
            director =  String.valueOf(directorEditText.getText()).toLowerCase();
            new getPeopleListTask().execute(director, "DIRECTOR");
        }

        if(!isEmptyEditText(movieNameEditTExt)){
            movieName =  String.valueOf(movieNameEditTExt.getText()).toLowerCase();
        }

        if(!isEmptyEditText(yearEditText))
            year =  Integer.parseInt(String.valueOf(yearEditText.getText()));

        MovieFilter movieFilter = MovieFilterBuilder.getBuilder()
                .title(movieName)
                .actorID(actorID)
                .directorID(directorID)
                .year(year)
                .runtime(duration)
                .genreID(genre)
                .build();

        return movieFilter;
    }

    private <T extends NamedIdElement> void setSpinnerResult(@NonNull Spinner spinner, @NonNull List<T> items){
        ArrayAdapter<T> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @SuppressLint("StaticFieldLeak")
    private class getGenresListTask extends AsyncTask<Void, Void, String> {
        private List<Genre> genres = null;

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
    private class getPeopleListTask extends AsyncTask<String, Void, String> {
        private TmdbPeople.PersonResultsPage search;
        private String searchType;

        @Override
        protected String doInBackground(@NonNull String... param){
            final String[] result = new String[1];

            TmdbSearch tmdbSearch = new TmdbApi(getContext().getResources().getString(R.string.APIkey_the_movie_database)).getSearch();
            search = tmdbSearch.searchPerson(param[0], false, 1);

            if(search != null){
                result[0] = SUCCEDED;
                searchType = param[1];
            }
            else{ result[0] = FAIL; }

            return result[0];
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals(SUCCEDED)){

                List<Person> list = search.getResults();
                if(searchType.equals("ACTOR"))
                    actorID = list.get(0).getId();
                else
                    directorID = list.get(0).getId();
            }
        }
    }
}
