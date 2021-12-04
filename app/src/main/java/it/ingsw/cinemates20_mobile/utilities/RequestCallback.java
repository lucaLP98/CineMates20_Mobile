package it.ingsw.cinemates20_mobile.utilities;

import androidx.annotation.NonNull;

import com.android.volley.VolleyError;

public interface RequestCallback <T>{
    void onSuccess(@NonNull T result);

    void onError(@NonNull VolleyError error);
}
