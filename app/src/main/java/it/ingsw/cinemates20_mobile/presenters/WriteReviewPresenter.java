package it.ingsw.cinemates20_mobile.presenters;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.WriteReviewFragment;

public class WriteReviewPresenter extends FragmentPresenter{
    private final Movie movie;
    private final WriteReviewFragment fragment;

    public WriteReviewPresenter(@NonNull WriteReviewFragment fragment, @NonNull Movie movie){
        super(fragment);

        this.movie = movie;
        this.fragment = fragment;

        fragment.getCancellPublishReviewButton().setOnClickListener( v -> pressCancellButton() );
        fragment.getPublishReviewButton().setOnClickListener( v -> pressPublishReviewButton() );
        setImageViewPoster(fragment.getPoster());
    }

    private void pressCancellButton(){
        getFragmentManager().popBackStack();
    }

    private void pressPublishReviewButton(){
        if(isEmptyEditText(fragment.getVoteEditText()) || isEmptyEditText(fragment.getReviewEditText())){
            showErrorMessage(getContext().getResources().getString(R.string.error_review_label),getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        int vote = Integer.parseInt(String.valueOf(fragment.getVoteEditText().getText()));
        String text = String.valueOf(fragment.getReviewEditText().getText());

        if(vote < 0 || vote > 100){
            showErrorMessage(getContext().getResources().getString(R.string.error_review_label),getContext().getResources().getString(R.string.error_not_valid_review_vote));
            return;
        }

        publishReview(vote, text);
    }

    private void publishReview(Integer vote, String text) {
        Review newReview = new Review(0, ThisUser.getInstance().getUserID(), movie.getMovieID(), text, vote, movie.getTitle(), movie.getPosterUri().toString());

        RequestCallback<JSONObject> requestCallback = new RequestCallback<JSONObject>() {
            @Override
            public void onSuccess(@NonNull JSONObject result) {
                showSuccessMessage(getContext().getResources().getString(R.string.success_review_publish_label),getContext().getResources().getString(R.string.success_review_publish));
                getFragmentManager().popBackStack("MOVIE_CARD", 0);
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                if(error.networkResponse.statusCode == 461)
                    showErrorMessage(getContext().getResources().getString(R.string.error_review_publish), getContext().getResources().getString(R.string.error_review_already_posted));
            }
        };

        DAOFactory.getReviewDao().publishNewMovieReview(newReview, getContext(), requestCallback);
    }

    private void setImageViewPoster(ImageView poster){
        Glide
                .with(getContext())
                .load(movie.getPosterUri())
                .centerCrop()
                .into(poster);
    }
}
