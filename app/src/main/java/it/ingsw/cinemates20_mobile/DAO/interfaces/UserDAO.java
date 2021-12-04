package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;

import it.ingsw.cinemates20_mobile.model.ThisUser;

public interface UserDAO {
    void getUserdata(CognitoUserSession userSession, Context context);

    Boolean editUserData(String newName, String newSurname, String newNickname, String newBio, Context context);

    void setProfileImage(Uri profileImagePath, Context context);

    void searchUsers(Context context, String userName, String userSurname, RecyclerView recyclerView);
}
