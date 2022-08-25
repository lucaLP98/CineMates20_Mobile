package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.EditPasswordPresenter;

public class EditPasswordFragment extends Fragment {
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText repeatNewPasswordEditText;

    private Button cancelEditPasswordButton;
    private Button saveChangePasswordButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_edit_password, container, false);

        oldPasswordEditText = inflate.findViewById(R.id.oldPasswordChangePasswordEditText);
        newPasswordEditText = inflate.findViewById(R.id.newPasswordChangePasswordEditText);
        repeatNewPasswordEditText = inflate.findViewById(R.id.repeatNewPasswordChangePasswordEditText2);
        cancelEditPasswordButton = inflate.findViewById(R.id.cancelEditPasswordButton);
        saveChangePasswordButton = inflate.findViewById(R.id.saveChangePasswordButton);

        EditPasswordPresenter editPasswordPresenter = new EditPasswordPresenter(this);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_edit_password_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.edit_password_label);
        setHasOptionsMenu(true);

        return inflate;
    }

    public EditText getOldPasswordEditText() {
        return oldPasswordEditText;
    }

    public EditText getNewPasswordEditText() {
        return newPasswordEditText;
    }

    public EditText getRepeatNewPasswordEditText() {
        return repeatNewPasswordEditText;
    }

    public Button getCancelEditPasswordButton() {
        return cancelEditPasswordButton;
    }

    public Button getSaveChangePasswordButton() {
        return saveChangePasswordButton;
    }
}