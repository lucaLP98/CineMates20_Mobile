package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

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
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;

public class ProfileFragment extends Fragment {
    private ProfilePresenter profilePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);

        Toolbar toolbar = (Toolbar) inflate.findViewById(R.id.toolbar_profile_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        profilePresenter = new ProfilePresenter(this, inflate);

        return inflate;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        //super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Boolean ret;

        switch (item.getItemId()) {

            case R.id.logout_toolbar_menu:
                profilePresenter.pressLogoutButton();
                ret = true;
            default:
                ret = false;
                break;
        }

        return ret;
    }
}