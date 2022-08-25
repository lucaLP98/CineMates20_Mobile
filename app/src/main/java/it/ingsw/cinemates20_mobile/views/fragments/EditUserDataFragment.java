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
import it.ingsw.cinemates20_mobile.presenters.EditUserDataPresenter;

public class EditUserDataFragment extends Fragment {
    private EditText editNameEditText;
    private EditText editSrunameEditText;
    private EditText editNicknameEditText;
    private EditText editBioEditText;
    private Button cancellEditutton;
    private Button editPasswordButton;
    private Button saveChangesUserDataButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_edit_user_data, container, false);

        editNameEditText = inflate.findViewById(R.id.editNameEditText);
        editSrunameEditText = inflate.findViewById(R.id.editSurnameEditText);
        editNicknameEditText = inflate.findViewById(R.id.editNicknameEditText);
        editBioEditText = inflate.findViewById(R.id.editBioEditText);
        cancellEditutton = inflate.findViewById(R.id.cancellEditutton);
        editPasswordButton = inflate.findViewById(R.id.editPasswordButton);
        saveChangesUserDataButton = inflate.findViewById(R.id.saveChangesUserDataButton);

        EditUserDataPresenter editUserDataPresenter = new EditUserDataPresenter(this);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_edit_userdata_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.edit_data_label);
        setHasOptionsMenu(true);

        return inflate;
    }

    public EditText getEditNameEditText() {
        return editNameEditText;
    }

    public EditText getEditSrunameEditText() {
        return editSrunameEditText;
    }

    public EditText getEditNicknameEditText() {
        return editNicknameEditText;
    }

    public EditText getEditBioEditText() {
        return editBioEditText;
    }

    public Button getCancellEditutton() {
        return cancellEditutton;
    }

    public Button getEditPasswordButton() {
        return editPasswordButton;
    }

    public Button getSaveChangesUserDataButton() {
        return saveChangesUserDataButton;
    }

    public void setEditNameEditText(String editName){
        editNameEditText.setText(editName);
    }

    public void setEditSurnameameEditText(String editSurname){
        editSrunameEditText.setText(editSurname);
    }

    public void setEditNicknameEditText(String editNickname){
        editNicknameEditText.setText(editNickname);
    }

    public void setEditBioEditText(String editBio){
        editBioEditText.setText(editBio);
    }
}