package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.ingsw.cinemates20_mobile.DAO.interfaces.UserDAO;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;
import it.ingsw.cinemates20_mobile.widgets.adapters.UsersAdapter;

public class UserDAOLambda implements UserDAO {
    private final String APIurl = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public void getUserdata(@NonNull CognitoUserSession userSession, Context context) {
        String url = APIurl + "/getuserdata?user_id=" + userSession.getUsername();
        final ThisUser[] user = new ThisUser[1];

        Response.Listener<JSONObject> listener = response -> {
            try {
                String name = response.getString("name");
                String surname = response.getString("surname");
                String email = response.getString("email");
                String nickname = response.getString("nickname");
                String uri_image = response.getString("uri_image");
                String biography = response.getString("biography");

                user[0] = ThisUser.createInstance(name, surname, nickname, email, userSession);
                user[0].setBiography(biography);
                if (!uri_image.equals("null")) {
                    user[0].setProfileImage(Uri.parse(uri_image));
                }
            } catch (JSONException e) {
                Log.d("JSONException", e.getLocalizedMessage());
            }
        };

        Response.ErrorListener errorListener = error -> Log.d("VolleyError", error.getLocalizedMessage());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public Boolean editUserData(String newName, String newSurname, String newNickname, String newBio, Context context) {
        final Boolean[] editCompleted = new Boolean[1];
        editCompleted[0] = true;
        String user_id = ThisUser.getInstance().getUserSession().getUsername();
        String url = APIurl + "/edituserdata?user_id=" + user_id + "&name=" + newName + "&surname=" + newSurname + "&nickname=" + newNickname + "&biography=" + newBio;

        Response.Listener<String> listener = response -> {
            Log.d("VolleySuccessEditUserData", "modifica con successo");
            editUserInstance(newName, newSurname, newNickname, newBio);
            editCompleted[0] = true;
        };

        Response.ErrorListener errorListener = error -> {
            Log.d("VolleyErrorEditUserData", error.getLocalizedMessage());
            editCompleted[0] = false;
        };

        StringRequest stringtRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringtRequest);

        return editCompleted[0];
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
        String url = APIurl + "/setprofileimageuri?user_id=" + userId + "&uri_image=" + imageUrl;

        Response.Listener<String> listener = response -> Log.d("VolleySuccessSetProfileImage", "immagine caricata con successo");

        Response.ErrorListener errorListener = error -> error.printStackTrace();

        StringRequest stringtRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringtRequest);
    }

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
    public void searchUsers(Context context, String userName, String userSurname, RecyclerView recyclerView){
        String url = APIurl + "/searchusers?";

        if(userSurname == null){
            url = url + "nickname="+userName;
        }else{
            url = url + "name="+userName+"&surname="+userSurname;
        }

        Log.d("URL", url);

        List<User> users = new ArrayList<>();

        Response.Listener<JSONObject>
                listener = response -> {
            try {
                String name, surname, nickname, uriImage, userID;
                JSONObject jsonObject;

                JSONArray jsonArray = response.getJSONArray("users");

                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);

                    userID = jsonObject.getString("user_id");
                    if(!userID.equals(ThisUser.getInstance().getUserID())){
                        name = jsonObject.getString("name");
                        surname = jsonObject.getString("surname");
                        nickname = jsonObject.getString("nickname");
                        uriImage = jsonObject.getString("uri_image");

                        users.add(new User(name, surname, nickname, Uri.parse(uriImage), userID));
                    }
                }

                recyclerView.setAdapter(new UsersAdapter(context, users));
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } catch (JSONException e) {
                Log.d("JSONException", e.getLocalizedMessage());
            }
        };

        Response.ErrorListener errorListener = error -> error.printStackTrace();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}