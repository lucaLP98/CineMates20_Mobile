package it.ingsw.cinemates20_mobile.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.ingsw.cinemates20_mobile.R;

public class AccessFragment extends Fragment {

    public AccessFragment() {
        // Required empty public constructor
    }

    public static AccessFragment newInstance(String param1, String param2) {
        return new AccessFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_access, container, false);

        inflate.findViewById(R.id.goToLoginButton).setOnClickListener( v -> pressLoginButton() );

        return inflate;
    }

    private void pressLoginButton(){
        getParentFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new LoginFragment()).commit();
    }
}