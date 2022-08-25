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
import it.ingsw.cinemates20_mobile.presenters.SingUpPresenter;

public class SingUpFragment extends Fragment {
    private EditText newNameEditText;
    private EditText newSurnameEditText;
    private EditText newUsernameEditText;
    private EditText newEmailEditText;
    private Button cancelSingUpButton;
    private Button nextButton;
    private Button alreadyCodeButton;

    public EditText getNewNameEditText() {
        return newNameEditText;
    }

    public EditText getNewSurnameEditText() {
        return newSurnameEditText;
    }

    public EditText getNewUsernameEditText() {
        return newUsernameEditText;
    }

    public EditText getNewEmailEditText() {
        return newEmailEditText;
    }

    public Button getCancelSingUpButton() {
        return cancelSingUpButton;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public Button getAlreadyCodeButton() {
        return alreadyCodeButton;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_sing_up, container, false);

        newNameEditText = inflate.findViewById(R.id.newNameEditText);
        newSurnameEditText = inflate.findViewById(R.id.newSurnameEditText);
        newUsernameEditText = inflate.findViewById(R.id.newUsernameEditText);
        newEmailEditText = inflate.findViewById(R.id.newEmailEditText);
        cancelSingUpButton = inflate.findViewById(R.id.cancelSingUpButton);
        nextButton = inflate.findViewById(R.id.nextButton);
        alreadyCodeButton = inflate.findViewById(R.id.alreadyCodeButton);

        new SingUpPresenter(this);

        return inflate;
    }
}