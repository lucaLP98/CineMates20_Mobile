package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.UserReviewsPresenter;

public class UserReviewsFragment extends Fragment {
    private UserReviewsPresenter userReviewsPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_user_reviews, container, false);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_user_reviews_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.reviews_made);

        userReviewsPresenter = new UserReviewsPresenter(this, inflate);
        inflate.findViewById(R.id.backUserReviewsFragmentButton).setOnClickListener( v -> userReviewsPresenter.pressBackButton() );
        userReviewsPresenter.setReviewsRecyclerView();

        return inflate;
    }
}