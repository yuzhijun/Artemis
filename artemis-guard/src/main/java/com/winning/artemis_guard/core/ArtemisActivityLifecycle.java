package com.winning.artemis_guard.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Field;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ArtemisActivityLifecycle implements Application.ActivityLifecycleCallbacks,IDisposable {
    private static volatile ArtemisActivityLifecycle sInstance = null;
    private WeakHashMap<Activity, InflaterDelegateFactory> mInflaterDelegateMap;
    private ConcurrentHashMap<AppCompatActivity,List<View>> mViewHashMap;
    private Application mApplication;

    public ArtemisActivityLifecycle(Application application) {
        mApplication = application;
        mViewHashMap = new ConcurrentHashMap<>();
        application.registerActivityLifecycleCallbacks(this);
    }

    public static ArtemisActivityLifecycle init(Application application) {
        if (sInstance == null) {
            synchronized (ArtemisActivityLifecycle.class) {
                if (sInstance == null) {
                    sInstance = new ArtemisActivityLifecycle(application);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof AppCompatActivity){
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            try {
                Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
                field.setAccessible(true);
                field.setBoolean(layoutInflater, false);
                LayoutInflaterCompat.setFactory(activity.getLayoutInflater(), getInflaterDelegate(ArtemisActivityLifecycle.this,(AppCompatActivity) activity));
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private InflaterDelegateFactory getInflaterDelegate(ArtemisActivityLifecycle artemisActivityLifecycle,AppCompatActivity activity) {
        if (mInflaterDelegateMap == null) {
            mInflaterDelegateMap = new WeakHashMap<>();
        }

        InflaterDelegateFactory mInflaterDelegate = mInflaterDelegateMap.get(activity);
        if (mInflaterDelegate == null) {
            mInflaterDelegate = InflaterDelegateFactory.create(artemisActivityLifecycle,activity);
        }
        mInflaterDelegateMap.put(activity, mInflaterDelegate);
        return mInflaterDelegate;
    }

    public ConcurrentHashMap<AppCompatActivity, List<View>> getViewHashMap() {
        return mViewHashMap;
    }

    @Override
    public void stop() {
        mApplication.unregisterActivityLifecycleCallbacks(sInstance);
        sInstance = null;
        mInflaterDelegateMap = null;
    }
}
