package it.ingsw.cinemates20_mobile.model;

import android.net.Uri;

import androidx.annotation.Nullable;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;

public class ThisUser{
    private static boolean authenticated;

    private String name;
    private String surname;
    private String nickname;
    private String biography = "";
    private Uri profileImage;
    private final String eMail;
    private final CognitoUserSession userSession;

    private static ThisUser userInstance;

    private ThisUser(String name, String surname, String nickname, String eMail, CognitoUserSession userSession){
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.eMail = eMail;
        this.userSession = userSession;
    }

    /*
        This method allow to create an instance of a User. User class si a Singleto.
        If there are already an instance of User, this method return the insance which
        is already present
     */
    public static ThisUser createInstance(String name, String surname, String nickname, String eMail, CognitoUserSession userSession){
        if(userInstance == null){
            userInstance = new ThisUser(name, surname, nickname, eMail, userSession);
        }

        return userInstance;
    }

    @Nullable
    public static ThisUser getInstance(){
        if(userInstance == null)
            return null;

        return userInstance;
    }

    public String getName(){ return name; }

    public String getSurname(){ return surname; }

    public String getNickname(){ return nickname; }

    public String getEmail(){ return eMail; }

    public String getBiography(){ return biography; }

    public CognitoUserSession getUserSession(){
        return userSession;
    }

    public Uri getUriProfileImage(){ return profileImage; }

    public void setName(String newName){
        this.name = newName;
    }

    public void setSurname(String newSurname){
        this.surname = newSurname;
    }

    public void setNickname(String newNickname){
        this.nickname = newNickname;
    }

    public void setBiography(String text){
        this.biography = text;
    }

    public void setProfileImage(Uri newImage){
        this.profileImage = newImage;
    }

    public String getUserID(){ return this.userSession.getUsername(); }

    public static void setUserAuthenticated(boolean login){
        ThisUser.authenticated = login;
    }

    public static boolean getUserAuthenticated(){
        return ThisUser.authenticated;
    }

    public void logout(){
        ThisUser.userInstance = null;
        ThisUser.setUserAuthenticated(false);
    }
}
