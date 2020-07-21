package com.iwalnexus.tsn.socialapp.AplicationClass;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDexApplication;

public class AplicationClass extends MultiDexApplication {

    private static  AplicationClass instance;

    public static AplicationClass getInstance(){return instance;}

    public static Context getContext(){return instance;}

    @Override
    public void onCreate() {

        instance = this;
        super.onCreate();
    }
}
