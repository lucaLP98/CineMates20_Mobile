package it.ingsw.cinemates20_mobile.model;

public class Notification {
    private final int notificationID;
    private final String text;
    private final notificationTypeEnum notificationType;
    private Integer movieID;

    public enum notificationTypeEnum{
        SHARING,
        REQUEST,
        ACCEPT,
        DECLINE
    }

    public Notification(int notificationID, String text, notificationTypeEnum notificationType){
        this.notificationID = notificationID;
        this.text = text;
        this.notificationType = notificationType;
    }

    public Notification(int notificationID, String text, notificationTypeEnum notificationType, Integer movieID){
        this.notificationID = notificationID;
        this.text = text;
        this.notificationType = notificationType;
        this.movieID = movieID;
    }

    public int getNotificationID(){
        return notificationID;
    }

    public String getNotificationText(){
        return text;
    }

    public notificationTypeEnum getType(){
        return notificationType;
    }

    public Integer getMovieID(){
        return movieID;
    }
}
