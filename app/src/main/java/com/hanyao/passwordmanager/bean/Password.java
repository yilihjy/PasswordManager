package com.hanyao.passwordmanager.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class Password implements Serializable{
    private int id;
    private String loginSite;
    private String loginName;
    private String loginPassword;
    private int effective;
    private int version;
    private Date time;
    private String question1;
    private String answer1;
    private String question2;
    private String answer2;
    private String question3;
    private String answer3;

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }
    public void setLoginSite(String loginSite){
        this.loginSite=loginSite;
    }

    public String getLoginSite(){
        return loginSite;
    }

    public void setLoginName(String loginName){
        this.loginName=loginName;
    }

    public String getLoginName(){
        return loginName;
    }

    public void setLoginPassword(String loginPassword){
        this.loginPassword=loginPassword;
    }

    public String getLoginPassword(){
        return loginPassword;
    }

    public void setEffective(int effective){
        this.effective=effective;
    }

    public int getEffective(){
        return effective;
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
    public Date getTime(){
        return time;
    }

    public void setQuestion1(String question1){
        this.question1=question1;
    }
    public void setQuestion2(String question2){
        this.question2=question2;
    }
    public void setQuestion3(String question3){
        this.question3=question3;
    }

    public String getQuestion1(){
        return question1;
    }
    public String getQuestion2(){
        return question2;
    }
    public String getQuestion3(){
        return question3;
    }

    public void setAnswer1(String answer1){
        this.answer1=answer1;
    }
    public void setAnswer2(String answer2){
        this.answer2=answer2;
    }
    public void setAnswer3(String answer3){
        this.answer3=answer3;
    }

    public String getAnswer1(){
        return answer1;
    }
    public String getAnswer2(){
        return answer2;
    }
    public String getAnswer3(){
        return answer3;
    }


}
