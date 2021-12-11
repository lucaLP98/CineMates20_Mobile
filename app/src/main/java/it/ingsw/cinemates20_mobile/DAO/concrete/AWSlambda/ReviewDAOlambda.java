package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.interfaces.ReviewDAO;
import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;
import it.ingsw.cinemates20_mobile.widgets.adapters.ReviewsAdapter;

public class ReviewDAOlambda implements ReviewDAO {
    private final String APIurl = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public void publishNewMovieReview(@NonNull Review newReview, Context context, RequestCallback<JSONObject> requestCallback){
        String url = APIurl + "/insertnewreview";

        JSONObject bodyRequest = new JSONObject();
        try {
            bodyRequest.put("user_id", newReview.getUserOwner());
            bodyRequest.put("vote", newReview.getReviewVote());
            bodyRequest.put("id_film", newReview.getMovieID());
            bodyRequest.put("film_poster", newReview.getFilmPosterUri());
            bodyRequest.put("film_title", newReview.getFilmTitle());
            bodyRequest.put("description", newReview.getReviewText());
        } catch (JSONException e) { e.printStackTrace(); }

        Response.Listener<JSONObject> listener = response -> requestCallback.onSuccess(response);

        Response.ErrorListener errorListener = error -> requestCallback.onError(error);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, bodyRequest, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void getMovieReviews(Context context, int movieID, RecyclerView reviewsRecyclerView) {
        String url = APIurl + "/getreviewbyfilm?film_id=" + movieID;

        Response.Listener<JSONObject> listener = response -> {
            List<Review> reviews = new ArrayList<>();
            List<String> usersNickname = new ArrayList<>();
            List<Uri> userProfileImages = new ArrayList<>();

            try {
                JSONObject jsonObject;
                String reviewsText, userID, userNickname, userImage;
                int vote, reviewID;

                JSONArray jsonArray = response.getJSONArray("reviews");

                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);

                    reviewID = jsonObject.getInt("id_review");
                    vote = jsonObject.getInt("vote");
                    reviewsText = jsonObject.getString("description");
                    userID = jsonObject.getString("user_owner");
                    userNickname = jsonObject.getString("user_nickname");
                    userImage = jsonObject.getString("user_image");

                    reviews.add(new Review(reviewID, userID, movieID, reviewsText, vote, null, null));
                    usersNickname.add(userNickname);
                    userProfileImages.add(Uri.parse(userImage));
                }

                reviewsRecyclerView.setAdapter(new ReviewsAdapter(context, reviews, usersNickname, userProfileImages));
                reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } catch (JSONException e) {
                Log.d("JSONException", "errore recupero recensioni");
            }
        };

        Response.ErrorListener errorListener = error -> Log.d("VolleyErrorGetUserReviews", ""+error.getMessage());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void getUserReviews(Context context, RequestCallback<List<Review>> callback){
        String userID = ThisUser.getInstance().getUserID();
        String url = APIurl + "/getreviewbyuser?user_id=" + userID;

        Response.Listener<JSONObject> listener = response -> {
            List<Review> reviews = new ArrayList<>();

            try {
                JSONObject jsonObject;
                String reviewsText, movieTitle, moviePoster;
                int vote, reviewID, movieID;

                JSONArray jsonArray = response.getJSONArray("reviews");

                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);

                    reviewID = jsonObject.getInt("id_review");
                    vote = jsonObject.getInt("vote");
                    reviewsText = jsonObject.getString("description");
                    movieID = jsonObject.getInt("id_film");
                    movieTitle = jsonObject.getString("film_title");
                    moviePoster = jsonObject.getString("film_poster");

                    reviews.add(new Review(reviewID, userID, movieID, reviewsText, vote, movieTitle, moviePoster));
                }

                callback.onSuccess(reviews);
            } catch (JSONException e) {
                Log.d("JSONException", "errore recupero recensioni");
            }
        };

        Response.ErrorListener errorListener = error -> callback.onError(error);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void deleteUserReviews(Context context, int reviewID){
        String url = APIurl + "/deletereview?review_id=" + reviewID;

        Response.Listener<String> listener = response -> Log.d("VolleySuccessDeleteReviews", "Recensione eliminata con successo");

        Response.ErrorListener errorListener = error -> Log.d("VolleyErrorDeleteReviews", ""+error.networkResponse.statusCode);

        StringRequest stringtRequest = new StringRequest(Request.Method.DELETE, url, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringtRequest);
    }

    @Override
    public void editUserReviews(Context context, int reviewID, int vote, String body){
        String url = APIurl + "/editreview";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("review_id", String.valueOf(reviewID));
            requestBody.put("vote", String.valueOf(vote));
            requestBody.put("description", body);
        }catch (JSONException e){ e.printStackTrace(); }

        Response.Listener<JSONObject> listener = response -> Log.d("VolleyEditReviews", "Recensione modificata con successo");

        Response.ErrorListener errorListener = error -> Log.d("VolleyEditReviews", "errore modifica recensione: "+error.networkResponse.statusCode);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
