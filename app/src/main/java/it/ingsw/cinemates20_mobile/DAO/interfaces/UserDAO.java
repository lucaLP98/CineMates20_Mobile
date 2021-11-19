package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;

import it.ingsw.cinemates20_mobile.model.User;

public interface UserDAO {
    public User getUserdata(CognitoUserSession userSession, Context context);

    public void editUserData(String newName, String newSurname, String newNickname, String newBio);
}
