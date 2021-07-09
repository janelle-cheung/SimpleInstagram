package com.example.simpleinstagram;

import android.app.Application;

import com.example.simpleinstagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("DsajerTCcww4E7z7v7647t65XFH7vpnBOd2irY7P")
                .clientKey("BUutrHPUcKIFrsoKFmZZc0dxhaRv8fhsH4TmuDjp")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
