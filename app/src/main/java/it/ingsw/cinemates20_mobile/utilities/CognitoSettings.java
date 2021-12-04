package it.ingsw.cinemates20_mobile.utilities;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    private final String userPoolID = " us-east-2_XmVrbqS7s";
    private final String clientID = "36lg0jvgdnr95a32gepj5s712f";
    private final String clientSecret = "ubpprb28bm8la2l607dk1b41hd39ndd757he2nt18l5i33jdbk5";
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
