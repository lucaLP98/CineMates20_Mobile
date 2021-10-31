package it.ingsw.cinemates20_mobile.presenters.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.activities.HomeActivity;
import it.ingsw.cinemates20_mobile.views.fragments.LoginFragment;

public class LoginPresenter extends FragmentPresenter{
    private final EditText emailEditText;
    private final EditText pswEditText;

    public LoginPresenter(LoginFragment loginFragment, View inflate){
        super(loginFragment);

        emailEditText = inflate.findViewById(R.id.eMailEditText);
        pswEditText = inflate.findViewById(R.id.passwordEditText);
    }

    public void pressCancelButton(){
        getFragmentManager().popBackStack();
    }

    public void pressLoginButton(){
        //required fields check
        if(isEmptyEditText(emailEditText) || isEmptyEditText(pswEditText)){
            showErrorMessage(getContext().getResources().getString(R.string.error_singin_label), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        loginUser(String.valueOf(emailEditText.getText()));
    }

    private void loginUser(String eMail){
        CognitoSettings cognitoSettings = new CognitoSettings(getContext());
        CognitoUser thisUser = cognitoSettings.getUserPool().getUser(eMail);

        thisUser.getSessionInBackground(authenticationHandler);
    }

    private boolean isEmptyEditText(@NonNull EditText editText){
        return String.valueOf(editText.getText()).equals("");
    }

    private final AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            Intent loginIntent = new Intent(getContext(), HomeActivity.class);
            loginIntent.putExtra("loginToken", userSession.getIdToken().toString());
            getContext().startActivity(loginIntent);
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, String.valueOf(pswEditText.getText()), null);
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);
            authenticationContinuation.continueTask();
        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {

        }

        @Override
        public void onFailure(Exception exception) {
            showErrorMessage(getContext().getResources().getString(R.string.error_singin_label), exception.getMessage());
        }
    };
}
