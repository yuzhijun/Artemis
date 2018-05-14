package com.winning.artemis_guard;

import android.app.Application;

import com.winning.artemis_guard.core.ArtemisActivityLifecycle;
import com.winning.artemis_guard.core.IDisposable;

/**
 * Entrance
 * */
public class Artemis implements IDisposable{
    private static volatile  Artemis sInstance;
    private final Application mApplication;
    private ArtemisActivityLifecycle mArtemisActivityLifecycle;

    private Artemis(Application application){
        this.mApplication = application;
        mArtemisActivityLifecycle = ArtemisActivityLifecycle.init(application);
    }

    public static Artemis getInstance(Application application){
        if (null == sInstance) {
            synchronized (Artemis.class){
                if (null == sInstance){
                    sInstance = new Artemis(application);
                }
            }
        }

        return sInstance;
    }


    @Override
    public void stop() {
        mArtemisActivityLifecycle.stop();
    }
}
