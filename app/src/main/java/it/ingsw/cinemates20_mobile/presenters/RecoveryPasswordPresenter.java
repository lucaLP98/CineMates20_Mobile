package it.ingsw.cinemates20_mobile.presenters;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.RecoveryPasswordFragment;

public class RecoveryPasswordPresenter extends FragmentPresenter{
    private final EditText pswEditText;
    private final EditText verificationCodeEditText;
    private final EditText repeatPswEditText;

    private final ForgotPasswordContinuation forgotPasswordContinuation;

    public RecoveryPasswordPresenter(RecoveryPasswordFragment recoveryPasswordFragment, @NonNull View inflate, ForgotPasswordContinuation continuation){
        super(recoveryPasswordFragment);

        verificationCodeEditText = inflate.findViewById(R.id.verificationCodeForgotPasswordEditText);
        pswEditText = inflate.findViewById(R.id.newPasswordForgotEditText);
        repeatPswEditText = inflate.findViewById(R.id.repeatNewPasswordForgotEditText);

        this.forgotPasswordContinuation = continuation;
    }

    public void pressResetPasswordButton(){
        if(isEmptyEditText(pswEditText) || isEmptyEditText(repeatPswEditText) || isEmptyEditText(verificationCodeEditText)){
            showErrorMessage(getContext().getResources().getString(R.string.recovery_password_error_label), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        String psw = String.valueOf(pswEditText.getText());
        String code = String.valueOf(verificationCodeEditText.getText());
        String repeatPsw = String.valueOf(repeatPswEditText.getText());

        if(!matchingPassword(psw, repeatPsw)){
            showErrorMessage(getContext().getResources().getString(R.string.recovery_password_error_label), getContext().getResources().getString(R.string.error_not_matched_password));
            return;
        }

        forgotPasswordContinuation.setPassword(psw);
        forgotPasswordContinuation.setVerificationCode(code);

        forgotPasswordContinuation.continueTask();
    }

    private boolean matchingPassword(@NonNull String psw1, String psw2){
        return psw1.equals(psw2);
    }
}
