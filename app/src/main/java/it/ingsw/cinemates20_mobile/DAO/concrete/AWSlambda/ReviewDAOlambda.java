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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.ingsw.cinemates20_mobile.DAO.interfaces.ReviewDAO;
import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;
import it.ingsw.cinemates20_mobile.widgets.adapters.ReviewsAdapter;

public class ReviewDAOlambda implements ReviewDAO {
    private final String APIurl = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public Integer publishNewMovieReview(@NonNull Review newReview, Context context){
        Integer[] responseCode = new Integer[1];
        String url = APIurl + "/insertnewreview?user_id=" + newReview.getUserOwner() + "&vote=" + newReview.getReviewVote() + "&id_film=" + newReview.getMovieID() + "&description=" + newReview.getReviewText();

        Response.Listener<String> listener = response -> {
            Log.d("VolleySuccessPostReviews", responseCode[0]+": Recensione publicata con successo");
            responseCode[0] = 200;


        };

        Response.ErrorListener errorListener = error -> {
            Log.d("VolleyErrorPostReviews", ""+error.networkResponse.statusCode);
            responseCode[0] = error.networkResponse.statusCode;
        };

        StringRequest stringtRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringtRequest);

        return responseCode[0];
    }

    @Override
    public List<Review> getUserReviews(Context context, RecyclerView reviewsRecyclerView) {
        String url = APIurl + "/getreviewbyuser?user_id=" + User.getInstance().getUserID();
        List<Review> reviews = new ArrayList<>();

        Response.Listener<JSONObject> listener = response -> {
            try {
                JSONObject jsonObject;
                String reviewsText, userID;
                int vote, reviewID, movieID;

                JSONArray jsonArray = response.getJSONArray("reviews");

                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);

                    reviewID = jsonObject.getInt("id_review");
                    userID = jsonObject.getString("user_owner");
                    movieID = jsonObject.getInt("id_film");
                    reviewsText = jsonObject.getString("description");
                    vote = jsonObject.getInt("vote");

                    reviews.add(new Review(reviewID, userID, movieID, reviewsText, vote));
                }

                reviewsRecyclerView.setAdapter(new ReviewsAdapter(context, reviews));
                reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } catch (JSONException e) {
                Log.d("JSONException", e.getLocalizedMessage());
            }
        };

        Response.ErrorListener errorListener = error -> Log.d("VolleyErrorGetUserReviews", error.getLocalizedMessage());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

        return reviews;
    }
}
