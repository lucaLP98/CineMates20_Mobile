package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.RecoveryPasswordPresenter;

public class RecoveryPasswordFragment extends Fragment {
    private EditText pswEditText;
    private EditText verificationCodeEditText;
    private EditText repeatPswEditText;
    private Button resetPasswordButton;

    private final ForgotPasswordContinuation forgotPasswordContinuation;

    public RecoveryPasswordFragment(ForgotPasswordContinuation continuation){
        super();

        this.forgotPasswordContinuation = continuation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_recovery_password, container, false);

        verificationCodeEditText = inflate.findViewById(R.id.verificationCodeForgotPasswordEditText);
        pswEditText = inflate.findViewById(R.id.newPasswordForgotEditText);
        repeatPswEditText = inflate.findViewById(R.id.repeatNewPasswordForgotEditText);
        resetPasswordButton = inflate.findViewById(R.id.resetPasswordButton);

        new RecoveryPasswordPresenter(this, forgotPasswordContinuation);

        return inflate;
    }

    public EditText getPswEditText() {
        return pswEditText;
    }

    public EditText getVerificationCodeEditText() {
        return verificationCodeEditText;
    }

    public EditText getRepeatPswEditText() {
        return repeatPswEditText;
    }

    public Button getResetPasswordButton() {
        return resetPasswordButton;
    }
}