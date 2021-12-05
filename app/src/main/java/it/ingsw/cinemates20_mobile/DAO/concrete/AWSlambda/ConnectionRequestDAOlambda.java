package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.interfaces.ConnectionRequestDAO;
import it.ingsw.cinemates20_mobile.model.ConnectionRequest;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;

public class ConnectionRequestDAOlambda implements ConnectionRequestDAO {
    private final String apiUrl = " https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public void getConnectionRequests(Context context, RequestCallback callback){
        String url = apiUrl + "/getconnectionrequests?user_id="+ ThisUser.getInstance().getUserID();

        Response.Listener<JSONObject> listener = response -> {
            List<ConnectionRequest> connectionRequestList = new ArrayList<>();

            try {
                JSONObject jsonObject;
                String userName, userSurname, userNickname, userID, userURIimage;
                int requestID;

                JSONArray jsonArray = response.getJSONArray("reviews");

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

        Response.ErrorListener errorListener = error -> error.printStackTrace();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void sendConnecctionRequests(Context context, String userReceiverID, RequestCallback<String> callback){
        String url = apiUrl + "/sendconnectionrequest?";

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
