package com.winning.artemis_guard.core;

import android.content.Context;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * inject view create
 * Created by yuzhijun on 2018/3/28.
 */
public class InflaterDelegateFactory implements LayoutInflaterFactory {
    private ArtemisActivityLifecycle mArtemisActivityLifecycle;
    private final AppCompatActivity mAppCompatActivity;
    private LayoutInflater mLayoutInflater;
    private final String[] sClassPrefix = {
            "android.widget.",
            "android.webkit.",
            "android.view."
    };

    private InflaterDelegateFactory(ArtemisActivityLifecycle artemisActivityLifecycle, AppCompatActivity appCompatActivity) {
        this.mArtemisActivityLifecycle = artemisActivityLifecycle;
        this.mAppCompatActivity = appCompatActivity;
        this.mLayoutInflater = appCompatActivity.getLayoutInflater();
    }
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;
        if (view == null) {
            view = createViewOrFailQuietly(name,context,attrs);
        }

        //view created
        if (null != view){
            List<View> mViews = mArtemisActivityLifecycle.getViewHashMap().get(mAppCompatActivity);
            if (null == mViews){
                mViews = new ArrayList<>();
            }
            mViews.add(view);
            mArtemisActivityLifecycle.getViewHashMap().put(mAppCompatActivity, mViews);
        }

        return  view;
    }

    private View createViewOrFailQuietly(String name, Context context, AttributeSet attrs) {
        //1.custom components should not add prefix
        if (name.contains(".")) {
            createViewOrFailQuietly(name, null, context, attrs);
        }
        //2.system components should add prefix
        for (String prefix : sClassPrefix) {
            View view = createViewOrFailQuietly(name, prefix, context, attrs);
            if (view != null) {
                return view;
            }
        }
        return null;
    }

    private View createViewOrFailQuietly(String name, String prefix, Context context,
                                         AttributeSet attrs) {
        try {
            return mLayoutInflater.createView(name, prefix, attrs);
        } catch (Exception e) {
            return null;
        }
    }

    public static InflaterDelegateFactory create(ArtemisActivityLifecycle artemisActivityLifecycle, AppCompatActivity appCompatActivity) {
        return new InflaterDelegateFactory(artemisActivityLifecycle,appCompatActivity);
    }
}
