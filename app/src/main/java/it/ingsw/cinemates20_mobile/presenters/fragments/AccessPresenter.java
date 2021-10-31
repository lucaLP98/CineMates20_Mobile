package it.ingsw.cinemates20_mobile.presenters.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.AccessFragment;
import it.ingsw.cinemates20_mobile.views.fragments.LoginFragment;
import it.ingsw.cinemates20_mobile.views.fragments.SingUpFragment;

public class AccessPresenter {
    private final AccessFragment accessFragment;
    private final FragmentManager fragmentManager;

    public AccessPresenter(AccessFragment accessFragment){
        this.accessFragment = accessFragment;
        fragmentManager = accessFragment.getParentFragmentManager();
    }

    public void pressLoginButton(){
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new LoginFragment()).commit();
    }

    public void pressSingUpButton(){
        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new SingUpFragment()).commit();
    }
}
