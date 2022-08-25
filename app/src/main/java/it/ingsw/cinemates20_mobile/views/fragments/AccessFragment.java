package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.AccessPresenter;

public class AccessFragment extends Fragment {
    private Button goToLoginButton;
    private Button goToSingUpButton;
    private Button loginLaterTextView;

    public Button getGoToLoginButton() {
        return goToLoginButton;
    }

    public Button getGoToSingUpButton() {
        return goToSingUpButton;
    }

    public Button getLoginLaterTextView() {
        return loginLaterTextView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_access, container, false);

        goToLoginButton = inflate.findViewById(R.id.goToLoginButton);
        goToSingUpButton = inflate.findViewById(R.id.goToSingUpButton);
        loginLaterTextView = inflate.findViewById(R.id.loginLaterTextView);

        new AccessPresenter(this);

        return inflate;
    }
}