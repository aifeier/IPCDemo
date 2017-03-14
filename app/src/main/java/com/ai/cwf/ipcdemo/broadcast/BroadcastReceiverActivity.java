package com.ai.cwf.ipcdemo.broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import com.ai.cwf.ipcdemo.R;

/**
 * Created at 陈 on 2017/3/13.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class BroadcastReceiverActivity extends AppCompatActivity {
    private AppCompatEditText input;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcase);
        input = (AppCompatEditText) findViewById(R.id.input);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestBroadcastReceiver.ACTION);
                intent.putExtra(BroadcastReceiverData.DATA_TEST, TextUtils.isEmpty(input.getText().toString()) ? "没有内容" : "输入的内容：" + input.getText().toString());
                sendBroadcast(intent);
            }
        });
    }
}
