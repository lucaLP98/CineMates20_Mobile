package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.fragments.WriteReviewFragment;

public class WriteReviewPresenter extends FragmentPresenter{
    private final EditText voteEditText;
    private final EditText reviewEditText;
    private final Movie movie;

    public WriteReviewPresenter(WriteReviewFragment fragment, @NonNull View inflate, @NonNull Movie movie){
        super(fragment);

        this.movie = movie;

        voteEditText = inflate.findViewById(R.id.reviewVoteEditTextNumber);
        reviewEditText = inflate.findViewById(R.id.reviewTextEditTextTextMultiLine);
    }

    public void pressCancellButton(){
        getFragmentManager().popBackStack();
    }

    public void pressPublishReviewButton(){
        if(isEmptyEditText(voteEditText) || isEmptyEditText(reviewEditText)){
            showErrorMessage(getContext().getResources().getString(R.string.error_review_label),getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        int vote = Integer.parseInt(String.valueOf(voteEditText.getText()));
        String text = String.valueOf(reviewEditText.getText());

        if(vote < 0 || vote > 100){
            showErrorMessage(getContext().getResources().getString(R.string.error_review_label),getContext().getResources().getString(R.string.error_not_valid_review_vote));
            return;
        }

        publishReview(vote, text);
    }

    private void publishReview(Integer vote, String text) {
        Review newReview = new Review(0, ThisUser.getInstance().getUserID(), movie.getMovieID(), text, vote, movie.getTitle(), movie.getPosterUri().toString());

        RequestCallback<String> requestCallback = new RequestCallback<String>() {
            @Override
            public void onSuccess(@NonNull String result) {
                showSuccessMessage(getContext().getResources().getString(R.string.success_review_publish_label),getContext().getResources().getString(R.string.success_review_publish));
                getFragmentManager().popBackStack("MOVIE_CARD", 0);
            }

            @Override
            public void onError(@NonNull VolleyError error) {
                if(error.networkResponse.statusCode == 461)
                    showErrorMessage(getContext().getResources().getString(R.string.error_review_publish), getContext().getResources().getString(R.string.error_review_already_posted));

                error.printStackTrace();
            }
        };

        DAOFactory.getReviewDao().publishNewMovieReview(newReview, getContext(), requestCallback);
    }

    public void setImageViewPoster(ImageView poster){
        Glide
                .with(getContext())
                .load(movie.getPosterUri())
                .centerCrop()
                .into(poster);
    }
}
