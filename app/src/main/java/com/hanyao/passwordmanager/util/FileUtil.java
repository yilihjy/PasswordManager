package com.hanyao.passwordmanager.util;

import android.content.Context;
import com.hanyao.passwordmanager.MyApplication;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class FileUtil {
    public static void saveFile(String fileName,String content){
        FileOutputStream out;
        BufferedWriter writer = null;
        try{
            out = MyApplication.getContext().openFileOutput(fileName,Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(content);
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void saveFile(String path,String fileName,String content){
        FileOutputStream out;
        BufferedWriter writer = null;
        try{
            out = new FileOutputStream(path+"/"+fileName);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(content);
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static String readFileByPath(String path){
        FileInputStream in;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = new FileInputStream(path);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine())!=null){
                content.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }


    public static String readFile(String fileName){
        FileInputStream in;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = MyApplication.getContext().openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine())!=null){
                content.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
