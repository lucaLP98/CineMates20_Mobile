package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import it.ingsw.cinemates20_mobile.DAO.interfaces.UserDAO;
import it.ingsw.cinemates20_mobile.model.User;

public class UserDAOLambda implements UserDAO {
    @Override
    public User getUserdata(CognitoUserSession userSession, Context context){
        String url = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/getUserData_test2/getuserdata?user_id="+userSession.getUsername();
        RequestQueue queue = Volley.newRequestQueue(context);
        final User[] user = new User[1];

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String name = response.getString("name");
                    String surname = response.getString("surname");
                    String email = response.getString("email");
                    String nickname = response.getString("nickname");
                    String uri_image = response.getString("uri_image");
                    String biography = response.getString("biography");

                    user[0] = User.createInstance(name, surname, nickname, email, userSession);
                    user[0].setBiography(biography);
                    if(!uri_image.equals("null")){
                        user[0].setProfileImage(Uri.parse(uri_image));
                    }
                }catch(JSONException e){
                    Log.d("JSONException", e.getLocalizedMessage());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VolleyError", error.getLocalizedMessage());
            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(jsonObjectRequest);

        return user[0];
    }

    public void editUserData(String newName, String newSurname, String newNickname, String newBio){
        User user = User.getInstance();

        user.setName(newName);
        user.setSurname(newSurname);
        user.setNickname(newNickname);
        user.setBiography(newBio);

        //crea JSON con nuovi dati
        //richiama API per richiamare lambda (MODIFICA NEL DATABASE)
        //modifica utente in COGNITO USER POOL
    }
}
