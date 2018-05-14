package com.winning.artemis_guard.utils;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import java.util.List;
/**
 * calculate max inflater depth
 * @author yuzhijun
 * */
public class InflateDepthUtil {
    /**
     * calculate max inflater depth
     * @param views all views
     * @Return long maxDepth
     * */
    public static long calculateDepthInflater(List<View> views){
        long maxDepth = 0;
        long viewDepth;
        for (View view : views){
            viewDepth = 0;
            do {
                if (view instanceof CoordinatorLayout){
                    if (viewDepth > maxDepth){
                        maxDepth = viewDepth;
                    }
                    break;
                }else if (view instanceof FrameLayout){
                    if (view.getId() == android.R.id.content){
                        if (viewDepth > maxDepth){
                            maxDepth = viewDepth;
                        }
                        break;
                    }else{
                        break;
                    }
                }

                if (view != null) {
                    final ViewParent parent = view.getParent();
                    view = parent instanceof View ? (View) parent : null;
                }
                viewDepth ++;
            } while (view != null);
        }
        return maxDepth;
    }

    public static long calculateDepthInflater(View view){
        long maxDepth = 0;
        long viewDepth = 0;
        do {
            if (view instanceof CoordinatorLayout){
                if (viewDepth > maxDepth){
                    maxDepth = viewDepth;
                }
                break;
            }else if (view instanceof FrameLayout){
                if (view.getId() == android.R.id.content){
                    if (viewDepth > maxDepth){
                        maxDepth = viewDepth;
                    }
                    break;
                }else{
                    break;
                }
            }

            if (view != null) {
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
            viewDepth ++;
        } while (view != null);

        return maxDepth;
    }
}
