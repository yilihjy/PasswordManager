package com.hanyao.passwordmanager.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hanyao.passwordmanager.util.LogUtil;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        LogUtil.d("ActivityName", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
