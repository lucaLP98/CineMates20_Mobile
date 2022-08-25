package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.LoginPresenter;

public class LoginFragment extends Fragment {
    private EditText emailEditText;
    private EditText pswEditText;
    private Button cancelLoginButton;
    private Button loginButton;
    private Button forgotPasswordButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);

        emailEditText = inflate.findViewById(R.id.eMailEditText);
        pswEditText = inflate.findViewById(R.id.passwordEditText);
        cancelLoginButton = inflate.findViewById(R.id.cancelLoginButton);
        loginButton = inflate.findViewById(R.id.loginButton);
        forgotPasswordButton = inflate.findViewById(R.id.forgotPasswordTextView);

        LoginPresenter loginPresenter = new LoginPresenter(this);

        return inflate;
    }

    public EditText getEmailEditText() {
        return emailEditText;
    }

    public EditText getPswEditText() {
        return pswEditText;
    }

    public Button getCancelLoginButton() {
        return cancelLoginButton;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getForgotPasswordButton() {
        return forgotPasswordButton;
    }
}