package com.winning.artemis_guard.model;

public class TouchEvent {
    private float x;
    private float y;
    private int ACTION_EVENT;

    public TouchEvent(float x, float y,int action_event) {
        this.x = x;
        this.y = y;
        this.ACTION_EVENT = action_event;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getACTION_EVENT() {
        return ACTION_EVENT;
    }

    public void setACTION_EVENT(int ACTION_EVENT) {
        this.ACTION_EVENT = ACTION_EVENT;
    }
}
