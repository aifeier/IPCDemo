package com.ai.cwf.ipctest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ai.cwf.common.SQLiteUtils;
import com.ai.cwf.common.SharedPreferencesUtils;
import com.ai.cwf.common.User;
import com.ai.cwf.common.Utils;

import java.util.ArrayList;

/**
 * Created at 陈 on 2017/3/16.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class TestSQLiteActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatEditText editName, editSchool;
    private ListView mListView;
    private Adapter adapter;
    private ArrayList<User> users = new ArrayList<>();
    Context anotherContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用sharedUserId共享数据库");
        setContentView(R.layout.activity_sqlite);
        editName = (AppCompatEditText) findViewById(R.id.edit_name);
        editSchool = (AppCompatEditText) findViewById(R.id.edit_school);
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.listview);
        try {
            anotherContent = this.createPackageContext("com.ai.cwf.ipcdemo", Context.CONTEXT_IGNORE_SECURITY);
//            Utils.showTip(this, "另一个应用中sp_key的值：" + SharedPreferencesUtils.getInstance(anotherContent).getString("sp_key"), false);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            anotherContent=this;
            Utils.showTip(this, "没有找到该应用: " + e.getMessage(), false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        users = SQLiteUtils.getInstance(anotherContent).getDB().getAllDate();
        if (adapter == null) {
            adapter = new Adapter();
            mListView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                SQLiteUtils.getInstance(anotherContent).getDB().insertUserToDB(new User("姓名" + editName.getText().toString(), "学校" + editSchool.getText().toString()));
                break;
            case R.id.btn_update:
                SQLiteUtils.getInstance(anotherContent).getDB().updateUserFormDB(new User("姓名" + editName.getText().toString(), "学校" + editSchool.getText().toString()));
                break;
            case R.id.btn_delete:
                SQLiteUtils.getInstance(anotherContent).getDB().deleteUserFormDB(new User("姓名" + editName.getText().toString(), "学校" + editSchool.getText().toString()));
                break;
        }
        users = SQLiteUtils.getInstance(anotherContent).getDB().getAllDate();
        adapter.notifyDataSetChanged();
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public User getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = getLayoutInflater().inflate(android.R.layout.simple_expandable_list_item_1, null);
                viewHolder.title = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(getItem(position).name + ":" + getItem(position).school);
            return convertView;
        }

        class ViewHolder {
            TextView title;
        }
    }

}
