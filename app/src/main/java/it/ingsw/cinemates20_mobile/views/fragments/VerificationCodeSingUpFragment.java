package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.fragments.VerificationCodePresenter;

public class VerificationCodeSingUpFragment extends Fragment {
    private View inflate;
    private String userEmail;
    private VerificationCodePresenter verificationCodePresenter;

    public VerificationCodeSingUpFragment(String eMail) {
        userEmail = eMail;
    }

    public View getInflate(){ return inflate; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        verificationCodePresenter = new VerificationCodePresenter(this, userEmail);

        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_verification_code_sing_up, container, false);

        inflate.findViewById(R.id.singupVerificationCodeButton).setOnClickListener( v -> verificationCodePresenter.clickConfirmRegistrazion());

        return inflate;
    }
}