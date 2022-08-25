package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.UserReviewsPresenter;

public class UserReviewsFragment extends Fragment {
    private RecyclerView reviewsRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public RecyclerView getReviewsRecyclerView() {
        return reviewsRecyclerView;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_user_reviews, container, false);

        reviewsRecyclerView = inflate.findViewById(R.id.userReviewsRecyclerView);

        UserReviewsPresenter userReviewsPresenter = new UserReviewsPresenter(this);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_user_reviews_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.reviews_made);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener( v -> userReviewsPresenter.pressBackButton() );

        return inflate;
    }
}