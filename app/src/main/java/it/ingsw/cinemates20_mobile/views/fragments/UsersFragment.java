package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.UserPresenter;

public class UsersFragment extends Fragment {
    public static final String usersFragmentLabel = "users_fragment";

    private UserPresenter userPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_users, container, false);

        userPresenter = new UserPresenter(this, inflate);
        inflate.findViewById(R.id.search_users_button).setOnClickListener( v -> userPresenter.pressSerachUsersButton() );

        return inflate;
    }
}