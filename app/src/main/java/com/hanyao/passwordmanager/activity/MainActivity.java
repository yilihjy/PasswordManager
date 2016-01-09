package com.hanyao.passwordmanager.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hanyao.passwordmanager.Adapter.PasswordListAdapter;
import com.hanyao.passwordmanager.Presenter.PasswordPresenter;
import com.hanyao.passwordmanager.R;
import com.hanyao.passwordmanager.bean.Password;
import com.hanyao.passwordmanager.bean.PasswordList;
import com.hanyao.passwordmanager.util.FileUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {
public static MainActivity instance=null;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private void findViews() {//添加控件
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
        findViews();
        toolbar.setTitle(this.getString(R.string.app_name));
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        SharedPreferences preferences =getSharedPreferences("data", MODE_PRIVATE);
        preferences.getString("userName","null");
        if(preferences.getString("userName","null").equals("null")){
            Intent intent = new Intent(MainActivity.this,LocalUserRegistActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this,CheckPowerActivity.class);
            startActivity(intent);
        }

        //菜单选项
        TextView change = (TextView)findViewById(R.id.change_password_item);
        TextView help = (TextView)findViewById(R.id.help_item);
        TextView about = (TextView)findViewById(R.id.about_item);
        TextView input =(TextView)findViewById(R.id.input_item);
        TextView output = (TextView)findViewById(R.id.output_item);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInformationActivity.actionShowInformationActivity(MainActivity.this, "<h1>正常密码保存模式</h1><p>普通模式将保存您自行设置的密码，适用于保存你已经拥有的账号和密码</p><h1>随机密码生成模式</h1><p>随机密码模式中你将不需要自己设置密码，应用会根据您的要求随机生成一个强密码。适合你新账号的密码设置和修改以前账号密码时的设置。注意，请保持本应用和您实际使用的密码的一致性。</p>");

            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInformationActivity.actionShowInformationActivity(MainActivity.this,"<h1>关于本应用</h1><p>本应用为应用开发者自用软件，旨在方便多帐号密码管理。</p><p>本应用不提供联网功能，开发者无法了解到您保存在本应用的一切信息。请牢记根密码，忘记跟密码您也会无法查看您保存在本应用上的一切信息</p><p>本应用不对使用者密码的安全性做出任何承诺</p><p>若使用者使用本应用产生损失，由使用者自行承担</p>");

            }
        });
        output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("特别提醒");
                dialog.setMessage("导出数据将以明文保存，请确认是否导出");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new OutputTask().execute();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,InputActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        TextView textView = (TextView)findViewById(R.id.no_password_tip);
        textView.setVisibility(View.GONE);
        ListView listView = (ListView)findViewById(R.id.main_list_view);
        final PasswordPresenter passwordPresenter = new PasswordPresenter();
        Map<String[],Password> map =  passwordPresenter.getPassword();
        if(map==null){
            textView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            return;
        }
        listView.setVisibility(View.VISIBLE);
        final List<PasswordList> passwordListList = new ArrayList<>();
        for(Map.Entry<String[],Password> entry:map.entrySet()){
            PasswordList passwordList = new PasswordList(entry.getKey()[0],entry.getKey()[1]);
            passwordList.setPasswordString(entry.getKey()[2]);
            passwordList.setPassword(entry.getValue());
            passwordListList.add(passwordList);
        }
        Collections.sort(passwordListList);
        PasswordListAdapter passwordListAdapter = new PasswordListAdapter(MainActivity.this,R.layout.password_list_layout,passwordListList);
        listView.setAdapter(passwordListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PasswordList passwordList = passwordListList.get(position);
                Intent intent = new Intent(MainActivity.this, DisplayPasswordActivity.class);
                intent.putExtra("password", passwordList.getPassword());
                passwordPresenter.addSee(passwordList.getPassword());
                startActivity(intent);
            }
        });
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add:{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("请选择模式");
                    dialog.setItems(new String[]{"正常密码保存模式", "随机密码生成模式"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0: {
                                    startActivity(new Intent(MainActivity.this, AddPasswordActivity.class));
                                }
                                break;
                                case 1: {
                                    startActivity(new Intent(MainActivity.this, AutoPasswordActivity.class));
                                }
                                break;
                            }
                        }
                    });
                    dialog.show();
                    }
                    break;
                case R.id.action_exit:
                    ActivityCollector.finishAll();
                    break;
            }
            return true;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    class OutputTask extends AsyncTask<Void,Intent,Boolean> {

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return  new PasswordPresenter().outputPasswords();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected  void onPostExecute(Boolean result){
            if(result){
                if (android.os.Build.VERSION.SDK_INT ==  19){
                    Toast.makeText(MainActivity.this, "密码信息导出成功\n保存路径："+getApplicationContext().getFilesDir().getAbsolutePath()+"/passwords.xml", Toast.LENGTH_LONG).show();
                }else{
                    String path = Environment.getExternalStorageDirectory().getPath();
                    Toast.makeText(MainActivity.this, "密码信息导出成功\n保存路径："+path+"/passwords.xml", Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(MainActivity.this,"密码信息导出失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
