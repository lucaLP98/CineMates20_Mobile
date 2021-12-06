package it.ingsw.cinemates20_mobile.model;

public class Notification {
    private final int notificationID;
    private final String text;

    public Notification(int notificationID, String text){
        this.notificationID = notificationID;
        this.text = text;
    }

    public int getNotificationID(){
        return notificationID;
    }

    public String getNotificationText(){
        return text;
    }
}
