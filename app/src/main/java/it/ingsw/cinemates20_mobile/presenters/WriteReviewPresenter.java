package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.model.Review;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.views.fragments.WriteReviewFragment;

public class WriteReviewPresenter extends FragmentPresenter{
    private final EditText voteEditText;
    private final EditText reviewEditText;
    private final Movie movie;

    public WriteReviewPresenter(WriteReviewFragment fragment, @NonNull View inflate, Movie movie){
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
        Review newReview = new Review(0, User.getInstance().getUserID(), movie.getMovieID(), text, vote);
        DAOFactory.getReviewDao().publishNewMovieReview(newReview, getContext());
        showSuccessMessage(getContext().getResources().getString(R.string.success_review_publish_label),getContext().getResources().getString(R.string.success_review_publish));
        getFragmentManager().popBackStack("MOVIE_CARD", 0);
    }
}
