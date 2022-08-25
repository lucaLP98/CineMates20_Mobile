package it.ingsw.cinemates20_mobile.presenters;

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
    private final SingUpPasswordFragment fragment;
    private final String name;
    private final String surname;
    private final String eMail;
    private final String nickname;
    private final CognitoUserAttributes userAttributes;

    public SingUpPasswordPresenter(SingUpPasswordFragment fragment, String name, String surname, String nickname, String eMail){
        super(fragment);

        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.eMail = eMail;
        this.fragment = fragment;

        fragment.getBackSingUpPasswordButton().setOnClickListener( v -> pressCancelButton());
        fragment.getConfirmButton().setOnClickListener( v -> pressConfirmButton());

        userAttributes = new CognitoUserAttributes();
    }

    private void pressCancelButton(){ getFragmentManager().popBackStack(); }

    private void pressConfirmButton(){
        //required fields check
        if(isEmptyEditText(fragment.getNewPasswordEditText()) ||isEmptyEditText(fragment.getRepeatPasswordEditText())){
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        //matching password check
        if(!matchingPassword(fragment.getNewPasswordEditText().getText().toString(), fragment.getRepeatPasswordEditText().getText().toString())){
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
        String psw = String.valueOf(fragment.getNewPasswordEditText().getText());

        userAttributes.addAttribute("given_name", name);
        userAttributes.addAttribute("family_name", surname);
        userAttributes.addAttribute("nickname", nickname);
        userAttributes.addAttribute("email", eMail);

        CognitoSettings congitoSettings = CognitoSettings.getInstance(getContext());

        congitoSettings.getUserPool().signUpInBackground(eMail, psw, userAttributes, null, singUpHandler);
    }

    private final SignUpHandler singUpHandler = new SignUpHandler() {
        @Override
        public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new VerificationCodeSingUpFragment(eMail)).commit();
        }

        @Override
        public void onFailure(@NonNull Exception exception) {
            showErrorMessage(getContext().getResources().getString(R.string.error_singup_label), exception.getLocalizedMessage());
        }
    };
}
