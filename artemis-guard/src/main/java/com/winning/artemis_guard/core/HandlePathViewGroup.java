package com.winning.artemis_guard.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 用于记录除dialog外所有的view的点击事件
 * 最终用于记录用户实现自动化埋点功能
 * */
public class HandlePathViewGroup extends FrameLayout {
    private LinkedHashMap<AppCompatActivity,List<MotionEvent>> mMotionEvents;
    private WeakReference<AppCompatActivity> mWeakReference;


    public HandlePathViewGroup(@NonNull Context context) {
        this(context,null,0);
    }

    public HandlePathViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HandlePathViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mWeakReference = new WeakReference<>((AppCompatActivity) context);
        mMotionEvents = new LinkedHashMap<>();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        handleMotionEvent(mWeakReference.get(),ev);
        return super.dispatchTouchEvent(ev);
    }

    private void handleMotionEvent(AppCompatActivity context, MotionEvent event){
        if (null == context){
            return;
        }

        if (null == mMotionEvents){
            mMotionEvents = new LinkedHashMap<>();
        }

        List<MotionEvent> motionEvents = mMotionEvents.get(context);
        if (null == motionEvents){
            motionEvents = new ArrayList<>();
        }

        motionEvents.add(event);
        mMotionEvents.put(context,motionEvents);
    }
}
