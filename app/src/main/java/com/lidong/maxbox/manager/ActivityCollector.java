package com.lidong.maxbox.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 17-9-20.
 * 用于 标识  activity栈中的 activity，方便多界面的跳转与维护
 * 可以 方便的清空 不需要的 activity
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
        if (activity.isFinishing()) {
            activity.finish();
        }
    }

    public static void finishAllActivities() {
        for (Activity activity : activities) {
            if( !activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }
}
