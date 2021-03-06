package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.SingUpPresenter;

public class SingUpFragment extends Fragment {
    private SingUpPresenter singUpPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_sing_up, container, false);

        singUpPresenter = new SingUpPresenter(this, inflate);

        inflate.findViewById(R.id.cancelSingUpButton).setOnClickListener( v -> singUpPresenter.pressCancelButton());
        inflate.findViewById(R.id.nextButton).setOnClickListener( v -> singUpPresenter.pressNextButton());
        inflate.findViewById(R.id.alreadyCodeButton).setOnClickListener( v -> singUpPresenter.pressAlreadyCodeButton());

        return inflate;
    }
}