package com.hanyao.passwordmanager.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hanyao.passwordmanager.bean.Password;
import com.hanyao.passwordmanager.Presenter.PasswordPresenter;
import com.hanyao.passwordmanager.R;

public class DisplayPasswordActivity extends AppCompatActivity {
    public static DisplayPasswordActivity instance= null;
    private Toolbar toolbar;
    private Password password;
    private String stringPassword;
    private TextView site ;
    private TextView name ;
    private TextView passwordString;
    private TextView  question1;
    private TextView  answer1;
    private TextView question2 ;
    private TextView  answer2;
    private TextView question3 ;
    private TextView  answer3;
    private TextView copyButton ;
    private TextView modifyButton ;
    private  TextView deleteButton ;
    private TextView showQuestion;

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        site = (TextView)findViewById(R.id.site_text_view);
        name = (TextView)findViewById(R.id.name_text_view);
        passwordString = (TextView)findViewById(R.id.password_text_view);
        question1= (TextView)findViewById(R.id.qusetion1_text_view);
        answer1= (TextView)findViewById(R.id.answer1_text_view);
        question2 = (TextView)findViewById(R.id.qusetion2_text_view);
        answer2= (TextView)findViewById(R.id.answer2_text_view);
        question3 = (TextView)findViewById(R.id.qusetion3_text_view);
        answer3= (TextView)findViewById(R.id.answer3_text_view);
        copyButton = (TextView)findViewById(R.id.copy_button);
        modifyButton = (TextView)findViewById(R.id.modify_button_in_display);
        deleteButton = (TextView)findViewById(R.id.delete_button);
        showQuestion = (TextView)findViewById(R.id.show_question);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_password);
        instance=this;
        findViews();
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("密码管家");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        password = (Password)getIntent().getSerializableExtra("password");
        PasswordPresenter passwordPresenter = new PasswordPresenter();
        try {
            String[] display = passwordPresenter.displayPassword(password);
            site.setText(display[0]);
            name.setText(display[1]);
            passwordString.setText(display[2]);
            stringPassword=display[2];
            question1.setText(display[3]);
            answer1.setText( display[4]);
            question2.setText(display[5]);
            answer2.setText( display[6]);
            question3.setText( display[7]);
            answer3.setText( display[8]);
            copyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("password", stringPassword);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(DisplayPasswordActivity.this, "密码已复制到剪贴板", Toast.LENGTH_SHORT).show();
                }
            });
            modifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(DisplayPasswordActivity.this);
                    dialog.setTitle("请选择模式");
                    dialog.setItems(new String[]{"正常密码保存模式", "随机密码生成模式"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0: {
                                    Intent intent = new Intent(DisplayPasswordActivity.this, ModifyPasswordActivity.class);
                                    intent.putExtra("password", password);
                                    startActivity(intent);
                                }
                                break;
                                case 1: {
                                    Intent intent = new Intent(DisplayPasswordActivity.this, AutoModifyPasswordActivity.class);
                                    intent.putExtra("password", password);
                                    startActivity(intent);
                                }
                                break;
                            }
                        }
                    });
                    dialog.show();
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(DisplayPasswordActivity.this);
                    dialog.setTitle("警告");
                    dialog.setMessage("是否删除此密码信息？");
                    dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PasswordPresenter passwordPresenter1 = new PasswordPresenter();
                            boolean flag = passwordPresenter1.deletePassword(password);
                            if (flag) {
                                finish();
                            } else {
                                Toast.makeText(DisplayPasswordActivity.this, "密码信息删除失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }
            });

            showQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(showQuestion.getText().equals(DisplayPasswordActivity.this.getString(R.string.show_question))){
                        LinearLayout linearLayout =(LinearLayout)findViewById(R.id.show_linear_layout);
                        linearLayout.setVisibility(View.VISIBLE);
                        showQuestion.setText(DisplayPasswordActivity.this.getString(R.string.hint_qusetion));
                    }else{
                        LinearLayout linearLayout =(LinearLayout)findViewById(R.id.show_linear_layout);
                        linearLayout.setVisibility(View.GONE);
                        showQuestion.setText(DisplayPasswordActivity.this.getString(R.string.show_question));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(DisplayPasswordActivity.this,"程序出错",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add:{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(DisplayPasswordActivity.this);
                    dialog.setTitle("请选择模式");
                    dialog.setItems(new String[]{"普通模式", "随机密码模式"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0: {
                                    startActivity(new Intent(DisplayPasswordActivity.this, AddPasswordActivity.class));
                                }
                                break;
                                case 1: {
                                    startActivity(new Intent(DisplayPasswordActivity.this, AutoPasswordActivity.class));
                                }
                                break;
                            }
                        }
                    });
                    dialog.show();
                }break;
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
