package com.hanyao.passwordmanager.util;

import java.security.SecureRandom;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class RandomUtil {

    /**
     *
     * @return 返回一个随机字符串
     */
    public static String getRandomString(){
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        String returnString="";
        for(int i = 0;i<bytes.length;i++){
            int temp = bytes[i];
            returnString +=temp;
        }
        return  returnString;
    }
}
