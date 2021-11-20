package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.AccessPresenter;

public class AccessFragment extends Fragment {
    private AccessPresenter accessPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_access, container, false);

        accessPresenter = new AccessPresenter(this);

        inflate.findViewById(R.id.goToLoginButton).setOnClickListener( v -> accessPresenter.pressLoginButton());
        inflate.findViewById(R.id.goToSingUpButton).setOnClickListener( v -> accessPresenter.pressSingUpButton());
        inflate.findViewById(R.id.loginLaterTextView).setOnClickListener( v -> accessPresenter.pressSingInLater());

        return inflate;
    }
}