package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;
import android.net.Uri;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;

import it.ingsw.cinemates20_mobile.model.User;

public interface UserDAO {
    User getUserdata(CognitoUserSession userSession, Context context);

    Boolean editUserData(String newName, String newSurname, String newNickname, String newBio, Context context);

    void setProfileImage(Uri profileImagePath, Context context);
}
