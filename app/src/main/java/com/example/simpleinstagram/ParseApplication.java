package com.example.simpleinstagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("DsajerTCcww4E7z7v7647t65XFH7vpnBOd2irY7P")
                .clientKey("BUutrHPUcKIFrsoKFmZZc0dxhaRv8fhsH4TmuDjp")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
