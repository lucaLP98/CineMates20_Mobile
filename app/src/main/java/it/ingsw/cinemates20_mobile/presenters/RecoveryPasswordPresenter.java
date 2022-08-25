package it.ingsw.cinemates20_mobile.presenters;

import androidx.annotation.NonNull;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.RecoveryPasswordFragment;

public class RecoveryPasswordPresenter extends FragmentPresenter{
    private final ForgotPasswordContinuation forgotPasswordContinuation;
    private final RecoveryPasswordFragment recoveryPasswordFragment;

    public RecoveryPasswordPresenter(RecoveryPasswordFragment recoveryPasswordFragment, ForgotPasswordContinuation continuation){
        super(recoveryPasswordFragment);

        this.recoveryPasswordFragment = recoveryPasswordFragment;
        this.forgotPasswordContinuation = continuation;

        recoveryPasswordFragment.getResetPasswordButton().setOnClickListener(v -> pressResetPasswordButton());
    }

    private void pressResetPasswordButton(){
        if(isEmptyEditText(recoveryPasswordFragment.getPswEditText()) || isEmptyEditText(recoveryPasswordFragment.getRepeatPswEditText()) || isEmptyEditText(recoveryPasswordFragment.getVerificationCodeEditText())){
            showErrorMessage(getContext().getResources().getString(R.string.recovery_password_error_label), getContext().getResources().getString(R.string.error_empty_field));
            return;
        }

        String psw = String.valueOf(recoveryPasswordFragment.getPswEditText().getText());
        String code = String.valueOf(recoveryPasswordFragment.getVerificationCodeEditText().getText());
        String repeatPsw = String.valueOf(recoveryPasswordFragment.getRepeatPswEditText().getText());

        if(!matchingPassword(psw, repeatPsw)){
            showErrorMessage(getContext().getResources().getString(R.string.recovery_password_error_label), getContext().getResources().getString(R.string.error_not_matched_password));
            return;
        }

        forgotPasswordContinuation.setPassword(psw);
        forgotPasswordContinuation.setVerificationCode(code);
        forgotPasswordContinuation.continueTask();
    }

    private boolean matchingPassword(@NonNull String psw1, @NonNull String psw2){
        return psw1.equals(psw2);
    }
}
