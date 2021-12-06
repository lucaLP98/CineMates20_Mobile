package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import java.util.List;

import it.ingsw.cinemates20_mobile.model.Notification;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;

public interface NotificationDAO {
    void getNotificationsList(Context context, RequestCallback<List<Notification>> callback);
}
