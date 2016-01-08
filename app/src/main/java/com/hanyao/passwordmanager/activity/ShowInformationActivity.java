package com.hanyao.passwordmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hanyao.passwordmanager.util.LogUtil;

import com.hanyao.passwordmanager.R;

public class ShowInformationActivity extends BaseActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_information);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle(this.getString(R.string.app_name));
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView =(TextView)findViewById(R.id.information);
        Intent intent = getIntent();
        String information = intent.getStringExtra("information");
        LogUtil.d("information", information);
        CharSequence charSequence = Html.fromHtml(information);
        textView.setText(charSequence);

    }

    public static void actionShowInformationActivity(Context context,String information){
        Intent intent = new Intent(context,ShowInformationActivity.class);
        intent.putExtra("information",information);
        context.startActivity(intent);
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
}
