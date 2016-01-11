package com.hanyao.passwordmanager.activity;

import android.content.DialogInterface;
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
                if (isChecked) {
                    passwordGridPasswordView.setPasswordVisibility(true);
                    repeatPasswordGridPasswordView.setPasswordVisibility(true);
                } else {
                    passwordGridPasswordView.setPasswordVisibility(false);
                    repeatPasswordGridPasswordView.setPasswordVisibility(false);
                }
            }
        });
        TextView to =(TextView)findViewById(R.id.to_agreement);
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowInformationActivity.actionShowInformationActivity(LocalUserRegistActivity.this, "<h1>使用协议</h1>\n" +
                        "<p>一切用户在下载并使用本应用时均被视为已经仔细阅读本条款并完全同意。凡以任何方式登陆本应用，或直接、间接使用本应用资料者，均被视为自愿接受本应用相关声明和使用协议的约束。</p>\n" +
                        "<p>使用者须遵守国家法律、法规</p>\n" +
                        "<p>本应用为应用开发者自用软件，现无偿提供给使用者使用。</p>\n" +
                        "<p>本应用不提供连接互联网功能，也没有申请联网权限。</p>\n" +
                        "<p>本应用对用户的密码信息安全不做任何承诺。</p>\n" +
                        "<p>使用者明确并同意其使用本应用所存在的包括密码信息泄露在内的一切风险将完全由其使用者本人承担。</p>\n" +
                        "<p>使用者使用本应用产生的一起后果由使用者本人承担。</p>\n" +
                        "<p>应用开发者不承担使用本应用产生的一切责任。</p>\n" +
                        "<p>使用者不同意本协议的，须停止使用本应用并卸载本应用。<p/>\n" +
                        "<p>本声明未涉及的问题请参见国家有关法律法规，当本声明与国家有关法律法规冲突时，以国家法律法规为准。</p>");

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
