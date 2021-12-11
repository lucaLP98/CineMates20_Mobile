package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import it.ingsw.cinemates20_mobile.DAO.interfaces.SharingMovieDAO;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.model.Notification;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.utilities.CurrentDate;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;


public class SharingMovieDAOlamda implements SharingMovieDAO {
    private final String APIurl = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public void shareFilm(@NonNull Context context, @NonNull Movie movie, @NonNull String friendID, RequestCallback<JSONObject> callback){
        String url = APIurl + "/sharemoviewhitfriend";

        String notifyText = ThisUser.getInstance().getNickname()+" "+context.getResources().getString(R.string.movie_shared)+" "+movie.getTitle()+"  "+CurrentDate.getInstance().getCurrentDate();
        JSONObject requestBody = new JSONObject();
        try{
            requestBody.put("movie_id", String.valueOf(movie.getMovieID()));
            requestBody.put("user_sender", ThisUser.getInstance().getUserID());
            requestBody.put("user_receiver", friendID);
            requestBody.put("notification_type", Notification.notificationTypeEnum.SHARING.toString());
            requestBody.put("notification_text", notifyText);
        }catch (JSONException e){ e.printStackTrace(); }

        Response.Listener<JSONObject> listner = response ->callback.onSuccess(response);

        Response.ErrorListener errorListener = error -> callback.onError(error);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody, listner, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
