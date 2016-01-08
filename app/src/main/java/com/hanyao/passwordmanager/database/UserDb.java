package com.hanyao.passwordmanager.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hanyao.passwordmanager.bean.User;
import com.hanyao.passwordmanager.util.LogUtil;
import com.hanyao.passwordmanager.MyApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class UserDb {
    private  SQLiteDatabase db ;
    public UserDb(){
        db = new MyDatabaseHelper(MyApplication.getContext(),"PasswordManager.db",null,1).getWritableDatabase();
    }
    public UserDb(SQLiteDatabase db){
        this.db=db;
    }

    /**
     * 保存user对象
     * @param user
     * @return
     */
    public int save(User user){
        ContentValues values = new ContentValues();
        values.put("userName", user.getUserName());
        values.put("password", user.getPassword());
        values.put("salt", user.getSalt());
        values.put("version", user.getVersion());
        values.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getTime()));
        int id = (int)db.insert("User", null, values);
        if (id!=-1){
            //LogUtil.d("database", "save user success");
            return id;
        }
        return -1;

    }

    /**
     * 根据用户取得user对象
     * @param userName
     * @return
     * @throws ParseException
     */
    public User getUser(String userName) throws ParseException {
        Cursor cursor =db.query("User",null,"userName = ?",new String[]{userName},null,null,null);
        if(cursor.moveToFirst()){
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
            user.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setSalt(cursor.getString(cursor.getColumnIndex("salt")));
            user.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(cursor.getColumnIndex("time"))));
            user.setVersion(cursor.getInt(cursor.getColumnIndex("version")));
            cursor.close();
            return user;
        }
        return null;
    }

    /**
     * 更新user对象
     * @param user
     * @return 影响行数
     */
    public int update(User user){
        ContentValues values = new ContentValues();
        values.put("userName",user.getUserName());
        values.put("password",user.getPassword());
        LogUtil.d("update", user.getPassword());
        values.put("salt",user.getSalt());
        values.put("version",user.getVersion());
        values.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getTime()));
        int flag=db.update("User", values, "id = ?", new String[]{user.getId() + ""});
        if(flag==0){
            return flag;
        }
        return flag;
    }

    /**
     * 开始事务
     */
    public void beginTran(){
        db.beginTransaction();
    }

    /**
     * 结束事务
     */
    public void endTran(){
        db.endTransaction();

    }

    /**
     * 提交事务
     */
    public void comitTran(){
        db.setTransactionSuccessful();
    }

    /**
     * 取得Database对象
     * @return
     */
    public SQLiteDatabase getDatebase(){
        return db;
    }

}
