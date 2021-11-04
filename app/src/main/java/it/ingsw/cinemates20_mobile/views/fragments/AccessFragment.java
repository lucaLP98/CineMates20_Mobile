package it.ingsw.cinemates20_mobile.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.fragments.AccessPresenter;

public class AccessFragment extends Fragment {
    private final int loginWithFacebook = 1001;
    private final int loginWithGoogle = 1002;
    private final int loginWithCognito = 1003;
    private final int singupWithCognito = 1004;
    private final int noLogin = 1005;

    private AccessPresenter accessPresenter;

    private LoginButton facebookLoginButton;
    private CallbackManager facebookCallbackManager;

    private int loginType;

    public AccessFragment(){
        super();

        facebookCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_access, container, false);

        accessPresenter = new AccessPresenter(this, inflate);

        inflate.findViewById(R.id.goToLoginButton).setOnClickListener( v ->{ loginType = loginWithCognito; accessPresenter.pressLoginButton();});
        inflate.findViewById(R.id.goToSingUpButton).setOnClickListener( v ->{loginType = singupWithCognito; accessPresenter.pressSingUpButton();});
        inflate.findViewById(R.id.loginLaterTextView).setOnClickListener( v ->{loginType = noLogin; accessPresenter.pressSingInLater();});


        facebookLoginButton = inflate.findViewById(R.id.login_fb_button);
        facebookLoginButton.setOnClickListener( v -> pressLoginWithFacebook());

        return inflate;
    }

    private void pressLoginWithFacebook(){
        loginType = loginWithFacebook;

        facebookLoginButton.setPermissions("email");
        facebookLoginButton.setFragment(this);

        facebookLoginButton.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("facebook", "Successo login, token : " + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Log.d("facebook", "cancello login");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("facebook", "Errore login : " + exception.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(loginType == loginWithFacebook)
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        else if(loginType == loginWithGoogle){

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}