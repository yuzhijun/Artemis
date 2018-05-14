package com.winning.artemis;

import android.app.Application;

import com.winning.artemis_guard.Artemis;

public class MyApplicaiton extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Artemis.getInstance(this);
    }
}
