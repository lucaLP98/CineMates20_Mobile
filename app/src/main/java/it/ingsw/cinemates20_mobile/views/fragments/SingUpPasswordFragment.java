package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.SingUpPasswordPresenter;

public class SingUpPasswordFragment extends Fragment {
    private final String name;
    private final String surname;
    private final String eMail;
    private final String username;

    private SingUpPasswordPresenter singUpPasswordPresenter;

    public SingUpPasswordFragment(String name, String surname, String eMail, String username){
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.eMail = eMail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_sing_up_password, container, false);

        singUpPasswordPresenter = new SingUpPasswordPresenter(this, inflate, name, surname, username, eMail);

        inflate.findViewById(R.id.backSingUpPasswordButton).setOnClickListener( v -> singUpPasswordPresenter.pressCancelButton());
        inflate.findViewById(R.id.confirmButton).setOnClickListener( v -> singUpPasswordPresenter.pressConfirmButton());

        return inflate;
    }
}