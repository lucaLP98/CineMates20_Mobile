package it.ingsw.cinemates20_mobile.presenters;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.activities.MainActivity;
import it.ingsw.cinemates20_mobile.views.fragments.EditUserDataFragment;
import it.ingsw.cinemates20_mobile.views.fragments.ProfileFragment;

public class ProfilePresenter extends FragmentPresenter{
    private final TextView nameTextView;
    private final TextView nicknameTextView;
    private final TextView biographyTextView;
    private final Button reviewsListButton;
    private final Button friendsListButton;
    private final ImageView profileImage;

    public ProfilePresenter(ProfileFragment profileFragment, @NonNull View inflate){
        super(profileFragment);

        nameTextView = inflate.findViewById(R.id.nameProfileTextView);
        nicknameTextView = inflate.findViewById(R.id.nicknameProfileTextView);
        reviewsListButton = inflate.findViewById(R.id.reviewsMadeButton);
        friendsListButton = inflate.findViewById(R.id.friendsListButton);
        biographyTextView = inflate.findViewById(R.id.biographyTextView);
        profileImage = inflate.findViewById(R.id.profileImageView);
    }

    public void setCustomizedTextView(){
        String name = User.getInstance().getName();
        String surname = User.getInstance().getSurname();
        String nickname = User.getInstance().getNickname();
        String bio = User.getInstance().getBiography();

        if(name != null && surname != null && nickname != null && bio != null){
            String completeName = name + " " + surname;
            nameTextView.setText(completeName);
            nicknameTextView.setText(nickname);
            biographyTextView.setText(bio);
        }
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

    public void pressEdituserData(){
        EditUserDataFragment editUserDataFragment = new EditUserDataFragment();
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container , editUserDataFragment).commit();
    }
}
