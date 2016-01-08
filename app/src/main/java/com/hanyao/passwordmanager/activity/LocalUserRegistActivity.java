package com.hanyao.passwordmanager.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.hanyao.passwordmanager.Presenter.UserPresenter;
import com.hanyao.passwordmanager.util.LogUtil;
import com.jungly.gridpasswordview.GridPasswordView;

import com.hanyao.passwordmanager.R;

public class LocalUserRegistActivity extends BaseActivity {
    private GridPasswordView passwordGridPasswordView;
    private GridPasswordView repeatPasswordGridPasswordView;
    private Button registButton;
    private Switch passwordDisplaySwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_user_regist);
        findView();
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordGridPasswordView.getPassWord();
                String repeatPassword = repeatPasswordGridPasswordView.getPassWord();
                if (password.length() < 8) {
                    Toast.makeText(LocalUserRegistActivity.this, "根密码需设置为8位数字或字母", Toast.LENGTH_SHORT).show();
                    passwordGridPasswordView.clearPassword();
                    repeatPasswordGridPasswordView.clearPassword();
                } else if (!password.equals(repeatPassword)) {
                    Toast.makeText(LocalUserRegistActivity.this, "两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    passwordGridPasswordView.clearPassword();
                    repeatPasswordGridPasswordView.clearPassword();
                } else {
                    LogUtil.d("test", "user enter password is:" + password);
                    new UserPresenter().localUserRegist(password);
                    finish();
                }
            }
        });
        passwordDisplaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passwordGridPasswordView.setPasswordVisibility(true);
                    repeatPasswordGridPasswordView.setPasswordVisibility(true);
                }else{
                    passwordGridPasswordView.setPasswordVisibility(false);
                    repeatPasswordGridPasswordView.setPasswordVisibility(false);
                }
            }
        });
    }


    private  void findView(){
        passwordGridPasswordView = (GridPasswordView)findViewById(R.id.password);
        repeatPasswordGridPasswordView = (GridPasswordView)findViewById(R.id.repeat_password);
        registButton = (Button)findViewById(R.id.regist_button);
        passwordDisplaySwitch = (Switch)findViewById(R.id.switch_password_displaying);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(LocalUserRegistActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("是否退出应用？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCollector.finishAll();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        }
        return false;
    }
}
