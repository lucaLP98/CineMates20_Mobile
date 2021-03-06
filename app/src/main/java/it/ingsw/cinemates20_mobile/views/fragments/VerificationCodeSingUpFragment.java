package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.VerificationCodePresenter;

public class VerificationCodeSingUpFragment extends Fragment {
    private final String userEmail;
    private VerificationCodePresenter verificationCodePresenter;

    public VerificationCodeSingUpFragment(String eMail) {
        userEmail = eMail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_verification_code_sing_up, container, false);

        verificationCodePresenter = new VerificationCodePresenter(this, inflate, userEmail);

        inflate.findViewById(R.id.singupVerificationCodeButton).setOnClickListener( v -> verificationCodePresenter.pressConfirmRegistrazion());
        inflate.findViewById(R.id.resendVerificationCodeButton).setOnClickListener( v -> verificationCodePresenter.pressResendVerificationCodeButton());

        return inflate;
    }
}