package com.ai.cwf.ipcdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

/**
 * Created at 陈 on 2017/3/14.
 * 自定义启动action
 * 可被其它应用通过action调用的,和调用自己activity一样
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class TestActivity extends AppCompatActivity {
    private final String ACTION = "com.ai.cwf.ipcdemo.test.activity";
    private AppCompatEditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        editText = (AppCompatEditText) findViewById(R.id.edit);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data", "你填写的字符是：" + editText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
