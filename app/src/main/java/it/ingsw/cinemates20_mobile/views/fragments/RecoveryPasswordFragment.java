package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.RecoveryPasswordPresenter;

public class RecoveryPasswordFragment extends Fragment {
    private RecoveryPasswordPresenter recoveryPasswordPresenter;

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
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_recovery_password, container, false);

        recoveryPasswordPresenter = new RecoveryPasswordPresenter(this, inflate, forgotPasswordContinuation);

        inflate.findViewById(R.id.resetPasswordButton).setOnClickListener(v -> recoveryPasswordPresenter.pressResetPasswordButton());

        return inflate;
    }
}