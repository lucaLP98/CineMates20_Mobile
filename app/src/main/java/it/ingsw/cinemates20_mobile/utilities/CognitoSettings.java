package it.ingsw.cinemates20_mobile.utilities;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    private String userPoolID = " us-east-2_XmVrbqS7s";
    private String clientID = "36lg0jvgdnr95a32gepj5s712f";
    private String clientSecret = "ubpprb28bm8la2l607dk1b41hd39ndd757he2nt18l5i33jdbk5";
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
