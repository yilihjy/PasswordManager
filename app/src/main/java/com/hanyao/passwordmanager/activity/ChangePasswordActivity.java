package com.hanyao.passwordmanager.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.jungly.gridpasswordview.GridPasswordView;
import com.hanyao.passwordmanager.Presenter.UserPresenter;
import com.hanyao.passwordmanager.R;
import com.hanyao.passwordmanager.util.LogUtil;

public class ChangePasswordActivity extends BaseActivity {

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
        TextView textView = (TextView)findViewById(R.id.title_text);
        textView.setText(this.getString(R.string.change_root_password));
        registButton.setText(this.getString(R.string.change_root_password));
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password = passwordGridPasswordView.getPassWord();
                String repeatPassword = repeatPasswordGridPasswordView.getPassWord();
                if (password.length() < 8) {
                    Toast.makeText(ChangePasswordActivity.this, "根密码需设置为8位数字或字母", Toast.LENGTH_SHORT).show();
                    passwordGridPasswordView.clearPassword();
                    repeatPasswordGridPasswordView.clearPassword();
                } else if (!password.equals(repeatPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    passwordGridPasswordView.clearPassword();
                    repeatPasswordGridPasswordView.clearPassword();
                } else {
                    LogUtil.d("test", "user enter password is:" + password);
                    ChangeTask changeTask = new ChangeTask();
                    changeTask.execute(password);
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

    class ChangeTask extends AsyncTask<String,Intent,Boolean>{
        ProgressBar progressBar =(ProgressBar)findViewById(R.id.progress_bar);
        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                return  new UserPresenter().changePassword(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected  void onPostExecute(Boolean result){
            progressBar.setVisibility(View.GONE);
            if(result){
                Toast.makeText(ChangePasswordActivity.this,"根密码修改成功，请牢记",Toast.LENGTH_SHORT).show();
                MainActivity.instance.finish();
                Intent intent = new Intent(ChangePasswordActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(ChangePasswordActivity.this,"根密码修改失败",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
