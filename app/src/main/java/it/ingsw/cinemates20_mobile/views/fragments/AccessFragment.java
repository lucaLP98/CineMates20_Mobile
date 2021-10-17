package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.fragments.AccessPresenter;

public class AccessFragment extends Fragment {

    private AccessPresenter accessPresenter;

    public AccessFragment() { }

    public static AccessFragment newInstance(String param1, String param2) {
        return new AccessFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        accessPresenter = new AccessPresenter(this);

        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_access, container, false);

        inflate.findViewById(R.id.goToLoginButton).setOnClickListener( v -> accessPresenter.pressLoginButton());

        return inflate;
    }


}