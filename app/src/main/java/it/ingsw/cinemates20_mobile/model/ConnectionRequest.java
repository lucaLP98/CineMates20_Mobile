package it.ingsw.cinemates20_mobile.model;

public class ConnectionRequest {
    private final User requestSenderUser;
    private final int connectionRequestID;

    public ConnectionRequest(User requestSenderUser, int connectionRequestID) {
        this.requestSenderUser = requestSenderUser;
        this.connectionRequestID = connectionRequestID;
    }

    public User getUserSender(){ return requestSenderUser; }

    public int getConnectionRequestID(){ return connectionRequestID; }
}
