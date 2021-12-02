package it.ingsw.cinemates20_mobile.presenters;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Review;

public class EditReviewPresenter{
    private final Review review;
    private  EditText voteEditText;
    private  EditText bodyEditText;
    private final Context context;

    public EditReviewPresenter(@NonNull Review review, Context context) {
        this.context = context;
        this.review = review;

        setUpEditText();
    }

    public boolean pressSaveChangesReviewbutton(){
        if(String.valueOf(voteEditText.getText()).equals("") || String.valueOf(bodyEditText.getText()).equals("")){
            return false;
        }

        int vote = Integer.parseInt(String.valueOf(voteEditText.getText()));

        if(vote < 0 || vote > 100){
            return false;
        }

        review.setReviewText(String.valueOf(bodyEditText.getText()));
        review.setReviewVote(Integer.parseInt(String.valueOf(voteEditText.getText())));

        editReview();

        return true;
    }

    private void editReview(){
        DAOFactory.getReviewDao().editUserReviews(context, review.getReviewID(), review.getReviewVote(), review.getReviewText());
    }

    private void setUpEditText(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(20, 10,20, 10);

        bodyEditText = new EditText(context);
        bodyEditText.setBackground(context.getResources().getDrawable(R.drawable.textfield_rounded_corner, null));
        bodyEditText.setLayoutParams(params);
        bodyEditText.setText(review.getReviewText());

        voteEditText= new EditText(context);
        voteEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        voteEditText.setBackground(context.getResources().getDrawable(R.drawable.textfield_rounded_corner, null));
        voteEditText.setLayoutParams(params);
        voteEditText.setText(String.valueOf(review.getReviewVote()));
    }

    public View getEditReviewsDialogLayout(){
        LinearLayout layout = new LinearLayout(context);

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(voteEditText);
        layout.addView(bodyEditText);

        return layout;
    }
}
