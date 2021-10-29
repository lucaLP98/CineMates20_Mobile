package it.ingsw.cinemates20_mobile.presenters.fragments;

import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.fragments.SingUpFragment;
import it.ingsw.cinemates20_mobile.views.fragments.VerificationCodeSingUpFragment;

public class SingUpPresenter {
    private final SingUpFragment singUpFragment;

    private final CognitoUserAttributes userAttributes = new CognitoUserAttributes();

    public SingUpPresenter(SingUpFragment singUpFragment){
        this.singUpFragment = singUpFragment;
    }

    public void pressCancelButton(){
        singUpFragment.getActivity().getSupportFragmentManager().popBackStack();
    }

    public void pressSingUpButton(){
        EditText newNameEditText = singUpFragment.getInflate().findViewById(R.id.newNameEditText);
        EditText newSurnameEditText = singUpFragment.getInflate().findViewById(R.id.newSurnameEditText);
        EditText newUsernameEditText = singUpFragment.getInflate().findViewById(R.id.newUsernameEditText);
        EditText newEmailEditText = singUpFragment.getInflate().findViewById(R.id.newEmailEditText);
        EditText newPasswordEditText = singUpFragment.getInflate().findViewById(R.id.newPasswordEditText);
        EditText repeatPasswordEditText = singUpFragment.getInflate().findViewById(R.id.repeatNewPasswordEditText);

        //required fields check
        if(
                isEmptyEditText(newNameEditText) || isEmptyEditText(newSurnameEditText) ||
                isEmptyEditText(newEmailEditText) || isEmptyEditText(newPasswordEditText) ||
                isEmptyEditText(newUsernameEditText) || isEmptyEditText(repeatPasswordEditText)
        ){
            Toast.makeText(singUpFragment.getActivity(), R.string.error_empty_field, Toast.LENGTH_SHORT).show();
            return;
        }

        //matching password check
        if(!matchingPassword(newPasswordEditText.getText().toString(), repeatPasswordEditText.getText() .toString())){
            Toast.makeText(singUpFragment.getActivity(), R.string.error_not_matched_password, Toast.LENGTH_SHORT).show();
            return;
        }

        String name = String.valueOf(newNameEditText.getText());
        String surname = String.valueOf(newSurnameEditText.getText());
        String nickname = String.valueOf(newUsernameEditText.getText());
        String eMail = String.valueOf(newEmailEditText.getText());
        String psw = String.valueOf(newPasswordEditText.getText());

        userAttributes.addAttribute("given_name", name);
        userAttributes.addAttribute("family_name", surname);
        userAttributes.addAttribute("nickname", nickname);
        userAttributes.addAttribute("email", eMail);

        CognitoSettings congitoSettings = new CognitoSettings(singUpFragment.getActivity());

        congitoSettings.getUserPool().signUpInBackground(eMail, psw, userAttributes, null,
                new SignUpHandler() {
                    @Override
                    public void onSuccess(CognitoUser user, boolean signUpConfirmationState, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                        singUpFragment.getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new VerificationCodeSingUpFragment(eMail)).commit();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        showErrorMessage(exception.getMessage());
                    }
                }
        );
    }

    public void pressAlreadyCodeButton(){
        EditText newEmailEditText = singUpFragment.getInflate().findViewById(R.id.newEmailEditText);

        if(isEmptyEditText(newEmailEditText)){
            Toast.makeText(singUpFragment.getActivity(), R.string.email_already_code, Toast.LENGTH_SHORT).show();
            return;
        }

        String eMail = String.valueOf(newEmailEditText.getText());
        singUpFragment.getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new VerificationCodeSingUpFragment(eMail)).commit();
    }

    private boolean isEmptyEditText(@NonNull EditText editText){
        return editText.getText().toString().equals("");
    }

    private boolean matchingPassword(@NonNull String psw1, String psw2){
        return psw1.equals(psw2);
    }

    private void showErrorMessage(String errorMsg){
        new AlertDialog.Builder(this.singUpFragment.getActivity())
                .setTitle(R.string.error_singup_label)
                .setMessage(errorMsg)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
