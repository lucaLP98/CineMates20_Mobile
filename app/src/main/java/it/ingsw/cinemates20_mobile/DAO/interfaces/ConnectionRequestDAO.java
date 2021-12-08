package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import java.util.List;

import it.ingsw.cinemates20_mobile.model.ConnectionRequest;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;

public interface ConnectionRequestDAO {
    void getConnectionRequests(Context context, RequestCallback<List<ConnectionRequest>> callback);

    void sendConnecctionRequests(Context context, String userReceiverID, RequestCallback<String> callback);

    void respondToConnectionRequest(Context context, int requestID, String sender, boolean respond);
}
