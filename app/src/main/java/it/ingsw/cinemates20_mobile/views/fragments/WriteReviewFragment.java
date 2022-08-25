package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.presenters.WriteReviewPresenter;

public class WriteReviewFragment extends Fragment {
    private final Movie movie;

    private EditText voteEditText;
    private EditText reviewEditText;
    private Button cancellPublishReviewButton;
    private Button publishReviewButton;
    private ImageView poster;

    public WriteReviewFragment(@NonNull Movie movie){
        this.movie = movie;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_write_review, container, false);

        voteEditText = inflate.findViewById(R.id.reviewVoteEditTextNumber);
        reviewEditText = inflate.findViewById(R.id.reviewTextEditTextTextMultiLine);
        cancellPublishReviewButton = inflate.findViewById(R.id.cancellPublishReviewButton);
        publishReviewButton = inflate.findViewById(R.id.publishReviewButton);
        poster = inflate.findViewById(R.id.moviePosterWriteReviewImageView);

        new WriteReviewPresenter(this, movie);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_write_review_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(movie.getTitle());

        return inflate;
    }

    public EditText getVoteEditText() {
        return voteEditText;
    }

    public EditText getReviewEditText() {
        return reviewEditText;
    }

    public Button getCancellPublishReviewButton() {
        return cancellPublishReviewButton;
    }

    public Button getPublishReviewButton() {
        return publishReviewButton;
    }

    public ImageView getPoster() {
        return poster;
    }
}