package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.LoginPresenter;

public class LoginFragment extends Fragment {
    private LoginPresenter loginPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);

        loginPresenter = new LoginPresenter(this, inflate);

        inflate.findViewById(R.id.cancelLoginButton).setOnClickListener( v -> loginPresenter.pressCancelButton());
        inflate.findViewById(R.id.loginButton).setOnClickListener( v -> loginPresenter.pressLoginButton());
        inflate.findViewById(R.id.forgotPasswordTextView).setOnClickListener( v -> loginPresenter.pressForgottenPassword());

        return inflate;
    }
}