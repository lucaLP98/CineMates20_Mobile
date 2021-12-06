package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.ProfilePresenter;

public class ProfileFragment extends Fragment {
    public static final String profileFragmentLabel = "profile_fragment";

    private ProfilePresenter profilePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePresenter = new ProfilePresenter(this, inflate);
        profilePresenter.setCustomizedTextView();
        profilePresenter.setProfileImageView();

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_profile_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.profile_label);
        setHasOptionsMenu(true);

        inflate.findViewById(R.id.addPhotoButton).setOnClickListener( v -> profilePresenter.pressAddPhotoButton());
        inflate.findViewById((R.id.reviewsMadeButton)).setOnClickListener( v->profilePresenter.pressViewReviewsListButton() );
        inflate.findViewById((R.id.friendsListButton)).setOnClickListener( v->profilePresenter.pressViewFriendsListButton() );

        return inflate;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean ret;

        switch (item.getItemId()) {
            case R.id.logout_toolbar_menu:
                profilePresenter.pressLogoutButton();
                ret = true;
                break;

            case R.id.edit_data_toolbar_menu:
                profilePresenter.pressEdituserData();
                ret = true;
                break;

            default:
                ret = false;
                break;
        }

        return ret;
    }
}