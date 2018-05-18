package com.winning.artemis_guard.model;

public class TouchEvent {
    private float x;
    private float y;
    private long downTime;
    private long eventTime;
    private int ACTION_EVENT;
    private KeyBackEvent keyEvent;

    public TouchEvent(float x, float y,int action_event,long downTime,long eventTime) {
        this.x = x;
        this.y = y;
        this.ACTION_EVENT = action_event;
        this.downTime = downTime;
        this.eventTime = eventTime;
    }

    public TouchEvent(KeyBackEvent keyEvent) {
        this.keyEvent = keyEvent;
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


    public long getDownTime() {
        return downTime;
    }

    public void setDownTime(long downTime) {
        this.downTime = downTime;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public KeyBackEvent getKeyEvent() {
        return keyEvent;
    }

    public void setKeyEvent(KeyBackEvent keyEvent) {
        this.keyEvent = keyEvent;
    }

    public static class KeyBackEvent{
        private int mDeviceId;
        private int mSource;
        private int mMetaState;
        private int mAction;
        private int mKeyCode;
        private int mScanCode;
        private int mRepeatCount;
        private int mFlags;
        private long mDownTime;
        private long mEventTime;

        public KeyBackEvent(int deviceId, int source, int metaState, int action, int keyCode, int scanCode, int repeatCount, int flags, long downTime, long eventTime) {
            mDeviceId = deviceId;
            mSource = source;
            mMetaState = metaState;
            mAction = action;
            mKeyCode = keyCode;
            mScanCode = scanCode;
            mRepeatCount = repeatCount;
            mFlags = flags;
            mDownTime = downTime;
            mEventTime = eventTime;
        }

        public int getDeviceId() {
            return mDeviceId;
        }

        public void setDeviceId(int deviceId) {
            mDeviceId = deviceId;
        }

        public int getSource() {
            return mSource;
        }

        public void setSource(int source) {
            mSource = source;
        }

        public int getMetaState() {
            return mMetaState;
        }

        public void setMetaState(int metaState) {
            mMetaState = metaState;
        }

        public int getAction() {
            return mAction;
        }

        public void setAction(int action) {
            mAction = action;
        }

        public int getKeyCode() {
            return mKeyCode;
        }

        public void setKeyCode(int keyCode) {
            mKeyCode = keyCode;
        }

        public int getScanCode() {
            return mScanCode;
        }

        public void setScanCode(int scanCode) {
            mScanCode = scanCode;
        }

        public int getRepeatCount() {
            return mRepeatCount;
        }

        public void setRepeatCount(int repeatCount) {
            mRepeatCount = repeatCount;
        }

        public int getFlags() {
            return mFlags;
        }

        public void setFlags(int flags) {
            mFlags = flags;
        }

        public long getDownTime() {
            return mDownTime;
        }

        public void setDownTime(long downTime) {
            mDownTime = downTime;
        }

        public long getEventTime() {
            return mEventTime;
        }

        public void setEventTime(long eventTime) {
            mEventTime = eventTime;
        }
    }

}
