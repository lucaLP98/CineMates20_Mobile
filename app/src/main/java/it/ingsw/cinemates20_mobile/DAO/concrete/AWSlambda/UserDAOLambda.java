package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.ingsw.cinemates20_mobile.DAO.interfaces.UserDAO;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;

public class UserDAOLambda implements UserDAO {
    private final String APIurl = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public void getUserdata(@NonNull CognitoUserSession userSession, Context context, RequestCallback<Map<String, String>> callback) {
        String url = APIurl + "/getuserdata?user_id=" + userSession.getUsername();
        Map<String, String> userAttributes = new HashMap<>();

        Response.Listener<JSONObject> listener = response -> {
            try {
                userAttributes.put("name", response.getString("name"));
                userAttributes.put("surname", response.getString("surname"));
                userAttributes.put("email", response.getString("email"));
                userAttributes.put("nickname", response.getString("nickname"));
                userAttributes.put("uri_image", response.getString("uri_image"));
                userAttributes.put("biography", response.getString("biography"));

                callback.onSuccess(userAttributes);
            } catch (JSONException e) {
                Log.d("JSONException", e.getLocalizedMessage());
            }
        };

        Response.ErrorListener errorListener = error -> callback.onError(error);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void editUserData(String newName, String newSurname, String newNickname, String newBio, Context context) {
        String url = APIurl + "/edituserdata";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("user_id", ThisUser.getInstance().getUserID());
            requestBody.put("name", newName);
            requestBody.put("surname", newSurname);
            requestBody.put("nickname", newNickname);
            requestBody.put("biography", newBio);
        }catch (JSONException e){ e.printStackTrace(); }

        Response.Listener<JSONObject> listener = response -> editUserInstance(newName, newSurname, newNickname, newBio);

        Response.ErrorListener errorListener = error -> error.printStackTrace();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private void editUserInstance(String newName, String newSurname, String newNickname, String newBio) {
        ThisUser user = ThisUser.getInstance();

        user.setName(newName);
        user.setSurname(newSurname);
        user.setNickname(newNickname);
        user.setBiography(newBio);
    }

    private void setProfileImageIntoDatabase(String imageUrl, Context context){
        String userId = ThisUser.getInstance().getUserSession().getUsername();
        String url = APIurl + "/setprofileimageuri";

        JSONObject requestBody = new JSONObject();
        try{
            requestBody.put("user_id", userId);
            requestBody.put("uri_image", imageUrl);
        }catch (JSONException e){e.printStackTrace();}

        Response.Listener<JSONObject> listener = response -> Log.d("VolleySuccessSetProfileImage", "immagine caricata con successo");

        Response.ErrorListener errorListener = error -> error.printStackTrace();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void setProfileImage(Uri profileImagePath, Context context){
        MediaManager.get().upload(profileImagePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {

            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {

            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                String imageUrlString = (String)resultData.get("url");
                ThisUser.getInstance().setProfileImage(Uri.parse(imageUrlString));
                setProfileImageIntoDatabase(imageUrlString, context);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {

            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }

    @Override
    public void searchUsers(Context context, String userName, String userSurname, RequestCallback<List<User>> callback){
        String url = APIurl + "/searchusers?";

        if(userSurname == null){
            url = url + "nickname="+userName;
        }else{
            url = url + "name="+userName+"&surname="+userSurname;
        }

        List<User> users = new ArrayList<>();

        Response.Listener<JSONObject> listener = response -> {
            try {
                String name, surname, nickname, uriImage, userID;
                JSONObject jsonObject;

                JSONArray jsonArray = response.getJSONArray("users");

                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);

                    userID = jsonObject.getString("user_id");
                    name = jsonObject.getString("name");
                    surname = jsonObject.getString("surname");
                    nickname = jsonObject.getString("nickname");
                    uriImage = jsonObject.getString("uri_image");

                    users.add(new User(name, surname, nickname, Uri.parse(uriImage), userID));
                }

                callback.onSuccess(users);
            } catch (JSONException e) {
                Log.d("JSONException", e.getLocalizedMessage());
            }
        };

        Response.ErrorListener errorListener = error -> callback.onError(error);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}