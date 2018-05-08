package com.ai.cwf.ipcdemo.shareduserid;

import android.content.ContentValues;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ai.cwf.common.SQLiteUtils;
import com.ai.cwf.common.SelfContentProvider;
import com.ai.cwf.common.SimpleSQLiteOpenHelper;
import com.ai.cwf.common.User;
import com.ai.cwf.ipcdemo.R;

import java.util.ArrayList;

/**
 * Created at 陈 on 2017/3/16.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class TestSQLiteActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_USE_CONTENT_PROVIDER = "user_content_provider";

    private boolean useProvider;
    private AppCompatEditText editName, editSchool;
    private ListView mListView;
    private Adapter adapter;
    private ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        useProvider = getIntent().getBooleanExtra(KEY_USE_CONTENT_PROVIDER, false);
        if (useProvider) {
            setTitle("使用ContentProvider共享数据");
        } else
            setTitle("使用sharedUserId共享数据库");
        setContentView(R.layout.activity_sqlite);
        editName = (AppCompatEditText) findViewById(R.id.edit_name);
        editSchool = (AppCompatEditText) findViewById(R.id.edit_school);
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.listview);
        refresh();
        getContentResolver().registerContentObserver(SelfContentProvider.UserTable.CONTENT_URI, false, observer);
    }

    private ContentObserver observer = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            if (SelfContentProvider.User_URI_CODE == SelfContentProvider.sUriMatcher.match(uri)) {
                refresh();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void refresh() {
        if (useProvider) {
            users.clear();
            Cursor c = getContentResolver().query(SelfContentProvider.UserTable.CONTENT_URI, null, null, null, null);//查询并获得游标
            if (c.moveToFirst()) {//判断游标是否为空
                do {//移动到指定记录
                    User u = new User(c.getString(c.getColumnIndex(SelfContentProvider.UserTable.COL_USERNAME))
                            , c.getString(c.getColumnIndex(SelfContentProvider.UserTable.COL_SCHOOL)));
                    users.add(u);
                } while (c.moveToNext());
            }
            c.close();
        } else {
            users = SQLiteUtils.getInstance(this).getDB().getAllDate();
        }
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
                if (useProvider) {
                    ContentValues values = new ContentValues();
                    values.put(SelfContentProvider.UserTable.COL_USERNAME, editName.getText().toString());
                    values.put(SelfContentProvider.UserTable.COL_SCHOOL, editSchool.getText().toString());
                    getContentResolver().insert(SelfContentProvider.UserTable.CONTENT_URI, values);
                } else
                    SQLiteUtils.getInstance(this).getDB().insertUserToDB(new User("姓名" + editName.getText().toString(), "学校" + editSchool.getText().toString()));
                break;
            case R.id.btn_update:
                if (useProvider) {
                    ContentValues values = new ContentValues();
                    values.put(SelfContentProvider.UserTable.COL_USERNAME, editName.getText().toString());
                    values.put(SelfContentProvider.UserTable.COL_SCHOOL, editSchool.getText().toString());
                    //删除的条件
                    String whereClause = SelfContentProvider.UserTable.COL_USERNAME + "=?";
                    //删除的条件参数
                    String[] whereArgs = {editName.getText().toString()};
                    getContentResolver().update(SelfContentProvider.UserTable.CONTENT_URI, values, whereClause, whereArgs);
                } else
                    SQLiteUtils.getInstance(this).getDB().updateUserFormDB(new User("姓名" + editName.getText().toString(), "学校" + editSchool.getText().toString()));
                break;
            case R.id.btn_delete:
                if (useProvider) {
                    //删除的条件
                    String whereClause = SelfContentProvider.UserTable.COL_USERNAME + "=?";
                    //删除的条件参数
                    String[] whereArgs = {editName.getText().toString()};
                    getContentResolver().delete(SelfContentProvider.UserTable.CONTENT_URI, whereClause, whereArgs);
                } else
                    SQLiteUtils.getInstance(this).getDB().deleteUserFormDB(new User("姓名" + editName.getText().toString(), "学校" + editSchool.getText().toString()));
                break;
        }
        refresh();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(observer);
    }
}
