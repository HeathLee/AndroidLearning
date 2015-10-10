package com.example.heath.activitytest;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by heath on 15-9-16.
 */
public class ActivityCollector {
    public static ArrayList<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
