package com.winning.artemis_guard.core;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.winning.artemis_guard.model.TouchEvent;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import static android.content.Context.WINDOW_SERVICE;

public class ArtemisActivityLifecycle implements Application.ActivityLifecycleCallbacks,IDisposable {
    private static volatile ArtemisActivityLifecycle sInstance = null;
//    private InflaterDelegateFactory inflaterDelegateFactory;

    private WeakHashMap<Activity, InflaterDelegateFactory> mInflaterDelegateMap;
    private ConcurrentHashMap<AppCompatActivity,List<View>> mViewHashMap;
    private Application mApplication;

    private ArtemisActivityLifecycle(Application application) {
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
//            LayoutInflater layoutInflater = activity.getLayoutInflater();
//            try {
//                Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
//                field.setAccessible(true);
//                field.setBoolean(layoutInflater, false);
//                inflaterDelegateFactory = getInflaterDelegate(ArtemisActivityLifecycle.this,(AppCompatActivity) activity);
//                LayoutInflaterCompat.setFactory(activity.getLayoutInflater(), inflaterDelegateFactory);
//            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
            showFloatingView(activity);
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
        recordOperatePath(activity);
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

    private void recordOperatePath(Activity activity) {
        LinkedHashMap<AppCompatActivity,MarkViewGroup> markViewGroupHashMap = OperatePath.getInstance().getConcurrentHashMap();
            if (null != markViewGroupHashMap && markViewGroupHashMap.size() > 0){
                MarkViewGroup markViewGroup = markViewGroupHashMap.get(activity);
                if (null != markViewGroup){
                    LinkedHashMap<AppCompatActivity,List<TouchEvent>> motionEvents = markViewGroup.getMotionEvents();
                    if (null != motionEvents && motionEvents.size() > 0){
                        OperatePath.getInstance().getMapQueue().offer(motionEvents);
                    }
                }
            }
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

    /**
     * Parse the activity parameters.
     * @param activity activity
     * @return HashMap
     */
    private static Map<String, Object> parseIntent(Activity activity){
        Map<String, Object> hashMap = new HashMap<>();
        if (activity == null)return hashMap;
        android.content.Intent intent = activity.getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Set<String> stringSet = bundle.keySet();
                for(String s: stringSet){
                    hashMap.put(s, bundle.get(s));
                }
            }
        }
        return hashMap;
    }

    public void showFloatingView(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(activity)) {
                showFloatView(activity);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                activity.startActivity(intent);
            }
        } else {
            showFloatView(activity);
        }
    }

    private void showFloatView(Activity activity) {
        WindowManager windowManager = (WindowManager) activity.getSystemService(WINDOW_SERVICE);
        MarkViewGroup floatView = new MarkViewGroup(activity);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        params.width = 1;
        params.height = 1;
        params.x = 0;
        params.y = 0;
        windowManager.addView(floatView, params);
    }
}
