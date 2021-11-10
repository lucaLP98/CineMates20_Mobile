package it.ingsw.cinemates20_mobile.presenters;

import android.content.Intent;
import android.view.View;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.views.activities.HomeActivity;
import it.ingsw.cinemates20_mobile.views.fragments.AccessFragment;
import it.ingsw.cinemates20_mobile.views.fragments.LoginFragment;
import it.ingsw.cinemates20_mobile.views.fragments.SingUpFragment;

public class AccessPresenter extends FragmentPresenter{

    public AccessPresenter(AccessFragment accessFragment, View inflate){
        super(accessFragment);
    }

    public void pressLoginButton(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new LoginFragment()).commit();
    }

    public void pressSingUpButton(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivityContainer, new SingUpFragment()).commit();
    }

    public void pressSingInLater(){
        User.setUserAuthenticated(false);
        Intent goToHomePageIntent = new Intent(getContext(), HomeActivity.class);
        getContext().startActivity(goToHomePageIntent);
    }
}
