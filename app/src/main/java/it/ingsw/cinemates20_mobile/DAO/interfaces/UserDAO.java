package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;
import android.net.Uri;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;

import java.util.List;

import it.ingsw.cinemates20_mobile.model.User;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;

public interface UserDAO {
    void getUserdata(CognitoUserSession userSession, Context context);

    void editUserData(String newName, String newSurname, String newNickname, String newBio, Context context);

    void setProfileImage(Uri profileImagePath, Context context);

    void searchUsers(Context context, String userName, String userSurname, RequestCallback<List<User>> callback);
}
