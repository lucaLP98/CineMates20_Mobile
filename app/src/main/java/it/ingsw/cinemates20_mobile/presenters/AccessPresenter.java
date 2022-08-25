package it.ingsw.cinemates20_mobile.presenters;

import android.content.Intent;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.views.activities.HomeActivity;
import it.ingsw.cinemates20_mobile.views.fragments.AccessFragment;
import it.ingsw.cinemates20_mobile.views.fragments.LoginFragment;
import it.ingsw.cinemates20_mobile.views.fragments.SingUpFragment;

public class AccessPresenter extends FragmentPresenter{

    public AccessPresenter(AccessFragment accessFragment){
        super(accessFragment);
        accessFragment.getGoToLoginButton().setOnClickListener( v -> pressLoginButton());
        accessFragment.getGoToSingUpButton().setOnClickListener( v -> pressSingUpButton());
        accessFragment.getLoginLaterTextView().setOnClickListener( v -> pressSingInLater());
    }

    private void pressLoginButton(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new LoginFragment()).commit();
    }

    private void pressSingUpButton(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new SingUpFragment()).commit();
    }

    private void pressSingInLater(){
        ThisUser.setUserAuthenticated(false);
        Intent goToHomePageIntent = new Intent(getContext(), HomeActivity.class);
        getContext().startActivity(goToHomePageIntent);
    }
}
