package it.ingsw.cinemates20_mobile.utilities;

import android.content.Context;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class CloudinarySettings {
    private final String cloudName = "CLOUD_NAME";
    private final String APIkey = "API_KEY";
    private final String APIsecret = "API_SECRET";

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
        Map<String, Object> config = new HashMap<>();

        config.put("cloud_name", cloudName);
        config.put("api_key", APIkey);
        config.put("api_secret", APIsecret);
        config.put("secure", true);

        MediaManager.init(context, config);
    }
}
