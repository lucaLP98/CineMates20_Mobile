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
import java.util.concurrent.ExecutionException;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.core.NamedIdElement;
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

    public MovieFilterPresenter(MovieFilterFragment fragment, @NonNull View inflate){
        super(fragment);

        directorEditText = inflate.findViewById(R.id.director_EditText);
        genreSpinner = inflate.findViewById(R.id.genre_spinner);
        yearEditText = inflate.findViewById(R.id.year_EditText);
        durationEditText = inflate.findViewById(R.id.duration_EditText);
        actorEditText = inflate.findViewById(R.id.actor_EditText);

        new getGenresListTask().execute();
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }

    public MovieFilter pressSetFilter() {
        Integer duration = null;
        Integer actorID = null;
        Integer directorID = null;
        Integer year = null;

        Genre selectedGenre = (Genre)genreSpinner.getSelectedItem();
        Integer genre = selectedGenre.getId();

        if(!isEmptyEditText(durationEditText))
            duration = Integer.parseInt(String.valueOf(durationEditText.getText()));

        if(!isEmptyEditText(actorEditText)){
            String actor =  String.valueOf(actorEditText.getText()).toLowerCase();
            try {
                actorID = new getPeopleListTask().execute(actor).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(!isEmptyEditText(directorEditText)){
            String director =  String.valueOf(directorEditText.getText()).toLowerCase();
            try {
                directorID = new getPeopleListTask().execute(director).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(!isEmptyEditText(yearEditText))
            year =  Integer.parseInt(String.valueOf(yearEditText.getText()));

        return MovieFilterBuilder.getBuilder()
                .actorID(actorID)
                .directorID(directorID)
                .year(year)
                .runtime(duration)
                .genreID(genre)
                .build();
    }

    private <T extends NamedIdElement> void setSpinnerResult(@NonNull Spinner spinner, @NonNull List<T> items){
        ArrayAdapter<T> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @SuppressLint("StaticFieldLeak")
    private class getGenresListTask extends AsyncTask<Void, Void, String> {
        private List<Genre> genres;

        @Override
        protected String doInBackground(@NonNull Void... param){
            genres = new TmdbApi(getContext()
                    .getResources()
                    .getString(R.string.APIkey_the_movie_database))
                    .getGenre()
                    .getGenreList(Locale.getDefault().getLanguage());

            if(genres != null) return "success";
            return "fail";
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals("success")) setSpinnerResult(genreSpinner, genres);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getPeopleListTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(@NonNull String... param){
            TmdbPeople.PersonResultsPage search = new TmdbApi(getContext()
                    .getResources()
                    .getString(R.string.APIkey_the_movie_database))
                    .getSearch()
                    .searchPerson(param[0], false, 1);

            return (search != null) ? search.getResults().get(0).getId() : -1;
        }
    }
}
