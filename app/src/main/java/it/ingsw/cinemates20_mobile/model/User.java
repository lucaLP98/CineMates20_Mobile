package it.ingsw.cinemates20_mobile.model;

import android.net.Uri;

public class User {
    private static boolean authenticated;

    private String name;
    private String surname;
    private String nickname;
    private String eMail;
    private String biography;
    private Uri profileImage;

    private static User userInstance;

    private User(String name, String surname, String nickname, String eMail){
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.eMail = eMail;
    }

    public static User getInstance(String name, String surname, String nickname, String eMail){
        if(userInstance == null){
            userInstance = new User(name, surname, nickname, eMail);
        }

        return userInstance;
    }

    public String getName(){ return name; }

    public String getSurname(){ return surname; }

    public String getUsername(){ return nickname; }

    public String getEmail(){ return eMail; }

    public void setProfileImage(Uri newImage){

    }

    public void setBiography(String text){
        this.biography = text;
    }

    public static void setUserAuthenticated(boolean login){
        User.authenticated = login;
    }

    public static boolean getUserAuthenticated(){
        return User.authenticated;
    }
}
