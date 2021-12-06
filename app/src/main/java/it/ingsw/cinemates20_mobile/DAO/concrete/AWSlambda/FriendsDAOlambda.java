package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.interfaces.FriendsDAO;
import it.ingsw.cinemates20_mobile.model.Friend;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;

public class FriendsDAOlambda implements FriendsDAO {
    private final String APIurl = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public void getFriendsList(Context context, RequestCallback<List<Friend>> callback) {
        String url = APIurl + "/getfriendslist?user_id=" + ThisUser.getInstance().getUserID();

        Response.Listener<JSONObject> listener = response -> {
            List<Friend> friends = new ArrayList<>();

            try {
                JSONObject jsonObject;
                String friendName, friendSurname, friendUserID, friendNickname;
                Uri friendImageUri;
                int friendshipID;

                JSONArray jsonArray = response.getJSONArray("friends");

                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);

                    friendName = jsonObject.getString("name");
                    friendSurname = jsonObject.getString("surname");
                    friendUserID = jsonObject.getString("friend_id");
                    friendNickname = jsonObject.getString("nickname");
                    friendshipID = jsonObject.getInt("firendship_id");
                    friendImageUri = Uri.parse(jsonObject.getString("friend_uri_image"));

                    friends.add(new Friend(friendName, friendSurname, friendNickname, friendUserID, friendImageUri, friendshipID));
                }

                callback.onSuccess(friends);
            } catch (JSONException e) {
                Log.d("JSONException", "errore recupero lista amici");
            }
        };

        Response.ErrorListener errorListener = error -> callback.onError(error);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void deleteFriend(Context context, int friendshipID){
        String url = APIurl + "/deletefriend?friendship_id=" + friendshipID;

        Response.Listener<String> listner = response -> {
            Log.d("VolleyRequestDeleteFriend", response);
        };

        Response.ErrorListener errorListener = error -> {
            error.printStackTrace();
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listner, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
