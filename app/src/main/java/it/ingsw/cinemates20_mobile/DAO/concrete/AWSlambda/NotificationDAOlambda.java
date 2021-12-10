package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.interfaces.NotificationDAO;
import it.ingsw.cinemates20_mobile.model.Notification;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;

public class NotificationDAOlambda implements NotificationDAO {
    private final String APIurl = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public void getNotificationsList(Context context, RequestCallback<List<Notification>> callback) {
        String url = APIurl + "/getnotificationslist?user_id=" + ThisUser.getInstance().getUserID();

        Response.Listener<JSONObject> listener = response -> {
            List<Notification> notifications = new ArrayList<>();

            try {
                JSONObject jsonObject;
                String notificationText, type;
                int notificationID, movieID;

                JSONArray jsonArray = response.getJSONArray("notifications");

                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);

                    notificationID = jsonObject.getInt("id_notification");
                    notificationText = jsonObject.getString("text");
                    type = jsonObject.getString("notification_type");

                    if(type.equals(Notification.notificationTypeEnum.SHARING.toString())){
                        movieID = jsonObject.getInt("id_film");
                        notifications.add(new Notification(notificationID, notificationText, Notification.notificationTypeEnum.valueOf(type), movieID));
                    }else{
                        notifications.add(new Notification(notificationID, notificationText, Notification.notificationTypeEnum.valueOf(type)));
                    }
                }

                callback.onSuccess(notifications);
            } catch (JSONException e) {
                Log.d("JSONException", "errore recupero notifiche");
            }
        };

        Response.ErrorListener errorListener = error -> callback.onError(error);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
