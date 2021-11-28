package it.ingsw.cinemates20_mobile.DAO.concrete.AWSlambda;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import it.ingsw.cinemates20_mobile.DAO.interfaces.ReviewDAO;
import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.utilities.RequestQueueSingleton;

public class ReviewDAOlambda implements ReviewDAO {
    private final String APIurl = "https://g66whp96o7.execute-api.us-east-2.amazonaws.com/cinemates20_API";

    @Override
    public Boolean publishNewMovieReview(@NonNull Review newReview, Context context){
        final Boolean[] publishCompleted = new Boolean[1];
        String url = APIurl + "/insertnewreview?user_id=" + newReview.getUserOwner() + "&description=" + newReview.getReviewText() + "&vote=" + newReview.getReviewVote() + "&id_film=" + newReview.getMovieID();

        Response.Listener<String> listener = response -> {
            Log.d("VolleyError", "recensione publicata con successo");
            publishCompleted[0] = true;
        };

        Response.ErrorListener errorListener = error -> {
            Log.d("VolleyError", error.getMessage()+"");
            publishCompleted[0] = false;
        };

        StringRequest stringtRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringtRequest);

        return publishCompleted[0];
    }
}
