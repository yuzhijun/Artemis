package com.winning.artemis_guard.core;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OperatePath {
    private static volatile OperatePath mInstance;
    //record operate path
    private Queue<LinkedHashMap<AppCompatActivity,List<MotionEvent>>> mMapQueue;
    //record lasted AppCompatActivity=>MarkViewGroup map
    private LinkedHashMap<AppCompatActivity,MarkViewGroup> mConcurrentHashMap;

    private OperatePath(){
        this.mConcurrentHashMap = new LinkedHashMap<>();
        this.mMapQueue = new ConcurrentLinkedQueue<>();
    }

    public static OperatePath getInstance(){
        if (null == mInstance){
            synchronized (OperatePath.class){
                if (null == mInstance){
                    mInstance = new OperatePath();
                }
            }
        }

        return mInstance;
    }

    public LinkedHashMap<AppCompatActivity, MarkViewGroup> getConcurrentHashMap() {
        return mConcurrentHashMap;
    }

    public Queue<LinkedHashMap<AppCompatActivity, List<MotionEvent>>> getMapQueue() {
        return mMapQueue;
    }
}
