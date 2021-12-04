package it.ingsw.cinemates20_mobile.utilities;

import android.content.Context;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class CloudinarySettings {
    private final String cloudName = "disob0iu6";
    private final String APIkey = "395938578982147";
    private final String APIsecret = "3Ktd666Y3JqoZirC-lAjbhOzo2M";
    //private final String cloudinaryURL = "cloudinary://395938578982147:3Ktd666Y3JqoZirC-lAjbhOzo2M@disob0iu6";

    private static CloudinarySettings cloudinarySettingInstance;

    private CloudinarySettings(Context context){
        init(context);
    }

    public static CloudinarySettings getInstance(Context context){
        if(cloudinarySettingInstance == null){
            cloudinarySettingInstance = new CloudinarySettings(context);
        }

        return cloudinarySettingInstance;
    }

    private void init(Context context){
        Map config = new HashMap();

        config.put("cloud_name", cloudName);
        config.put("api_key", APIkey);
        config.put("api_secret", APIsecret);
        config.put("secure", true);

        MediaManager.init(context, config);
    }
}
