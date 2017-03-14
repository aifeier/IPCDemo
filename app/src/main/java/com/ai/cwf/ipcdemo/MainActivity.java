package com.ai.cwf.ipcdemo;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ai.cwf.ipcdemo.broadcast.BroadcastReceiverActivity;

import java.util.ArrayList;
import java.util.List;


/*
http://www.cnblogs.com/rayray/p/3175091.html
*/
public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private List<Modal> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview);
        mList.add(new Modal("使用BroadcastReceiver", BroadcastReceiverActivity.class));
        mList.add(new Modal("哈哈", null));
        mList.add(new Modal("哈哈", null));
        mListView.setAdapter(new Adapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Modal item = mList.get(position);
                if (item.mClass != null) {
                    startActivity(new Intent(MainActivity.this, item.mClass));
                }
            }
        });
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Modal getItem(int position) {
            return mList.get(position);
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
                convertView.setTag(convertView);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(getItem(position).title);
            return convertView;
        }

        class ViewHolder {
            TextView title;
        }
    }


}
