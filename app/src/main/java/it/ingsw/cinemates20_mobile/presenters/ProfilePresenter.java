package it.ingsw.cinemates20_mobile.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import it.ingsw.cinemates20_mobile.R;
import it.ingsw.cinemates20_mobile.model.ThisUser;
import it.ingsw.cinemates20_mobile.utilities.CognitoSettings;
import it.ingsw.cinemates20_mobile.views.activities.HomeActivity;
import it.ingsw.cinemates20_mobile.views.activities.MainActivity;
import it.ingsw.cinemates20_mobile.views.fragments.EditUserDataFragment;
import it.ingsw.cinemates20_mobile.views.fragments.FriendsFragment;
import it.ingsw.cinemates20_mobile.views.fragments.ProfileFragment;
import it.ingsw.cinemates20_mobile.views.fragments.UserReviewsFragment;

public class ProfilePresenter extends FragmentPresenter{
    private final TextView nameTextView;
    private final TextView nicknameTextView;
    private final TextView biographyTextView;
    private final ImageView profileImageView;

    private final Activity parentActivity;

    public ProfilePresenter(ProfileFragment profileFragment, @NonNull View inflate){
        super(profileFragment);

        nameTextView = inflate.findViewById(R.id.nameProfileTextView);
        nicknameTextView = inflate.findViewById(R.id.nicknameProfileTextView);
        biographyTextView = inflate.findViewById(R.id.biographyTextView);
        profileImageView = inflate.findViewById(R.id.profileImageView);

        parentActivity = profileFragment.getActivity();
    }

    public void setCustomizedTextView(){
        String name = null;
        String surname = null;
        String nickname = null;
        String bio= null;

        if(ThisUser.getInstance() != null){
            name = ThisUser.getInstance().getName();
            surname = ThisUser.getInstance().getSurname();
            nickname = ThisUser.getInstance().getNickname();
            bio = ThisUser.getInstance().getBiography();
        }

        if(name != null && surname != null && nickname != null && bio != null){
            String completeName = name + " " + surname;
            nameTextView.setText(completeName);
            nicknameTextView.setText(nickname);
            biographyTextView.setText(bio);
        }
    }

    public void setProfileImageView(){
        if(ThisUser.getInstance() != null && ThisUser.getInstance().getUriProfileImage() != null){
            Glide
                .with(getContext())
                .load(ThisUser.getInstance().getUriProfileImage())
                .centerCrop()
                .into(profileImageView);
        }
    }

    private void logout(){
        ThisUser user = ThisUser.getInstance();
        CognitoSettings.getInstance(getContext()).getUserPool().getUser(user.getEmail()).signOut();
        user.logout();
    }

    public void pressLogoutButton(){
        logout();

        Intent logoutIntent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(logoutIntent);
    }

    public void pressEdituserData(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container , new EditUserDataFragment()).commit();
    }

    public void pressViewFriendsListButton(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container , new FriendsFragment()).commit();
    }

    public void pressAddPhotoButton(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(parentActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, HomeActivity.PICTURE_REQUEST);
        }else{
            selectImageFromGalery();
        }
    }

    private void selectImageFromGalery(){
        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);

        selectImageIntent.setType("image/*");
        selectImageIntent.setAction(Intent.ACTION_GET_CONTENT);

        parentActivity.startActivityForResult(selectImageIntent, HomeActivity.PICTURE_REQUEST);
    }

    public void pressViewReviewsListButton(){
        getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_page_container , new UserReviewsFragment()).commit();
    }
}
