package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import it.ingsw.cinemates20_mobile.DAO.interfaces.UserDAO;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;

public class UserDAOLambda implements UserDAO {
    private final String APIurl = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public User getUserdata(@NonNull CognitoUserSession userSession, Context context) {
        String url = APIurl + "/getuserdata?user_id=" + userSession.getUsername();
        final User[] user = new User[1];

        Response.Listener<JSONObject> listener = response -> {
            try {
                String name = response.getString("name");
                String surname = response.getString("surname");
                String email = response.getString("email");
                String nickname = response.getString("nickname");
                String uri_image = response.getString("uri_image");
                String biography = response.getString("biography");

                user[0] = User.createInstance(name, surname, nickname, email, userSession);
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

        return user[0];
    }

    public Boolean editUserData(String newName, String newSurname, String newNickname, String newBio, Context context) {
        final Boolean[] editCompleted = new Boolean[1];
        editCompleted[0] = true;
        String user_id = User.getInstance().getUserSession().getUsername();
        String url = APIurl + "/edituserdata?user_id=" + user_id + "&name=" + newName + "&surname=" + newSurname + "&nickname=" + newNickname + "&biography=" + newBio;

        Response.Listener<String> listener = response -> {
            Log.d("VolleyError", "modifica con successo");
            editUserInstance(newName, newSurname, newNickname, newBio);
            editCompleted[0] = true;
        };

        Response.ErrorListener errorListener = error -> {
            Log.d("VolleyError", error.getLocalizedMessage());
            editCompleted[0] = false;
        };

        StringRequest stringtRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringtRequest);

        return editCompleted[0];
    }

    private void editUserInstance(String newName, String newSurname, String newNickname, String newBio) {
        User user = User.getInstance();

        user.setName(newName);
        user.setSurname(newSurname);
        user.setNickname(newNickname);
        user.setBiography(newBio);
    }
}