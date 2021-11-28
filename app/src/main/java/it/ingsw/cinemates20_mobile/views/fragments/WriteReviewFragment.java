package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.Movie;
import it.ingsw.cinemates20_mobile.presenters.WriteReviewPresenter;

public class WriteReviewFragment extends Fragment {
    private Movie movie;

    private WriteReviewPresenter writeReviewPresenter;

    public WriteReviewFragment(Movie movie){
        this.movie = movie;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_write_review, container, false);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_write_review_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(movie.getTitle());

        ImageView poster = inflate.findViewById(R.id.moviePosterWriteReviewImageView);
        setImageViewPoster(poster);

        writeReviewPresenter = new WriteReviewPresenter(this, inflate, movie);
        inflate.findViewById(R.id.cancellPublishReviewButton).setOnClickListener( v -> writeReviewPresenter.pressCancellButton() );
        inflate.findViewById(R.id.publishReviewButton).setOnClickListener( v -> writeReviewPresenter.pressPublishReviewButton() );

        return inflate;
    }

    private void setImageViewPoster(ImageView poster){
        Glide
                .with(getContext())
                .load(movie.getPosterUri())
                .centerCrop()
                .into(poster);
    }
}