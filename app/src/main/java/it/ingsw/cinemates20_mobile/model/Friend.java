package it.ingsw.cinemates20_mobile.model;

import android.net.Uri;

public class Friend extends User{
    private final int friendshipID;

    public Friend(String name, String surname, String nickname, String userID, Uri imageUri, int friendshipID){
        super(name, surname, nickname, imageUri, userID);
        this.friendshipID = friendshipID;
    }

    public int getFriendshipID(){
        return friendshipID;
    }
}
