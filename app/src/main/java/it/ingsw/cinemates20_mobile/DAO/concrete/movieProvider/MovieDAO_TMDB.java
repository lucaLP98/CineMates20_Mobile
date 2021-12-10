package it.ingsw.cinemates20_mobile.DAO.concrete.movieProvider;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.ingsw.cinemates20_mobile.DAO.interfaces.MovieDAO;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.model.MovieFilter;
import it.ingsw.cinemates20_mobile.model.builder.MovieBuilder;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;

public class MovieDAO_TMDB implements MovieDAO {
    @Override
    public void searchFilmByFilter(Context context, MovieFilter filter, RequestCallback<List<Movie>> callback) {
        if(filter != null){
            String url = "https://api.themoviedb.org/3/discover/movie?api_key="+context.getResources().getString(R.string.APIkey_the_movie_database)+
                    "&language="+ Locale.getDefault().getLanguage() + "&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&with_watch_monetization_types=flatrate";

            url = buildFilterSearchUrl(filter, url);

            Response.Listener<JSONObject> listener = response -> {
                try {
                    JSONObject jsonObject;
                    int movieID;
                    String movieTitle, moviePosterURI;
                    List<Movie> movies = new ArrayList<>();

                    JSONArray jsonArray = response.getJSONArray("results");

                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);

                        movieID = jsonObject.getInt("id");
                        movieTitle = jsonObject.getString("title");
                        moviePosterURI = "http://image.tmdb.org/t/p/w185" + jsonObject.getString("poster_path");

                        movies.add(MovieBuilder.getBuilder(movieID)
                                .title(movieTitle)
                                .poster(Uri.parse(moviePosterURI))
                                .build());
                    }

                    callback.onSuccess(movies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            };

            Response.ErrorListener errorListener = error -> callback.onError(error);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
            RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        }
    }

    private String buildFilterSearchUrl(@NonNull MovieFilter filter, @NonNull String url){
        if(filter.getActorID() != null){
            url = url + "&with_cast=" + filter.getActorID();
        }

        if(filter.getDirectorID() != null){
            url = url + "&with_crew=" + filter.getDirectorID();
        }

        if(filter.getYear() != null){
            url = url + "&year=" + filter.getYear();
        }

        if(filter.getGenreID() != null){
            url = url + "&with_genres=" + filter.getGenreID();
        }

        if(filter.getRuntime() != null){
            url = url + "&with_runtime.lte=" + filter.getRuntime();
        }

        return url;
    }
}
