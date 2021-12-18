package it.ingsw.cinemates20_mobile.presenters;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
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
import com.android.volley.VolleyError;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.ingsw.cinemates20_mobile.DAO.DAOFactory;
import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;
import it.ingsw.cinemates20_mobile.views.activities.HomeActivity;
import it.ingsw.cinemates20_mobile.views.fragments.LoginFragment;
import it.ingsw.cinemates20_mobile.views.fragments.RecoveryPasswordFragment;

public class LoginPresenter extends FragmentPresenter{
    private final EditText emailEditText;
    private final EditText pswEditText;

    public LoginPresenter(LoginFragment loginFragment, @NonNull View inflate){
        super(loginFragment);

        emailEditText = inflate.findViewById(R.id.eMailEditText);
        pswEditText = inflate.findViewById(R.id.passwordEditText);
    }

    public void pressCancelButton(){
        getFragmentManager().popBackStack();
    }

    private boolean checkEmailCorrectFormat(String eMail){
        boolean ret;

        Pattern validEmailAddressRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = validEmailAddressRegex.matcher(eMail);
        ret = matcher.find();

        return ret;
    };

    public void pressLoginButton(){
        if(isEmptyEditText(pswEditText) || isEmptyEditText(emailEditText)){
            showErrorMessage(getContext().getResources().getString(R.string.error_singin_label), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        if(!checkEmailCorrectFormat(String.valueOf(emailEditText.getText()))){
            showErrorMessage(getContext().getResources().getString(R.string.error_singin_label), getContext().getResources().getString(R.string.error_email_format));
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
            ThisUser.setUserAuthenticated(true);

            RequestCallback<Map<String, String>> callback = new RequestCallback<Map<String, String>>() {
                @Override
                public void onSuccess(@NonNull Map<String, String> result) {
                    String name = result.get("name");
                    String surname = result.get("surname");
                    String nickname = result.get("nickname");
                    String email = result.get("email");
                    String uri_image = result.get("uri_image");
                    String biography = result.get("biography");

                    ThisUser user = ThisUser.createInstance(name, surname, nickname, email, userSession);
                    user.setBiography(biography);
                    if (!uri_image.equals("null")) {
                        user.setProfileImage(Uri.parse(uri_image));
                    }
                }

                @Override
                public void onError(@NonNull VolleyError error) {
                    error.printStackTrace();
                }
            };

            DAOFactory.getUserDao().getUserdata(userSession, getContext(), callback);

            Intent loginIntent = new Intent(getContext(), HomeActivity.class);
            getContext().startActivity(loginIntent);
        }

        @Override
        public void getAuthenticationDetails(@NonNull AuthenticationContinuation authenticationContinuation, String userId) {
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
        public void onFailure(@NonNull Exception exception) {
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
        public void onFailure(@NonNull Exception exception) {
            if(exception.getLocalizedMessage().contains("UserLambdaValidationException")){
                onSuccess();
            }else{
                showErrorMessage(getContext().getResources().getString(R.string.recovery_password_error_label), exception.getLocalizedMessage());
            }
        }
    };
}
