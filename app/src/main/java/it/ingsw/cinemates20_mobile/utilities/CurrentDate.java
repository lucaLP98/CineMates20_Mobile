package it.ingsw.cinemates20_mobile.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {
    private static CurrentDate currentDateInstance;

    private final SimpleDateFormat formatter;

    private CurrentDate(){
        formatter= new SimpleDateFormat("dd-MM-yyyy 'alle' HH:mm");
    }

    public static CurrentDate getInstance(){
        if(currentDateInstance == null)
            currentDateInstance = new CurrentDate();

        return currentDateInstance;
    }

    public String getCurrentDate(){
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }
}
