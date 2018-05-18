package com.winning.artemis_guard.core.uimock;

import android.app.Instrumentation;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.winning.artemis_guard.core.OperatePath;
import com.winning.artemis_guard.model.TouchEvent;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
/**
 * TODO
 * 此类仅做测试
 * */
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
        final Queue<LinkedHashMap<AppCompatActivity,List<TouchEvent>>> mMapQueue = OperatePath.getInstance().getMapQueue();
        if (null != mMapQueue && mMapQueue.size() > 0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    uiMock(mMapQueue);
                }
            }).start();
        }
    }

    private void uiMock(final Queue<LinkedHashMap<AppCompatActivity, List<TouchEvent>>> mMapQueue) {
        if (null == mMapQueue || mMapQueue.isEmpty()) {
            return;
        }

        final LinkedHashMap<AppCompatActivity,List<TouchEvent>> mockHashMap = mMapQueue.poll();
        Set<Map.Entry<AppCompatActivity,List<TouchEvent>>> entrySet = mockHashMap.entrySet();

        for (Map.Entry<AppCompatActivity,List<TouchEvent>> entry : entrySet){
            final AppCompatActivity appCompatActivity = entry.getKey();
            final List<TouchEvent> motionEvents = entry.getValue();

            handleEvent(appCompatActivity,motionEvents);

            uiMock(mMapQueue);
        }
    }

    private void handleEvent(AppCompatActivity appCompatActivity,List<TouchEvent> motionEvents) {
        boolean successfull = false;
        int retry = 0;

        while (!successfull && retry < 20){
            //mock ui handle event
            for (TouchEvent motionEvent : motionEvents){
                MotionEvent event = MotionEvent.obtain(motionEvent.getDownTime(), motionEvent.getEventTime(),
                       motionEvent.getACTION_EVENT(), motionEvent.getX(), motionEvent.getY(), 0);

                try{
                    mInstrumentation.sendPointerSync(event);
                }catch (SecurityException e){
                    retry ++;
                }
            }

            successfull = true;
        }
    }
}
