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
import it.ingsw.cinemates20_mobile.presenters.SingUpPasswordPresenter;

public class SingUpPasswordFragment extends Fragment {
    private final String name;
    private final String surname;
    private final String eMail;
    private final String username;

    private EditText newPasswordEditText;
    private EditText repeatPasswordEditText;
    private Button backSingUpPasswordButton;
    private Button confirmButton;

    public EditText getNewPasswordEditText() {
        return newPasswordEditText;
    }

    public EditText getRepeatPasswordEditText() {
        return repeatPasswordEditText;
    }

    public Button getBackSingUpPasswordButton() {
        return backSingUpPasswordButton;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_sing_up_password, container, false);

        newPasswordEditText = inflate.findViewById(R.id.newPasswordEditText);
        repeatPasswordEditText = inflate.findViewById(R.id.repeatNewPasswordEditText);
        backSingUpPasswordButton = inflate.findViewById(R.id.backSingUpPasswordButton);
        confirmButton = inflate.findViewById(R.id.confirmButton);

        new SingUpPasswordPresenter(this, name, surname, username, eMail);

        return inflate;
    }
}