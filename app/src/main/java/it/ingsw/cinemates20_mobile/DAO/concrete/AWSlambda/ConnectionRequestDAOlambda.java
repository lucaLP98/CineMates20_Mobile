package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.interfaces.ConnectionRequestDAO;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ConnectionRequest;
import it.ingsw.cinemates20_mobile.model.Notification;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.CurrentDate;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;

public class ConnectionRequestDAOlambda implements ConnectionRequestDAO {
    private final String apiUrl = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public void getConnectionRequests(@NonNull Context context,@NonNull RequestCallback<List<ConnectionRequest>>  callback){
        String url = apiUrl + "/getconnectionrequests?user_id="+ ThisUser.getInstance().getUserID();

        Response.Listener<JSONObject> listener = response -> {
            List<ConnectionRequest> connectionRequestList = new ArrayList<>();

            try {
                JSONObject jsonObject;
                String userName, userSurname, userNickname, userID, userURIimage;
                int requestID;

                JSONArray jsonArray = response.getJSONArray("requests");

                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);

                    requestID = jsonObject.getInt("id_requests");
                    userID = jsonObject.getString("user_id");
                    userName = jsonObject.getString("name");
                    userSurname = jsonObject.getString("surname");
                    userNickname = jsonObject.getString("nickname");
                    userURIimage = jsonObject.getString("uri_profile_image");

                    connectionRequestList.add(new ConnectionRequest(new User(userName, userSurname, userNickname, Uri.parse(userURIimage), userID), requestID));
                }

                callback.onSuccess(connectionRequestList);
            } catch (JSONException e) {
                Log.d("JSONException", "errore recupero richieste");
            }
        };

        Response.ErrorListener errorListener = Throwable::printStackTrace;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void sendConnecctionRequests(@NonNull Context context, @NonNull String userReceiverID){
        String url = apiUrl + "/sendrequest";

        String messageNotification = ThisUser.getInstance().getNickname() + " " + context.getResources().getString(R.string.connection_request_sended_notify_msg) + "  " + CurrentDate.getInstance().getCurrentDate();
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("userSender", ThisUser.getInstance().getUserID());
            requestBody.put("userReceiver", userReceiverID);
            requestBody.put("notifyType", Notification.notificationTypeEnum.REQUEST.toString());
            requestBody.put("notifyMsg", messageNotification);
        }catch (JSONException e){e.printStackTrace();}

        Response.Listener<JSONObject> listener = response -> {};

        Response.ErrorListener errorListener = error -> {};

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void respondToConnectionRequest(@NonNull Context context, int requestID,@NonNull String sender, int respond){
        String url = apiUrl + "/respondtoconnectionrequest";
        String notifyType;
        String notifyText = ThisUser.getInstance().getNickname();

        if(respond == 1){
            notifyType = Notification.notificationTypeEnum.ACCEPT.toString();
            notifyText = notifyText + " " + context.getResources().getString(R.string.connection_request_accepted) + "  " + CurrentDate.getInstance().getCurrentDate();
        }else{
            notifyType = Notification.notificationTypeEnum.DECLINE.toString();
            notifyText = notifyText + " " + context.getResources().getString(R.string.connection_request_refused) + "  " + CurrentDate.getInstance().getCurrentDate();
        }

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("request_id", requestID);
            requestBody.put("sender", sender);
            requestBody.put("receiver",ThisUser.getInstance().getUserID());
            requestBody.put("requestResponse", String.valueOf(respond));
            requestBody.put("notifyType", notifyType);
            requestBody.put("notifyText", notifyText);
        }catch (JSONException e){ e.printStackTrace();}

        Response.Listener<JSONObject> listener = response -> Log.d("VoleyRequestRespondoToConnectionRequest", "Request response success");

        Response.ErrorListener errorListener = error -> Log.d("VoleyRequestRespondoToConnectionRequest", "Request response error");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
