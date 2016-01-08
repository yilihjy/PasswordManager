package com.hanyao.passwordmanager.activity;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void  removeActivity(Activity activity){
        activities.remove(activity);
    }

    /**
     * 退出程序
     */
    public static  void finishAll(){
        for (Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
