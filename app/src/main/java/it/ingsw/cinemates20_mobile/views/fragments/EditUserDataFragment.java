package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.EditUserDataPresenter;

public class EditUserDataFragment extends Fragment {
    private EditUserDataPresenter editUserDataPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_edit_user_data, container, false);

        editUserDataPresenter = new EditUserDataPresenter(this, inflate);
        editUserDataPresenter.setEditTextValue();

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_edit_userdata_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.edit_data_label);
        setHasOptionsMenu(true);

        inflate.findViewById(R.id.cancellEditutton).setOnClickListener( v -> editUserDataPresenter.pressCancellButton());
        inflate.findViewById(R.id.editPasswordButton).setOnClickListener( v -> editUserDataPresenter.pressEditPassword());
        inflate.findViewById(R.id.saveChangesUserDataButton).setOnClickListener( v -> editUserDataPresenter.pressSaveChanges());

        return inflate;
    }
}