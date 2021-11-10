package it.ingsw.cinemates20_mobile.presenters;

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
import it.ingsw.cinemates20_mobile.views.fragments.SingUpPasswordFragment;
import it.ingsw.cinemates20_mobile.views.fragments.VerificationCodeSingUpFragment;

public class SingUpPasswordPresenter extends FragmentPresenter{
    private final EditText newPasswordEditText;
    private final EditText repeatPasswordEditText;

    private final String name;
    private final String surname;
    private final String eMail;
    private final String nickname;

    private final CognitoUserAttributes userAttributes;

    public SingUpPasswordPresenter(SingUpPasswordFragment fragment, View inflate, String name, String surname, String nickname, String eMail) {
        super(fragment);

        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.eMail = eMail;

        newPasswordEditText = inflate.findViewById(R.id.newPasswordEditText);
        repeatPasswordEditText = inflate.findViewById(R.id.repeatNewPasswordEditText);

        userAttributes = new CognitoUserAttributes();
    }

    public void pressCancelButton(){ getFragmentManager().popBackStack(); }

    public void pressConfirmButton(){
        //required fields check
        if(isEmptyEditText(newPasswordEditText) ||isEmptyEditText(repeatPasswordEditText)){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        //matching password check
        if(!matchingPassword(newPasswordEditText.getText().toString(), repeatPasswordEditText.getText().toString())){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_not_matched_password));
            return;
        }

        new AlertDialog.Builder(getContext())
                .setTitle(R.string.privacy_policy_label)
                .setMessage(R.string.privacy_policy)
                .setPositiveButton(R.string.accept, (dialog, which) -> singUpUser())
                .setNegativeButton(R.string.refuse, (dialog, which) -> {})
                .show();
    }

    private boolean matchingPassword(@NonNull String psw1, String psw2){
        return psw1.equals(psw2);
    }

    private void singUpUser(){
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
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new VerificationCodeSingUpFragment(eMail)).commit();
        }

        @Override
        public void onFailure(Exception exception) {
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), exception.getLocalizedMessage());
        }
    };
}
