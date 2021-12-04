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
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;
import it.ingsw.cinemates20_mobile.widgets.adapters.ReviewsAdapter;
import it.ingsw.cinemates20_mobile.widgets.adapters.UserReviewsAdapter;

public class ReviewDAOlambda implements ReviewDAO {
    private final String APIurl = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public void publishNewMovieReview(@NonNull Review newReview, Context context, RequestCallback requestCallback){
        String url = APIurl + "/insertnewreview?user_id=" + newReview.getUserOwner() + "&vote=" + newReview.getReviewVote() + "&id_film=" + newReview.getMovieID()
                + "&film_poster=" + newReview.getFilmPosterUri() + "&film_title=" + newReview.getFilmTitle() + "&description=" + newReview.getReviewText();

        Response.Listener<String> listener = response -> {
            Log.d("VolleySuccessPostReviews", "Recensione publicata con successo");
            requestCallback.onSuccess(response);
        };

        Response.ErrorListener errorListener = error -> {
            Log.d("VolleyErrorPostReviews", ""+error.networkResponse.statusCode);
            requestCallback.onError(error);
        };

        StringRequest stringtRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringtRequest);
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
    public void getUserReviews(Context context, String userID, RecyclerView reviewsRecyclerView) {
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

                reviewsRecyclerView.setAdapter(new UserReviewsAdapter(context, reviews));
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
    public void deleteUserReviews(Context context, int reviewID){
        String url = APIurl + "/deletereview?review_id=" + reviewID;

        Response.Listener<String> listener = response -> Log.d("VolleySuccessDeleteReviews", "Recensione eliminata con successo");

        Response.ErrorListener errorListener = error -> Log.d("VolleyErrorDeleteReviews", ""+error.networkResponse.statusCode);

        StringRequest stringtRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringtRequest);
    }

    @Override
    public void editUserReviews(Context context, int reviewID, int vote, String body){
        String url = APIurl + "/editreview?review_id=" + reviewID +"&vote=" + vote + "&description=" + body;

        Response.Listener<String> listener = response -> Log.d("VolleySuccessEditReviews", "Recensione eliminata con successo");

        Response.ErrorListener errorListener = error -> Log.d("VolleyErrorEditReviews", ""+error.networkResponse.statusCode);

        StringRequest stringtRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringtRequest);
    }
}
