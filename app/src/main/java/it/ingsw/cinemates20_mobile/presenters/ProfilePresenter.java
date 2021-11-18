package it.ingsw.cinemates20_mobile.presenters;

import android.content.Intent;
import android.view.View;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.activities.MainActivity;
import it.ingsw.cinemates20_mobile.views.fragments.ProfileFragment;

public class ProfilePresenter extends FragmentPresenter{
    public ProfilePresenter(ProfileFragment profileFragment, View inflate){
        super(profileFragment);
    }

    private void logout(){
        User user = User.getInstance();
        CognitoSettings.getInstance(getContext()).getUserPool().getUser(user.getEmail()).signOut();
        user.logout();
    }

    public void pressLogoutButton(){
        logout();

        Intent logoutIntent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(logoutIntent);
    }
}
