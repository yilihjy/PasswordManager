package com.hanyao.passwordmanager.bean;



import java.util.Date;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class User {
    private int id;
    private String userName;
    private String password;
    private String salt;
    private int version;
    private Date time;

    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return password;
    }
    public void setSalt(String salt){
        this.salt=salt;
    }
    public String getSalt(){
        return salt;
    }
    public void setVersion(int version){
        this.version=version;
    }
    public int getVersion(){
        return version;
    }
    public void setTime(Date time){
        this.time=time;
    }
    public Date getTime(){return time;}
}
