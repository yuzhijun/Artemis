package com.winning.artemis_guard.core.uimock;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.winning.artemis_guard.core.OperatePath;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class UiMock {
    private static UiMock mInstance;
    private  Instrumentation mInstrumentation;

    private UiMock(){
        mInstrumentation = new Instrumentation();
    }

    public static UiMock getInstance(){
        if (null == mInstance){
            synchronized (UiMock.class){
                if (null == mInstance){
                    mInstance = new UiMock();
                }
            }
        }
        return mInstance;
    }

    public void mock(){
        Queue<LinkedHashMap<AppCompatActivity,List<MotionEvent>>> mMapQueue = OperatePath.getInstance().getMapQueue();
        if (null != mMapQueue && mMapQueue.size() > 0){
            uiMock(mMapQueue);
        }
    }

    private void uiMock(final Queue<LinkedHashMap<AppCompatActivity, List<MotionEvent>>> mMapQueue) {
        if (null == mMapQueue) {
            return;
        }

        final LinkedHashMap<AppCompatActivity,List<MotionEvent>> mockHashMap = mMapQueue.poll();

        Set<Map.Entry<AppCompatActivity,List<MotionEvent>>> entrySet = mockHashMap.entrySet();
        for (Map.Entry<AppCompatActivity,List<MotionEvent>> entry : entrySet){
            final AppCompatActivity appCompatActivity = entry.getKey();
            final List<MotionEvent> motionEvents = entry.getValue();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    handleEvent(appCompatActivity,motionEvents);

                    uiMock(mMapQueue);
                }
            }).start();
        }
    }

    private void handleEvent(AppCompatActivity appCompatActivity,List<MotionEvent> motionEvents) {
        //turn off touch mode
        mInstrumentation.setInTouchMode(false);

        //start activity
        Intent intent = new Intent();
        intent.setClassName(appCompatActivity.getPackageName(),appCompatActivity.getClass().getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mInstrumentation.startActivitySync(intent);
        mInstrumentation.waitForIdleSync();

        //mock ui handle event
        for (MotionEvent motionEvent : motionEvents){
            mInstrumentation.sendPointerSync(motionEvent);
        }
    }
}
