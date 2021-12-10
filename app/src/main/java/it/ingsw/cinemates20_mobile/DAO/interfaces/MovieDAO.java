package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import java.util.List;

import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.model.MovieFilter;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;

public interface MovieDAO {
    void searchFilmByFilter(Context context, MovieFilter filter, RequestCallback<List<Movie>> callback);
}
