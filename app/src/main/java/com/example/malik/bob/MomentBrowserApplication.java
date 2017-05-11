package com.example.malik.bob;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by malik on 26-04-2017.
 */

public class MomentBrowserApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
