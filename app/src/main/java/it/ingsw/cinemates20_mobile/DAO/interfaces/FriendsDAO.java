package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import java.util.List;

import it.ingsw.cinemates20_mobile.model.Friend;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;

public interface FriendsDAO {
    void getFriendsList(Context context, RequestCallback<List<Friend>> callback);

    void deleteFriend(Context context, int friendshipID);
}
