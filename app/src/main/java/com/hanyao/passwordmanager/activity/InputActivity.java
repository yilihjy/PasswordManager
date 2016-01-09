package com.hanyao.passwordmanager.activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hanyao.passwordmanager.Presenter.PasswordPresenter;
import com.hanyao.passwordmanager.R;
public class InputActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tip;
    private TextView insert;
    private TextView cover;
    private Button insertButton;
    private Button coverButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        findView();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(this.getString(R.string.app_name));
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tip.setText("请先将passwords.xml文件放在" + Environment.getExternalStorageDirectory().getPath() + "目录下");
        insert.setText("插入导入将不会影响您现在的密码信息，但可能会导致密码信息重复，请在导入完成后手动删除重复项目");
        cover.setText("覆盖导入将会在删除您当前的密码信息之后导入passwords.xml文件中的密码信息，请谨慎选择这种导入模式");
        insertButton.setText("插入导入");
        coverButton.setText("覆盖导入");
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InputTask().execute(1);
            }
        });
        coverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InputTask().execute(0);
            }
        });



    }

    private void findView(){
        tip = (TextView)findViewById(R.id.input_text_view);
        insert=(TextView)findViewById(R.id.insert_text_view);
        cover=(TextView)findViewById(R.id.cover_text_view);
        insertButton=(Button)findViewById(R.id.insert_button);
        coverButton=(Button)findViewById(R.id.cover_button);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
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

    class InputTask extends AsyncTask<Integer,Intent,Integer> {

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress);
        @Override
        protected void onPreExecute(){
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                if(new PasswordPresenter().inputPassword(params[0])){
                    return 1;
                }else
                    return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }

        @Override
        protected  void onPostExecute(Integer result){
            progressBar.setVisibility(View.GONE);
            if(result==1){
                Toast.makeText(InputActivity.this,"密码信息导入成功",Toast.LENGTH_SHORT).show();
                finish();
            }else if(result==0){
                Toast.makeText(InputActivity.this,"passwords.xml文件不存在",Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(InputActivity.this,"密码信息导入失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
