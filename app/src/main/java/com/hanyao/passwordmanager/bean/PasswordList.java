package com.hanyao.passwordmanager.bean;

/**
 * Created by HanYao-Huang on 2016/1/4.
 */
public class PasswordList implements Comparable<PasswordList>{
    private String site;
    private  String loginName;
    private String passwordString;
    private Password password;
    public PasswordList(String site,String loginName){
        this.site=site;
        this.loginName=loginName;
    }
    public void setSite(String site){
        this.site=site;
    }
    public String getSite(){
        return site;
    }
    public void setLoginName(String loginName){
        this.loginName=loginName;
    }
    public  String getLoginName(){
        return loginName;
    }
    public void setPassword(Password password){
        this.password=password;
    }
    public Password getPassword(){
        return password;
    }
    public void setPasswordString(String passwordString){
        this.passwordString=passwordString;
    }
    public String getPasswordString(){
        return passwordString;
    }

    @Override
    public int compareTo(PasswordList another) {
        return this.password.compareTo(another.password);
    }
}
