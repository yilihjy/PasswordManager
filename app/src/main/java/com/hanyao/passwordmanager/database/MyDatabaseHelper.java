package com.hanyao.passwordmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hanyao.passwordmanager.util.LogUtil;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER="create table User (" +
            "id integer primary key autoincrement," +
            "userName text," +
            "password text," +
            "salt text," +
            "version integer," +
            "time text)";
    public static final String CREATE_PASSWORD="create table Password(" +
            "id integer primary key autoincrement," +
            "loginSite text," +
            "loginName text," +
            "loginPassword text," +
            "effective integer," +
            "version integer," +
            "time text," +
            "question1 text," +
            "answer1 text," +
            "question2 text," +
            "answer2 text," +
            "question3 text," +
            "answer3 text," +
            "see integer)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_PASSWORD);
        LogUtil.d("database","create table success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
