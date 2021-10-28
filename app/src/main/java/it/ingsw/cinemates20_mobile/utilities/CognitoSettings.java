package it.ingsw.cinemates20_mobile.utilities;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    private String userPoolID = "us-east-2_ZDQpJ0hzW";
    private String clientID = "55pbo7deukaj1l13h24bhrnbio";
    private String clientSecret = "1hp6qqbvjce7uf24s50q4f58462rl4ku6k96nqt6laetdfm7t3n5";
    private Regions cognitoRegion = Regions.US_EAST_2;

    private Context context;

    public CognitoSettings(Context cont){
        this.context = cont;
    }

    public String getUserPoolID(){
        return userPoolID;
    }

    public String getClientID(){
        return clientID;
    }

    public String getClientSecret(){
        return clientSecret;
    }

    public Regions getRegion(){
        return cognitoRegion;
    }

    public CognitoUserPool getUserPool(){
        return new CognitoUserPool(context, userPoolID, clientID, clientSecret, cognitoRegion);
    }
}
