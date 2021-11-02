package it.ingsw.cinemates20_mobile.model;

import android.net.Uri;

public class User {
    private static boolean authenticated;

    private String name;
    private String surname;
    private String username;
    private String eMail;
    private String biography;
    private Uri profileImage;

    public User(String name, String surname, String username, String eMail){
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.eMail = eMail;
    }

    public String getName(){ return name; }

    public String getSurname(){ return surname; }

    public String getUsername(){ return username; }

    public String getEmail(){ return eMail; }

    public void setProfileImage(Uri newImage){

    }

    public static void setUserAuthenticated(boolean login){
        User.authenticated = login;
    }

    public static boolean getUserAuthenticated(){
        return User.authenticated;
    }
}
