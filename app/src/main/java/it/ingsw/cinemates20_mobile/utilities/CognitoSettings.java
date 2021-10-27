package it.ingsw.cinemates20_mobile.utilities;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.regions.Regions;

public class CognitoSettings {
    private String userPoolID = "us-east-2_oH0lxw5FI";
    private String clientID = "74nd976jf7age7iarukaftqb34";
    private String clientSecret = "d6m1e922b6v6bdgdcr1a45i86rvulaomqobtp5g74tlln7i6u9g";
    private Regions cognitoRegion = Regions.US_EAST_2;

    private Context context;

    public CognitoSettings(Context cont){
        this.context = cont;
    }

    public CognitoUserPool getUserPool(){
        return new CognitoUserPool(context, userPoolID, clientID, clientSecret, cognitoRegion);
    }
}
