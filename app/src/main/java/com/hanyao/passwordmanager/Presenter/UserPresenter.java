package com.hanyao.passwordmanager.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.hanyao.passwordmanager.bean.Password;
import com.hanyao.passwordmanager.database.PasswordDb;
import com.hanyao.passwordmanager.util.FileUtil;
import com.hanyao.passwordmanager.util.LogUtil;
import com.hanyao.passwordmanager.util.SHA256Util;
import com.hanyao.passwordmanager.MyApplication;
import com.hanyao.passwordmanager.bean.User;
import com.hanyao.passwordmanager.database.UserDb;
import com.hanyao.passwordmanager.util.AESUtil;
import com.hanyao.passwordmanager.util.RandomUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class UserPresenter {
    /**
     * 本地用户注册
     * @param password 主密码
     */
    public void localUserRegist(String password){
        User user = new User();
        user.setUserName("LocalUser");
        String salt = RandomUtil.getRandomString();
        String passwordSave = SHA256Util.Encrypt(password + salt);
        user.setPassword(passwordSave);
        user.setSalt(salt);
        user.setVersion(1);
        user.setTime(new Date(System.currentTimeMillis()));
        UserDb userDb = new UserDb();
        userDb.save(user);
        SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putString("userName","LocalUser");
        editor.commit();
        FileUtil.saveFile("userInfo", "LocalUser");
        MyApplication.areYouMyMaster(password);
    }

    /**
     * 检查权限
     * @param password
     * @return
     */
    public boolean checkPower(String password){
        UserDb userDb = new UserDb();
        User user =null;
        try {
            user = userDb.getUser(FileUtil.readFile("userInfo"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String passwordSave = user.getPassword();
        LogUtil.d("check", passwordSave);
        String salt = user.getSalt();
        if(passwordSave.equals(SHA256Util.Encrypt(password+salt))){
            MyApplication.areYouMyMaster(password);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 耗时方法。运行在线程里,修改根密码
     * @param password
     * @return
     * @throws Exception
     */
    public boolean changePassword(String password) throws Exception {
        UserDb userDb = new UserDb();
        userDb.beginTran();
        User user =null;
        user = userDb.getUser(FileUtil.readFile("userInfo"));
        String salt = RandomUtil.getRandomString();
        String passwordSave = SHA256Util.Encrypt(password+salt);
        user.setPassword(passwordSave);
        user.setSalt(salt);
        user.setVersion(user.getVersion()+1);
        user.setTime(new Date(System.currentTimeMillis()));
        int flag=userDb.update(user);
        if(flag==0){
            throw new Exception("change fail");
        }
        List<Password> passwordList = new PasswordDb(userDb.getDatebase()).get(true);
        modify(passwordList,userDb.getDatebase(),password);
        passwordList = new PasswordDb(userDb.getDatebase()).get(false);
        modify(passwordList, userDb.getDatebase(),password);
        MyApplication.areYouMyMaster(password);
        userDb.comitTran();
        userDb.endTran();
        return true;
    }

    private void modify(List<Password> passwordList,SQLiteDatabase db,String newPassword) throws Exception {
        PasswordDb passwordDb = new PasswordDb(db);
        if(passwordList.size()==0){
            return;
        }
        String servant=MyApplication.curse();
        for(Password pd:passwordList){
            pd.setLoginSite(AESUtil.encrypt(newPassword,AESUtil.decrypt(servant,pd.getLoginSite())));
            pd.setLoginName(AESUtil.encrypt(newPassword, AESUtil.decrypt(servant, pd.getLoginName())));
            pd.setLoginPassword(AESUtil.encrypt(newPassword, AESUtil.decrypt(servant, pd.getLoginPassword())));
            if(pd.getQuestion1()!=null){
                pd.setQuestion1(AESUtil.encrypt(newPassword,AESUtil.decrypt(servant,pd.getQuestion1())));
            }
            if(pd.getQuestion2()!=null){
                pd.setQuestion2(AESUtil.encrypt(newPassword, AESUtil.decrypt(servant, pd.getQuestion2())));
            }
            if(pd.getQuestion3()!=null){
                pd.setQuestion3(AESUtil.encrypt(newPassword, AESUtil.decrypt(servant, pd.getQuestion3())));
            }
            if(pd.getAnswer1()!=null){
                pd.setAnswer1(AESUtil.encrypt(newPassword, AESUtil.decrypt(servant, pd.getAnswer1())));
            }
            if(pd.getAnswer2()!=null){
                pd.setAnswer2(AESUtil.encrypt(newPassword, AESUtil.decrypt(servant, pd.getAnswer2())));
            }
            if(pd.getAnswer3()!=null){
                pd.setAnswer3(AESUtil.encrypt(newPassword, AESUtil.decrypt(servant, pd.getAnswer3())));
            }
            int f = passwordDb.update(pd);
            if(f==0){
                throw new Exception("change fail");
            }
        }

    }

}
