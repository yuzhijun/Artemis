package com.winning.artemis_guard.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.winning.artemis_guard.model.TouchEvent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MarkViewGroup extends FrameLayout {
    private LinkedHashMap<AppCompatActivity,List<TouchEvent>> mMotionEvents;
    private WeakReference<AppCompatActivity> mWeakReference;

    public MarkViewGroup(@NonNull Context context) {
        this(context,null,0);
    }

    public MarkViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MarkViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mWeakReference = new WeakReference<>((AppCompatActivity) context);
        mMotionEvents = new LinkedHashMap<>();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        handleMotionEvent(mWeakReference.get(),ev,null);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() ==  KeyEvent.KEYCODE_BACK){
            handleMotionEvent(mWeakReference.get(),null,event);
            mWeakReference.get().finish();
        }
        return super.dispatchKeyEvent(event);
    }

    private void handleMotionEvent(AppCompatActivity context, MotionEvent event, KeyEvent keyEvent){
        if (null == context){
            return;
        }

        if (null == mMotionEvents){
            mMotionEvents = new LinkedHashMap<>();
        }

        List<TouchEvent> motionEvents = mMotionEvents.get(context);
        if (null == motionEvents){
           motionEvents = new ArrayList<>();
        }

        if (null != event){
            motionEvents.add(new TouchEvent(event.getX(),event.getY(),event.getAction(),event.getEventTime(),event.getDownTime()));
        }else{
            motionEvents.add(new TouchEvent(new TouchEvent.KeyBackEvent(keyEvent.getDeviceId(),keyEvent.getSource(),keyEvent.getMetaState(),keyEvent.getAction(),
                    keyEvent.getKeyCode(),keyEvent.getScanCode(),keyEvent.getRepeatCount(),keyEvent.getFlags(),keyEvent.getDownTime(),keyEvent.getEventTime())));
        }
        mMotionEvents.put(context,motionEvents);

        OperatePath.getInstance().getConcurrentHashMap().put(context, this);
    }

    public LinkedHashMap<AppCompatActivity, List<TouchEvent>> getMotionEvents() {
        return mMotionEvents;
    }
}
