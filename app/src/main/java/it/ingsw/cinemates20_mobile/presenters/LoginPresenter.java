package it.ingsw.cinemates20_mobile.presenters;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.FragmentManager;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.DAO.interfaces.UserDAO;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.activities.HomeActivity;
import it.ingsw.cinemates20_mobile.views.fragments.LoginFragment;
import it.ingsw.cinemates20_mobile.views.fragments.RecoveryPasswordFragment;

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
        CognitoSettings cognitoSettings = CognitoSettings.getInstance(getContext());
        CognitoUser user = cognitoSettings.getUserPool().getUser(eMail);
        user.signOut();
        user.getSessionInBackground(authenticationHandler);
    }

    private final AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            User.setUserAuthenticated(true);

            DAOFactory.getUserDao().getUserdata(userSession, getContext());

            Intent loginIntent = new Intent(getContext(), HomeActivity.class);
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
            showErrorMessage(getContext().getResources().getString(R.string.error_singin_label), exception.getLocalizedMessage());
        }
    };

    public void pressForgottenPassword(){
        if(isEmptyEditText(emailEditText)){
            showErrorMessage(getContext().getResources().getString(R.string.recovery_password_error_label), getContext().getResources().getString(R.string.email_already_code));
            return;
        }

        CognitoSettings cognito = CognitoSettings.getInstance(getContext());
        CognitoUser user = cognito.getUserPool().getUser(String.valueOf(emailEditText.getText()));

        user.forgotPasswordInBackground(forgotPasswordCallback);
    }

    private final ForgotPasswordHandler forgotPasswordCallback = new ForgotPasswordHandler() {
        @Override
        public void onSuccess() {
            showSuccessMessage(getContext().getResources().getString(R.string.recovery_password_success_label), getContext().getResources().getString(R.string.recovery_password_success_msg));
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        @Override
        public void getResetCode(ForgotPasswordContinuation continuation) {
            showSuccessMessage(getContext().getResources().getString(R.string.resend_verification_code_success_label), getContext().getResources().getString(R.string.send_verification_code_forgot_password_success_msg));
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new RecoveryPasswordFragment(continuation)).commit();
        }

        @Override
        public void onFailure(Exception exception) {
            showErrorMessage(getContext().getResources().getString(R.string.recovery_password_error_label), exception.getLocalizedMessage());
        }
    };
}
