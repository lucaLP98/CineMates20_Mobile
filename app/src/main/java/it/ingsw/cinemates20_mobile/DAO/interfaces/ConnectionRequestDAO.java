package it.ingsw.cinemates20_mobile.DAO.interfaces;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

import it.ingsw.cinemates20_mobile.model.ConnectionRequest;
import it.ingsw.cinemates20_mobile.utilities.RequestCallback;

public interface ConnectionRequestDAO {
    void getConnectionRequests(@NonNull Context context, @NonNull RequestCallback<List<ConnectionRequest>> callback);

    void sendConnecctionRequests(@NonNull Context context, @NonNull String userReceiverID);

    void respondToConnectionRequest(@NonNull Context context, int requestID, @NonNull String sender, boolean respond);
}
