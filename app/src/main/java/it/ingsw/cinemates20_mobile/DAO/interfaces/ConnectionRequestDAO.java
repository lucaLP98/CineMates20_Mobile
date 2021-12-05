package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import it.ingsw.cinemates20_mobile.utilities.RequestCallback;

public interface ConnectionRequestDAO {
    void getConnectionRequests(Context context, RequestCallback<?> callback);

    void sendConnecctionRequests(Context context, String userReceiverID, RequestCallback<String> callback);
}
