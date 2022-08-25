package it.ingsw.cinemates20_mobile.presenters;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
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
    private final MovieFilterFragment fragment;

    public MovieFilterPresenter(MovieFilterFragment fragment){
        super(fragment);

        this.fragment = fragment;

        fragment.getSetFilterButton().setOnClickListener( v -> {
            FilmPresenter.filter = pressSetFilter();
            getFragmentManager().popBackStack();
        });

        new GetGenresListTask(fragment).execute();
    }

    public void pressBackButton(){
        getFragmentManager().popBackStack();
    }

    private MovieFilter pressSetFilter() {
        Integer duration = null;
        Integer actorID = null;
        Integer directorID = null;
        Integer year = null;

        Genre selectedGenre = (Genre)fragment.getGenreSpinner().getSelectedItem();
        Integer genre = selectedGenre.getId();

        if(!isEmptyEditText(fragment.getDurationEditText()))
            duration = Integer.parseInt(String.valueOf(fragment.getDurationEditText().getText()));

        if(!isEmptyEditText(fragment.getActorEditText())){
            String actor =  String.valueOf(fragment.getActorEditText().getText()).toLowerCase();
            try {
                actorID = new getPeopleListTask(getContext()).execute(actor).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(!isEmptyEditText(fragment.getDirectorEditText())){
            String director =  String.valueOf(fragment.getDirectorEditText().getText()).toLowerCase();
            try {
                directorID = new getPeopleListTask(getContext()).execute(director).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(!isEmptyEditText(fragment.getYearEditText()))    year =  Integer.parseInt(String.valueOf(fragment.getYearEditText().getText()));

        return MovieFilterBuilder.getBuilder()
                .actorID(actorID)
                .directorID(directorID)
                .year(year)
                .runtime(duration)
                .genreID(genre)
                .build();
    }

    private static class GetGenresListTask extends AsyncTask<Void, Void, String> {
        private List<Genre> genres;

        private final MovieFilterFragment fragment;

        public GetGenresListTask(MovieFilterFragment fragment){
            super();
            this.fragment = fragment;
        }

        @Override
        protected String doInBackground(@NonNull Void... param){
            genres = new TmdbApi(fragment.getContext()
                    .getResources()
                    .getString(R.string.APIkey_the_movie_database))
                    .getGenre()
                    .getGenreList(Locale.getDefault().getLanguage());

            if(genres != null) return "success";
            return "fail";
        }

        private <T extends NamedIdElement> void setSpinnerResult(@NonNull Spinner spinner, @NonNull List<T> items){
            ArrayAdapter<T> adapter = new ArrayAdapter<>(fragment.getContext(), android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

        @Override
        protected void onPostExecute (@NonNull String result){
            if(result.equals("success")) setSpinnerResult(fragment.getGenreSpinner(), genres);
        }
    }

    private static class getPeopleListTask extends AsyncTask<String, Void, Integer> {
        private final Context context;

        public getPeopleListTask(Context context){
            super();
            this.context = context;
        }

        @Override
        protected Integer doInBackground(@NonNull String... param){
            TmdbPeople.PersonResultsPage search = new TmdbApi(context
                    .getResources()
                    .getString(R.string.APIkey_the_movie_database))
                    .getSearch()
                    .searchPerson(param[0], false, 1);

            return (search != null) ? search.getResults().get(0).getId() : -1;
        }
    }
}
