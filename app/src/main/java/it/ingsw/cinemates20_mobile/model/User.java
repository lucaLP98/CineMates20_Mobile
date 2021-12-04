package it.ingsw.cinemates20_mobile.model;

import android.net.Uri;

public class User {
    private final String name;
    private final String surname;
    private final String nickname;
    private final Uri profileImage;
    private final String userID;


    public User(String name, String surname, String nickname, Uri profileImage, String userID) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.userID = userID;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getNickname(){
        return nickname;
    }

    public String getUserID(){
        return userID;
    }

    public Uri getProfileImage(){
        return profileImage;
    }
}
