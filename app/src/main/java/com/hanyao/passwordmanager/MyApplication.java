package com.hanyao.passwordmanager;

import android.app.Application;
import android.content.Context;

import com.hanyao.passwordmanager.database.MyDatabaseHelper;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class MyApplication extends Application {
    private static Context context;
    private static String saber ="";

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        new MyDatabaseHelper(context,"PasswordManager.db",null,1).getWritableDatabase();
    }

    public static Context getContext(){
        return context;
    }

    public  static void areYouMyMaster(String saber){
        MyApplication.saber =saber;
    }
    public static String curse(){
        return saber;
    }
}
