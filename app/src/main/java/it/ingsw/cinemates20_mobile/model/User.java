package it.ingsw.cinemates20_mobile.model;

import android.net.Uri;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;

public class User {
    private static boolean authenticated;

    private String name;
    private String surname;
    private String nickname;
    private String eMail;
    private String biography = "";
    private Uri profileImage;
    private final CognitoUserSession userSession;

    private static User userInstance;

    private User(String name, String surname, String nickname, String eMail, CognitoUserSession userSession){
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
    public static User createInstance(String name, String surname, String nickname, String eMail, CognitoUserSession userSession){
        if(userInstance == null){
            userInstance = new User(name, surname, nickname, eMail, userSession);
        }

        return userInstance;
    }

    public static User getInstance(){
        if(userInstance == null)
            throw new NullPointerException();

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

    }

    public static void setUserAuthenticated(boolean login){
        User.authenticated = login;
    }

    public static boolean getUserAuthenticated(){
        return User.authenticated;
    }

    public void logout(){
        User.userInstance = null;
        User.setUserAuthenticated(false);
    }
}
