package it.ingsw.cinemates20_mobile.utilities;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    private final String userPoolID = " USER_POOL_ID";
    private final String clientID = "CLIENT_ID";
    private final String clientSecret = "CLIENT_SECRET";
    private final Regions cognitoRegion = Regions.US_EAST_2;

    private final CognitoUserPool cognitoUserPool;
    private  static CognitoSettings cognitoSettingsInstance;

    private CognitoSettings(Context context){
        this.cognitoUserPool = new CognitoUserPool(context, userPoolID, clientID, clientSecret, cognitoRegion);
    }

    public CognitoUserPool getUserPool(){
        return cognitoUserPool;
    }

    public static CognitoSettings getInstance(Context context){
        if(cognitoSettingsInstance == null){
            cognitoSettingsInstance = new CognitoSettings(context);
        }

        return cognitoSettingsInstance;
    }
}
