package it.ingsw.cinemates20_mobile.presenters.fragments;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.fragments.SingUpFragment;
import it.ingsw.cinemates20_mobile.views.fragments.VerificationCodeSingUpFragment;

public class SingUpPresenter extends FragmentPresenter{
    private final EditText newNameEditText;
    private final EditText newSurnameEditText;
    private final EditText newUsernameEditText;
    private final EditText newEmailEditText;
    private final EditText newPasswordEditText;
    private final EditText repeatPasswordEditText;

    private final CognitoUserAttributes userAttributes;

    public SingUpPresenter(SingUpFragment singUpFragment, View inflate){
        super(singUpFragment);

        newNameEditText = inflate.findViewById(R.id.newNameEditText);
        newSurnameEditText = inflate.findViewById(R.id.newSurnameEditText);
        newUsernameEditText = inflate.findViewById(R.id.newUsernameEditText);
        newEmailEditText = inflate.findViewById(R.id.newEmailEditText);
        newPasswordEditText = inflate.findViewById(R.id.newPasswordEditText);
        repeatPasswordEditText = inflate.findViewById(R.id.repeatNewPasswordEditText);

        userAttributes = new CognitoUserAttributes();
    }

    public void pressCancelButton(){ getFragmentManager().popBackStack(); }

    public void pressSingUpButton(){
        //required fields check
        if( isEmptyEditText(newNameEditText) || isEmptyEditText(newSurnameEditText) ||
            isEmptyEditText(newEmailEditText) || isEmptyEditText(newPasswordEditText) ||
            isEmptyEditText(newUsernameEditText) || isEmptyEditText(repeatPasswordEditText)
        ){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        //matching password check
        if(!matchingPassword(newPasswordEditText.getText().toString(), repeatPasswordEditText.getText() .toString())){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_not_matched_password));
            return;
        }

        new AlertDialog.Builder(getContext())
                .setTitle(R.string.privacy_policy_label)
                .setMessage(R.string.privacy_policy)
                .setPositiveButton(R.string.accept, (dialog, which) -> {singUpUser();})
                .setNegativeButton(R.string.refuse, (dialog, which) -> {})
                .show();
    }

    private void singUpUser(){
        String name = String.valueOf(newNameEditText.getText());
        String surname = String.valueOf(newSurnameEditText.getText());
        String nickname = String.valueOf(newUsernameEditText.getText());
        String eMail = String.valueOf(newEmailEditText.getText());
        String psw = String.valueOf(newPasswordEditText.getText());

        userAttributes.addAttribute("given_name", name);
        userAttributes.addAttribute("family_name", surname);
        userAttributes.addAttribute("nickname", nickname);
        userAttributes.addAttribute("email", eMail);
        userAttributes.addAttribute("preferred_username", nickname);

        CognitoSettings congitoSettings = new CognitoSettings(getContext());

        congitoSettings.getUserPool().signUpInBackground(eMail, psw, userAttributes, null, singUpHandler);
    }

    private final SignUpHandler singUpHandler = new SignUpHandler() {
        @Override
        public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new VerificationCodeSingUpFragment(String.valueOf(newEmailEditText.getText()))).commit();
        }

        @Override
        public void onFailure(Exception exception) {
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), exception.getLocalizedMessage());
        }
    };

    public void pressAlreadyCodeButton(){
        if(isEmptyEditText(newEmailEditText)){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.email_already_code));
            return;
        }

        String eMail = String.valueOf(newEmailEditText.getText());
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new VerificationCodeSingUpFragment(eMail)).commit();
    }

    private boolean isEmptyEditText(@NonNull EditText editText){
        return editText.getText().toString().equals("");
    }

    private boolean matchingPassword(@NonNull String psw1, String psw2){
        return psw1.equals(psw2);
    }
}
