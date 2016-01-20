package com.hanyao.passwordmanager.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.hanyao.passwordmanager.MyApplication;
import com.hanyao.passwordmanager.Presenter.PasswordPresenter;
import com.hanyao.passwordmanager.R;
import com.hanyao.passwordmanager.bean.PasswordList;
import java.util.List;

/**
 * Created by HanYao-Huang on 2016/1/4.
 */
public class PasswordListAdapter extends ArrayAdapter<PasswordList> {
    private int resourceId;

    public PasswordListAdapter(Context context, int resource, List<PasswordList> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        final PasswordList passwordList = getItem(position);
        View view ;
        final ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.newSite=(TextView)view.findViewById(R.id.new_site_item);
            viewHolder.newLoginName=(TextView)view.findViewById(R.id.new_login_name_item);
            viewHolder.loginPassword=(TextView)view.findViewById(R.id.login_password_item);
            viewHolder.showPassword=(TextView)view.findViewById(R.id.show_password_item);
            viewHolder.newCopy=(TextView)view.findViewById(R.id.new_copy_button_in_list);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.newSite.setText("站点/应用："+passwordList.getSite());
        viewHolder.newLoginName.setText("登录名：" + passwordList.getLoginName());
        StringBuilder stringBuilder= new StringBuilder();
        for(int i=0;i<passwordList.getPasswordString().length();i++){
            stringBuilder.append("*");
        }
         final String bforeShow = stringBuilder.toString();
        viewHolder.newCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) MyApplication.getContext().getSystemService(MyApplication.getContext().CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("password", passwordList.getPasswordString());
                clipboardManager.setPrimaryClip(clipData);
                PasswordPresenter passwordPresenter = new PasswordPresenter();
                passwordPresenter.addSee(passwordList.getPassword());
                Toast.makeText(MyApplication.getContext(), "密码已复制到剪贴板", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.loginPassword.setText("登录密码：" + bforeShow);
        viewHolder.showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewHolder.showPassword.getText().toString()) {
                    case "显示密码": {
                        viewHolder.loginPassword.setText("登录密码：" + passwordList.getPasswordString());
                        viewHolder.showPassword.setText("隐藏密码");
                    }
                    break;
                    case "隐藏密码": {
                        viewHolder.loginPassword.setText("登录密码：" + bforeShow);
                        viewHolder.showPassword.setText("显示密码");
                    }break;
                }
            }
        });

        return view;
    }

    class ViewHolder{
        TextView newSite;
        TextView newLoginName;
        TextView loginPassword;
        TextView showPassword;
        TextView newCopy;
    }
}
