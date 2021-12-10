package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

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

    public void shareFilm(@NonNull Context context, @NonNull Movie movie, @NonNull String friendID, RequestCallback<String> callback){
        String url = APIurl + "/sharemoviewhitfriend?movie_id="+movie.getMovieID()+"&user_sender="+ThisUser.getInstance().getUserID()+
                "&user_receiver="+friendID+"&notification_type="+Notification.notificationTypeEnum.SHARING.toString()+"&notification_text="+
                ThisUser.getInstance().getNickname()+" "+context.getResources().getString(R.string.movie_shared)+" "+movie.getTitle()+"  "+
                CurrentDate.getInstance().getCurrentDate();

        Response.Listener<String> listner = response ->callback.onSuccess(response);

        Response.ErrorListener errorListener = error -> callback.onError(error);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listner, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
