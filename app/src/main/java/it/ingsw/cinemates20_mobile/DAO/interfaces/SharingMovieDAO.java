package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;

public interface SharingMovieDAO {
    void shareFilm(@NonNull Context context, @NonNull Movie movie, @NonNull String friendID, RequestCallback<JSONObject> callback);
}
