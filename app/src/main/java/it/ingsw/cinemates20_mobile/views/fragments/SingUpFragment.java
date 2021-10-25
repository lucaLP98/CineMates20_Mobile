package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.presenters.fragments.SingUpPresenter;

public class SingUpFragment extends Fragment {

    private SingUpPresenter singUpPresenter;
    private View inflate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        singUpPresenter = new SingUpPresenter(this);

        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_sing_up, container, false);

        inflate.findViewById(R.id.cancelSingUpButton).setOnClickListener( v -> singUpPresenter.pressCancelButton());
        inflate.findViewById(R.id.singupButton).setOnClickListener( v -> singUpPresenter.pressSingUpButton());

        return inflate;
    }

    public View getInflate(){
        return inflate;
    }
}