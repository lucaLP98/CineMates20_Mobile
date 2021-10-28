package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.fragments.LoginPresenter;

public class LoginFragment extends Fragment {

    private LoginPresenter loginPresenter;
    private View inflate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loginPresenter = new LoginPresenter(this);

        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_login, container, false);

        inflate.findViewById(R.id.cancelLoginButton).setOnClickListener( v -> loginPresenter.pressCancelButton());
        inflate.findViewById(R.id.loginButton).setOnClickListener( v -> loginPresenter.pressLoginButton());

        return inflate;
    }

    public View getInflate(){
        return inflate;
    }
}