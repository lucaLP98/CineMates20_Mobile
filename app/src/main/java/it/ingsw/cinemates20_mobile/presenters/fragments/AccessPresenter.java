package it.ingsw.cinemates20_mobile.presenters.fragments;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.views.fragments.AccessFragment;
import it.ingsw.cinemates20_mobile.views.fragments.LoginFragment;
import it.ingsw.cinemates20_mobile.views.fragments.SingUpFragment;

public class AccessPresenter {

    private final AccessFragment accessFragment;

    public AccessPresenter(AccessFragment accessFragment){
        this.accessFragment = accessFragment;
    }

    public void pressLoginButton(){
        accessFragment.getParentFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new LoginFragment()).commit();
    }

    public void pressSingUpButton(){
        accessFragment.getParentFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new SingUpFragment()).commit();
    }
}
