package com.hanyao.passwordmanager.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hanyao.passwordmanager.Presenter.PasswordPresenter;
import com.hanyao.passwordmanager.bean.Password;

import com.hanyao.passwordmanager.R;

public class AddPasswordActivity extends BaseActivity {
    private Toolbar toolbar;
    private TextInputLayout loginSiteTIL;
    private TextInputLayout loginUserNameTIL;
    private TextInputLayout loginPasswordTIL;
    private TextInputLayout Question1TIL;
    private TextInputLayout Answer1TIL;
    private TextInputLayout Question2TIL;
    private TextInputLayout Answer2TIL;
    private TextInputLayout Question3TIL;
    private TextInputLayout Answer3TIL;
    private Button addButton;
    private Password password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        findView();
        setHint();//设置TextView默认显示内容
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(this.getString(R.string.app_name));
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(loginSiteTIL.getEditText().getText())){
                    Toast.makeText(AddPasswordActivity.this,"登录网站或应用不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(loginUserNameTIL.getEditText().getText())){
                    Toast.makeText(AddPasswordActivity.this,"登录名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(loginPasswordTIL.getEditText().getText())){
                    Toast.makeText(AddPasswordActivity.this,"登录密码",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    String loginSite = loginSiteTIL.getEditText().getText().toString();
                    String loginUserName =loginUserNameTIL.getEditText().getText().toString();
                    String loginPassword= loginPasswordTIL.getEditText().getText().toString();
                    String  Question1;
                    if(TextUtils.isEmpty(Question1TIL.getEditText().getText())){
                        Question1="未设置";
                    }else{
                         Question1= Question1TIL.getEditText().getText().toString();
                    }
                    String Answer1;
                    if(TextUtils.isEmpty(Answer1TIL.getEditText().getText())){
                        Answer1="未设置";
                    }else{
                        Answer1= Answer1TIL.getEditText().getText().toString();
                    }
                    String  Question2;
                    if(TextUtils.isEmpty(Question2TIL.getEditText().getText())){
                        Question2="未设置";
                    }else{
                        Question2= Question2TIL.getEditText().getText().toString();
                    }
                    String Answer2;
                    if(TextUtils.isEmpty(Answer2TIL.getEditText().getText())){
                        Answer2="未设置";
                    }else{
                        Answer2= Answer2TIL.getEditText().getText().toString();
                    }
                    String  Question3;
                    if(TextUtils.isEmpty(Question3TIL.getEditText().getText())){
                        Question3="未设置";
                    }else{
                        Question3= Question3TIL.getEditText().getText().toString();
                    }
                    String Answer3;
                    if(TextUtils.isEmpty(Answer3TIL.getEditText().getText())){
                        Answer3="未设置";
                    }else{
                        Answer3= Answer3TIL.getEditText().getText().toString();
                    }
                    PasswordPresenter passwordPresenter = new PasswordPresenter();
                    try {
                        //添加一条密码信息
                        password= passwordPresenter.addNewPassword(loginUserName,loginSite,loginPassword,Question1,Answer1,Question2,Answer2,Question3,Answer3);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddPasswordActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });
    }

    /**
     * 设置TextView默认显示内容
     */
    private void setHint(){
        loginSiteTIL.setHint(this.getString(R.string.login_site));
        loginUserNameTIL.setHint(this.getString(R.string.login_user_name));
        loginPasswordTIL.setHint(this.getString(R.string.login_password));
        Question1TIL.setHint(this.getString(R.string.question1));
        Answer1TIL.setHint(this.getString(R.string.answer1));
        Question2TIL.setHint(this.getString(R.string.question2));
        Answer2TIL.setHint(this.getString(R.string.answer2));
        Question3TIL.setHint(this.getString(R.string.question3));
        Answer3TIL.setHint(this.getString(R.string.answer3));
    }

    private void findView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loginSiteTIL=(TextInputLayout)findViewById(R.id.login_site_TIL);
         loginUserNameTIL=(TextInputLayout)findViewById(R.id.Login_user_name_TIL);
         loginPasswordTIL=(TextInputLayout)findViewById(R.id.login_password_TIL);
         Question1TIL=(TextInputLayout)findViewById(R.id.question1_TIL);
         Answer1TIL=(TextInputLayout)findViewById(R.id.answer1_TIL);
         Question2TIL=(TextInputLayout)findViewById(R.id.question2_TIL);
         Answer2TIL=(TextInputLayout)findViewById(R.id.answer2_TIL);
         Question3TIL=(TextInputLayout)findViewById(R.id.question3_TIL);
         Answer3TIL=(TextInputLayout)findViewById(R.id.answer3_TIL);
        addButton = (Button)findViewById(R.id.add_button);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(password!=null){
            Intent intent =new Intent(AddPasswordActivity.this,DisplayPasswordActivity.class);
            intent.putExtra("password",password);
            startActivity(intent);
        }
    }
    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_exit:
                    ActivityCollector.finishAll();
                    break;
            }
            return true;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_less, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
