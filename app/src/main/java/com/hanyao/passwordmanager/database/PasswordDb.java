package com.hanyao.passwordmanager.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.hanyao.passwordmanager.MyApplication;
import com.hanyao.passwordmanager.bean.Password;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class PasswordDb {
    private SQLiteDatabase db ;
    public PasswordDb(){
        db = new MyDatabaseHelper(MyApplication.getContext(),"PasswordManager.db",null,1).getWritableDatabase();
    }
    public PasswordDb( SQLiteDatabase db){
        this.db=db;
    }
    /**
     * 保存password对象，在数据库执行插入操作
     * @param password 一个等待保存的Password对象
     */
    public int save(Password password){
        ContentValues values = new ContentValues();
        values.put("loginSite", password.getLoginSite());
        values.put("loginName", password.getLoginName());
        values.put("loginPassword",password.getLoginPassword());
        values.put("effective",password.getEffective());
        values.put("version",password.getVersion());
        values.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(password.getTime()));
        values.put("question1",password.getQuestion1());
        values.put("answer1",password.getAnswer1());
        values.put("question2",password.getQuestion2());
        values.put("answer2",password.getAnswer2());
        values.put("question3",password.getQuestion3());
        values.put("answer3",password.getAnswer3());
        values.put("see",password.getSee());
        int id= (int)db.insert("Password", null, values);
        if (id!=-1){
            return id;
        }
        return -1;

    }

    /**
     * 根据密码是否有效查找password对象
     * @param effective 密码有效性 true表示有效密码 false表示无效密码
     * @return 一个List
     * @throws ParseException
     */
    public List<Password> get(Boolean effective) throws ParseException {
        if(effective){
            Cursor cursor =db.query("Password", null, "effective = ?", new String[]{"1"}, null, null, null);
            List<Password> passwordList = new ArrayList<>();
            if(cursor.moveToFirst()){
                do{
                    Password password = new Password();
                    password.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    password.setLoginSite(cursor.getString(cursor.getColumnIndex("loginSite")));
                    password.setLoginName(cursor.getString(cursor.getColumnIndex("loginName")));
                    password.setLoginPassword(cursor.getString(cursor.getColumnIndex("loginPassword")));
                    password.setEffective(cursor.getInt(cursor.getColumnIndex("effective")));
                    password.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(cursor.getColumnIndex("time"))));
                    password.setVersion(cursor.getInt(cursor.getColumnIndex("version")));
                    password.setQuestion1(cursor.getString(cursor.getColumnIndex("question1")));
                    password.setAnswer1(cursor.getString(cursor.getColumnIndex("answer1")));
                    password.setQuestion2(cursor.getString(cursor.getColumnIndex("question2")));
                    password.setAnswer2(cursor.getString(cursor.getColumnIndex("answer2")));
                    password.setQuestion3(cursor.getString(cursor.getColumnIndex("question3")));
                    password.setAnswer3(cursor.getString(cursor.getColumnIndex("answer3")));
                    password.setSee(cursor.getInt(cursor.getColumnIndex("see")));
                    passwordList.add(password);
                }while (cursor.moveToNext());
            }
            return passwordList;
        }else{
            Cursor cursor =db.query("Password", null, "effective = ?", new String[]{"0"}, null, null, null);
            List<Password> passwordList = new ArrayList<>();
            if(cursor.moveToFirst()){
                do{
                    Password password = new Password();
                    password.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    password.setLoginSite(cursor.getString(cursor.getColumnIndex("loginSite")));
                    password.setLoginName(cursor.getString(cursor.getColumnIndex("loginName")));
                    password.setLoginPassword(cursor.getString(cursor.getColumnIndex("loginPassword")));
                    password.setEffective(cursor.getInt(cursor.getColumnIndex("effective")));
                    password.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(cursor.getColumnIndex("time"))));
                    password.setVersion(cursor.getInt(cursor.getColumnIndex("version")));
                    password.setQuestion1(cursor.getString(cursor.getColumnIndex("question1")));
                    password.setAnswer1(cursor.getString(cursor.getColumnIndex("answer1")));
                    password.setQuestion2(cursor.getString(cursor.getColumnIndex("question2")));
                    password.setAnswer2(cursor.getString(cursor.getColumnIndex("answer2")));
                    password.setQuestion3(cursor.getString(cursor.getColumnIndex("question3")));
                    password.setAnswer3(cursor.getString(cursor.getColumnIndex("answer3")));
                    password.setSee(cursor.getInt(cursor.getColumnIndex("see")));
                    passwordList.add(password);
                }while (cursor.moveToNext());
            }
            return passwordList;
        }
    }

    /**
     * 更新password对象
     * @param password 注意，对象id属性须存在
     * @return 影响行数
     */
    public int update(Password password){
        ContentValues values = new ContentValues();
        values.put("loginSite", password.getLoginSite());
        values.put("loginName", password.getLoginName());
        values.put("loginPassword",password.getLoginPassword());
        values.put("effective",password.getEffective());
        values.put("version",password.getVersion());
        values.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(password.getTime()));
        values.put("question1",password.getQuestion1());
        values.put("answer1",password.getAnswer1());
        values.put("question2",password.getQuestion2());
        values.put("answer2", password.getAnswer2());
        values.put("question3", password.getQuestion3());
        values.put("answer3",password.getAnswer3());
        values.put("see",password.getSee());
        int flag=db.update("Password", values, "id = ?", new String[]{password.getId() + ""});
        return flag;
    }

    /**
     * 根据id查找password对象
     * @param id
     * @return
     * @throws ParseException
     */
    public Password getById(int id) throws ParseException {
        Cursor cursor =db.query("Password", null, "id = ?", new String[]{id+""}, null, null, null);
        if(cursor.moveToFirst()){
        Password password = new Password();
        password.setId(cursor.getInt(cursor.getColumnIndex("id")));
        password.setLoginSite(cursor.getString(cursor.getColumnIndex("loginSite")));
        password.setLoginName(cursor.getString(cursor.getColumnIndex("loginName")));
        password.setLoginPassword(cursor.getString(cursor.getColumnIndex("loginPassword")));
        password.setEffective(cursor.getInt(cursor.getColumnIndex("effective")));
        password.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(cursor.getString(cursor.getColumnIndex("time"))));
        password.setVersion(cursor.getInt(cursor.getColumnIndex("version")));
        password.setQuestion1(cursor.getString(cursor.getColumnIndex("question1")));
        password.setAnswer1(cursor.getString(cursor.getColumnIndex("answer1")));
        password.setQuestion2(cursor.getString(cursor.getColumnIndex("question2")));
        password.setAnswer2(cursor.getString(cursor.getColumnIndex("answer2")));
        password.setQuestion3(cursor.getString(cursor.getColumnIndex("question3")));
            password.setSee(cursor.getInt(cursor.getColumnIndex("see")));
        password.setAnswer3(cursor.getString(cursor.getColumnIndex("answer3")));return password;}else{
            return null;
        }
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

}
