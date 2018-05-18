package com.winning.artemis_guard.utils;

import android.app.Activity;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ActivityUtil {
    /**
     * Parse the activity parameters.
     * @param activity activity
     * @return HashMap
     */
    private static Map<String, Object> parseIntent(Activity activity){
        Map<String, Object> hashMap = new HashMap<>();
        if (activity == null)return hashMap;
        android.content.Intent intent = activity.getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Set<String> stringSet = bundle.keySet();
                for(String s: stringSet){
                    hashMap.put(s, bundle.get(s));
                }
            }
        }
        return hashMap;
    }
}
