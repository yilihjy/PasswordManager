package com.hanyao.passwordmanager.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hanyao.passwordmanager.Presenter.UserPresenter;
import com.jungly.gridpasswordview.GridPasswordView;
import com.hanyao.passwordmanager.MyApplication;

import com.hanyao.passwordmanager.R;

public class CheckPowerActivity extends BaseActivity {
    private GridPasswordView checkPassword;
    private Button checkButton;
    private TextView tip;
    private Switch aSwitch;
    private int count=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_power);
        findView();
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tip.setVisibility(View.GONE);
                String password = checkPassword.getPassWord();
                SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
                long lastError = preferences.getLong("LastError", 0L);
                long time = System.currentTimeMillis() - lastError;
                if (password.length() < 8) {
                    Toast.makeText(CheckPowerActivity.this, "密码为8为数字或字母", Toast.LENGTH_SHORT).show();
                    checkPassword.clearPassword();
                } else if (time < 300000L) {
                    int s = 5*60-(int) time / 1000;
                    Toast.makeText(CheckPowerActivity.this, "请等待" + s + "秒后再尝试", Toast.LENGTH_SHORT).show();
                    checkPassword.clearPassword();
                    return;
                } else {
                    UserPresenter userPresenter = new UserPresenter();
                    boolean flag;
                    flag = userPresenter.checkPower(password);
                    if (flag) {
                        finish();
                    } else {
                        tip.setVisibility(View.VISIBLE);
                        if (count == 0) {
                            tip.setText("输错密码次数太多了，请5分钟后再试");
                            SharedPreferences.Editor editor = MyApplication.getContext().getSharedPreferences("data", MODE_PRIVATE).edit();
                            editor.putLong("LastError", System.currentTimeMillis());
                            editor.commit();
                            checkPassword.clearPassword();
                        } else {
                            tip.setText("密码错误,你还可以尝试"+count+"次");
                            count--;
                            checkPassword.clearPassword();
                        }
                    }
                }
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkPassword.setPasswordVisibility(true);
                }else{
                    checkPassword.setPasswordVisibility(false);
                }
            }
        });
    }

    private void  findView(){
        checkPassword = (GridPasswordView)findViewById(R.id.password_check);
        checkButton = (Button)findViewById(R.id.check_button);
        tip = (TextView)findViewById(R.id.check_tip);
        aSwitch = (Switch)findViewById(R.id.switch_password_displaying_check);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(CheckPowerActivity.this);
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
