package com.hanyao.passwordmanager.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
        ViewHolder viewHolder;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.site =(TextView)view.findViewById(R.id.site_item);
            viewHolder.loginName= (TextView)view.findViewById(R.id.login_name_item);
            viewHolder.copy = (Button)view.findViewById(R.id.copy_button_in_list);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.site.setText(passwordList.getSite());
        viewHolder.loginName.setText(passwordList.getLoginName());
        viewHolder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) MyApplication.getContext().getSystemService(MyApplication.getContext().CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("password", passwordList.getPasswordString());
                clipboardManager.setPrimaryClip(clipData);
                PasswordPresenter passwordPresenter = new PasswordPresenter();
                passwordPresenter.addSee(passwordList.getPassword());
                Toast.makeText(MyApplication.getContext(),"密码已复制到剪贴板",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    class ViewHolder{
        TextView site;
        TextView loginName;
        Button copy;
    }
}
