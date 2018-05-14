package com.winning.artemis_guard.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class MarkViewGroup extends FrameLayout {

    public MarkViewGroup(@NonNull Context context) {
        this(context,null,0);
    }

    public MarkViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MarkViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int i = 0;
        return super.dispatchTouchEvent(ev);
    }
}
