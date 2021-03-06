package com.hanyao.passwordmanager.Presenter;

import android.os.Environment;

import com.hanyao.passwordmanager.bean.Password;
import com.hanyao.passwordmanager.database.PasswordDb;
import com.hanyao.passwordmanager.util.FileUtil;
import com.hanyao.passwordmanager.util.LogUtil;
import com.hanyao.passwordmanager.util.SHA256Util;
import com.hanyao.passwordmanager.MyApplication;
import com.hanyao.passwordmanager.util.AESUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class PasswordPresenter {
    private PasswordDb passwordDb = new PasswordDb();
    /**
     * 添加密码信息
     * @param loginName 登录名
     * @param loginSite 登录站点或应用
     * @param loginPassword 登录密码
     * @param question1 密保问题1
     * @param answer1 回答1
     * @param question2 密保问题2
     * @param answer2 回答2
     * @param question3 密保问题3
     * @param answer3 回答3
     * @return 刚刚添加的passw对象
     * @throws Exception
     */
    public Password addNewPassword(String loginName,String loginSite,String loginPassword,String question1,String answer1,String question2,String answer2,String question3,String answer3) throws Exception {
        Password password = new Password();
        String servant = MyApplication.curse();
        password.setLoginSite(AESUtil.encrypt(servant, loginSite));
        password.setLoginName(AESUtil.encrypt(servant, loginName));
        password.setLoginPassword(AESUtil.encrypt(servant,loginPassword));
        if(question1!=null){
            password.setQuestion1(AESUtil.encrypt(servant,question1));
        }
        if(question2!=null){
            password.setQuestion2(AESUtil.encrypt(servant, question2));
        }
        if(question3!=null){
            password.setQuestion3(AESUtil.encrypt(servant, question3));
        }
        if(answer1!=null){
            password.setAnswer1((AESUtil.encrypt(servant, answer1)));
        }
        if(answer2!=null){
            password.setAnswer2((AESUtil.encrypt(servant, answer2)));
        }
        if(answer3!=null){
            password.setAnswer3((AESUtil.encrypt(servant, answer3)));
        }
        password.setVersion(1);
        password.setSee(0);
        password.setTime(new Date(System.currentTimeMillis()));
        password.setEffective(1);
       // PasswordDb passwordDb = new PasswordDb();
        int id=passwordDb.save(password);
        if(id!=-1){
            return passwordDb.getById(id);
        }else{
            return null;
        }
    }

    /**
     * 修改密码信息
     * @param oldPassword 需要修改的password对象，要求id值存在
     * @param loginSite 登录站点或应用
     * @param loginName 登录名
     * @param loginPassword 登录密码
     * @param question1 密保问题1
     * @param answer1 回答1
     * @param question2 密保问题2
     * @param answer2 回答2
     * @param question3 密保问题3
     * @param answer3 回答3
     * @return 修改过的password对象
     * @throws Exception
     */
    public Password modifyPassword(Password oldPassword,String loginSite,String loginName,String loginPassword,String question1,String answer1,String question2,String answer2,String question3,String answer3) throws Exception {
        String servant = MyApplication.curse();
        oldPassword.setLoginSite(AESUtil.encrypt(servant, loginSite));
        oldPassword.setLoginName(AESUtil.encrypt(servant, loginName));
        oldPassword.setLoginPassword(AESUtil.encrypt(servant,loginPassword));
        if(question1!=null){
            oldPassword.setQuestion1(AESUtil.encrypt(servant, question1));
        }
        if(question2!=null){
            oldPassword.setQuestion2(AESUtil.encrypt(servant, question2));
        }
        if(question3!=null){
            oldPassword.setQuestion3(AESUtil.encrypt(servant, question3));
        }
        if(answer1!=null){
            oldPassword.setAnswer1((AESUtil.encrypt(servant, answer1)));
        }
        if(answer2!=null){
            oldPassword.setAnswer2((AESUtil.encrypt(servant, answer2)));
        }
        if(answer3!=null){
            oldPassword.setAnswer3((AESUtil.encrypt(servant,answer3)));
        }
        oldPassword.setVersion(oldPassword.getVersion() + 1);
        oldPassword.setTime(new Date(System.currentTimeMillis()));
        //PasswordDb passwordDb = new PasswordDb();
        int flag=passwordDb.update(oldPassword);
        if(flag==0){
            return null;
        }
        return passwordDb.getById(oldPassword.getId());
    }
    public boolean deletePassword(Password password){
        password.setEffective(0);
        //PasswordDb passwordDb = new PasswordDb();
        int flag=passwordDb.update(password);
        if(flag==0){
            return false;
        }
        return true;
    }

    /**
     * 获取有效密码信息集合
     * @return key为String数组，0位置为登录站点，1位置为登录名，2位置为登录密码；value为password对象
     */
    public Map<String[],Password> getPassword(){
        //PasswordDb passwordDb = new PasswordDb();
        List<Password> passwordList =null;
        try {
            passwordList=passwordDb.get(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(passwordList.size()==0){
            return null;
        }
        Map<String[],Password> map = new HashMap<>();
        for (Password password:passwordList){
            String servant = MyApplication.curse();
            try {
                String site = AESUtil.decrypt(servant,password.getLoginSite());
                String loginName = AESUtil.decrypt(servant,password.getLoginName());
                String passwordString = AESUtil.decrypt(servant, password.getLoginPassword());
                map.put(new String[]{site,loginName,passwordString},password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 获取有效密码信息集合
     * @return key为String数组，0位置为登录站点，1位置为登录名；value为password对象
     */
    public Map<String[],Password> seeDeletedPassword(){
        //PasswordDb passwordDb = new PasswordDb();
        List<Password> passwordList =null;
        try {
            passwordList=passwordDb.get(false);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(passwordList.size()==0){
            return null;
        }
        Map<String[],Password> map = new HashMap<>();
        for (Password password:passwordList){
            String servant = MyApplication.curse();
            try {
                String site = AESUtil.decrypt(servant,password.getLoginSite());
                String loginName = AESUtil.decrypt(servant,password.getLoginName());
                map.put(new String[]{site,loginName},password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 创建一个新密码
     * @param number 密码位数,应当小于等于20大于等于6
     * @param upperOrLower 区分大小写，1区分大小写，0不区分大小写
     * @param first 首字符要求，1首字符为英文，2首字符为数字，0首字符无要求
     * @param underline 是否支持下划线
     * @param seed 密码种子（用户根密码）
     * @return
     */
    public String createPassword(int number,int upperOrLower ,int first,boolean underline,String seed){
        String returnString = null;
        StringBuffer stringBuffer = new StringBuffer();
        int n = 0;
        if(first==0) {
            n = number+1;
        }else if(first==1) {
            n=number;
            stringBuffer.append((char)((int)(Math.random()*26+'A')));
        }else{
            n=number;
            stringBuffer.append((int)(Math.random()*9));
        }
        String temp = SHA256Util.Encrypt(seed + System.currentTimeMillis());
        int intTemp = (int)(Math.random()*(temp.length()-number-2));
        stringBuffer.append(temp.substring(intTemp,n-1+intTemp));
        if(underline){
            intTemp = (int)(Math.random()*(number-2)+1);
            stringBuffer.setCharAt(intTemp,'_');
        }
        if(upperOrLower==0){
            returnString=stringBuffer.toString().toLowerCase();
        }else{
            String s = stringBuffer.toString();
            stringBuffer = new StringBuffer();
            for(int i=0;i<s.length();i++){
                int r= (int)Math.random()*100;
                if(i % 2 != 0){
                stringBuffer.append(new String(new char[]{s.charAt(i)}).toLowerCase());

                }
                else{
                    stringBuffer.append(new String(new char[]{s.charAt(i)}).toUpperCase());
                }
            }
            returnString =stringBuffer.toString();
        }
        return returnString;
    }

    /**
     * 对password对象解密
     * @param password 需要解密的password对象
     * @return 一个数组，[0]登录站点，[1]登录名，[2]登录密码，[3]密保问题1,[4]回答1,[5]密保问题2,[6]回答2,[7]密保问题3,[8]回答3
     * @throws Exception
     */
    public String[] displayPassword(Password password) throws Exception {
        String loginSite = AESUtil.decrypt(MyApplication.curse(),password.getLoginSite());
        String loginUserName=AESUtil.decrypt(MyApplication.curse(),password.getLoginName());
        String loginPassword=AESUtil.decrypt(MyApplication.curse(),password.getLoginPassword());
        String question1=AESUtil.decrypt(MyApplication.curse(),password.getQuestion1());
        String answer1=AESUtil.decrypt(MyApplication.curse(),password.getAnswer1());
        String question2=AESUtil.decrypt(MyApplication.curse(), password.getQuestion2());
        String answer2=AESUtil.decrypt(MyApplication.curse(),password.getAnswer2());
        String question3=AESUtil.decrypt(MyApplication.curse(),password.getQuestion3());
        String answer3=AESUtil.decrypt(MyApplication.curse(),password.getAnswer3());
        return new String[]{loginSite,loginUserName,loginPassword,question1,answer1,question2,answer2,question3,answer3};
    }

    /**
     * 增加密码信息热度
     * @param password
     */
    public void addSee(Password password){
        password.setSee(password.getSee()+1);
       // PasswordDb passwordDb = new PasswordDb();
        passwordDb.update(password);
    }

    /**
     *导出数据
     * @return
     */
    public  boolean outputPasswords() throws Exception {
        Map<String[],Password> maps = getPassword();
        String output = "<Passwords version=\"1.0\">";
        for (Map.Entry<String[],Password> map:maps.entrySet()) {
            output=output+"<Password>";
            output=output+"<Site>";
            output=output+AESUtil.decrypt(MyApplication.curse(),map.getValue().getLoginSite());
            output=output+"</Site>";
            output=output+"<LoginName>";
            output=output+AESUtil.decrypt(MyApplication.curse(),map.getValue().getLoginName());
            output=output+"</LoginName>";
            output=output+"<LoginPassword>";
            output=output+AESUtil.decrypt(MyApplication.curse(),map.getValue().getLoginPassword());
            output=output+"</LoginPassword>";
            output=output+"<Question1>";
            output=output+AESUtil.decrypt(MyApplication.curse(),map.getValue().getQuestion1());
            output=output+"</Question1>";
            output=output+"<Answer1>";
            output=output+AESUtil.decrypt(MyApplication.curse(),map.getValue().getAnswer1());
            output=output+"</Answer1>";
            output=output+"<Question2>";
            output=output+AESUtil.decrypt(MyApplication.curse(),map.getValue().getQuestion2());
            output=output+"</Question2>";
            output=output+"<Answer2>";
            output=output+AESUtil.decrypt(MyApplication.curse(),map.getValue().getAnswer2());
            output=output+"</Answer2>";
            output=output+"<Question3>";
            output=output+AESUtil.decrypt(MyApplication.curse(),map.getValue().getQuestion3());
            output=output+"</Question3>";
            output=output+"<Answer3>";
            output=output+AESUtil.decrypt(MyApplication.curse(),map.getValue().getAnswer3());
            output=output+"</Answer3>";
            output=output+"</Password>";
        }
        output = output+"</Passwords>";
        if (android.os.Build.VERSION.SDK_INT ==  19){
            FileUtil.saveFile("passwords.xml", output);
            return true;
        }else{
            String path=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath()).getParent();

            FileUtil.saveFile(path,"passwords.xml", output);
            return true;
        }

    }

    /**
     *导入数据
     * @param type 0覆盖导入，1插入导入
     * @return
     */
    public boolean inputPassword(int type) throws Exception {
        String path=Environment.getExternalStorageDirectory().getPath()+"/passwords.xml";
        File file = new File(path);
        if(file.exists()){
            passwordDb.beginTran();
            switch (type){
                case 0:{
                    Map<String[],Password> maps = getPassword();
                    for (Map.Entry<String[],Password> map:maps.entrySet()){
                        deletePassword(map.getValue());
                    }
                }
                case 1:{
                    XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
                    XmlPullParser xmlPullParser = factory.newPullParser();
                    xmlPullParser.setInput(new StringReader(FileUtil.readFileByPath(path)));
                    int eventType=xmlPullParser.getEventType();
                    String site="";
                    String loginName="";
                    String loginPassword="";
                    String question1="";
                    String answer1="";
                    String question2="";
                    String answer2="";
                    String question3="";
                    String answer3="";
                    while (eventType!=XmlPullParser.END_DOCUMENT){
                        String nodeName =xmlPullParser.getName();
                        switch (eventType){
                            case XmlPullParser.START_TAG:{
                                if("Site".equals(nodeName)){
                                    site=xmlPullParser.nextText();
                                }else if("LoginName".equals(nodeName)){
                                    loginName=xmlPullParser.nextText();
                                }else  if ("LoginPassword".equals(nodeName)){
                                    loginPassword=xmlPullParser.nextText();
                                }else if("Question1".equals(nodeName)){
                                    question1=xmlPullParser.nextText();
                                }else if("Answer1".equals(nodeName)){
                                    answer1=xmlPullParser.nextText();
                                }else if("Question2".equals(nodeName)){
                                    question2=xmlPullParser.nextText();
                                }else if("Answer2".equals(nodeName)){
                                    answer2=xmlPullParser.nextText();
                                }else if("Question3".equals(nodeName)){
                                    question3=xmlPullParser.nextText();
                                }else if("Answer3".equals(nodeName)){
                                    answer3=xmlPullParser.nextText();
                                }break;
                            }
                            case XmlPullParser.END_TAG:{
                                if("Password".equals(nodeName)){
                                    addNewPassword(loginName, site, loginPassword, question1, answer1, question2, answer2, question3, answer3);
                                }break;
                            }default:break;
                        }eventType=xmlPullParser.next();
                    }
                }
                    break;
            }
            passwordDb.comitTran();
            passwordDb.endTran();
            return true;
        }else {
            return false;
        }
    }

}
