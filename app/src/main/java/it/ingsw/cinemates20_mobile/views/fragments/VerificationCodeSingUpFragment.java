package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.VerificationCodePresenter;

public class VerificationCodeSingUpFragment extends Fragment {
    private final String userEmail;

    private EditText codeEditText;
    private Button singupVerificationCodeButton;
    private Button resendVerificationCodeButton ;

    public VerificationCodeSingUpFragment(String eMail) {
        userEmail = eMail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public EditText getCodeEditText() {
        return codeEditText;
    }

    public Button getSingupVerificationCodeButton() {
        return singupVerificationCodeButton;
    }

    public Button getResendVerificationCodeButton() {
        return resendVerificationCodeButton;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_verification_code_sing_up, container, false);

        codeEditText = inflate.findViewById(R.id.verificationCodeEditText);
        singupVerificationCodeButton = inflate.findViewById(R.id.singupVerificationCodeButton);
        resendVerificationCodeButton = inflate.findViewById(R.id.resendVerificationCodeButton);

        new VerificationCodePresenter(this, userEmail);

        return inflate;
    }
}