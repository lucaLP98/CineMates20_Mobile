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
import it.ingsw.cinemates20_mobile.presenters.EditPasswordPresenter;

public class EditPasswordFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_edit_password, container, false);

        EditPasswordPresenter editPasswordPresenter = new EditPasswordPresenter(this, inflate);

        Toolbar toolbar = inflate.findViewById(R.id.toolbar_edit_password_fragment);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.edit_password_label);
        setHasOptionsMenu(true);

        inflate.findViewById(R.id.cancelEditPasswordButton).setOnClickListener( v -> editPasswordPresenter.pressCancellButton());
        inflate.findViewById(R.id.saveChangePasswordButton).setOnClickListener( v -> editPasswordPresenter.pressSaveChangesPassword());

        return inflate;
    }
}