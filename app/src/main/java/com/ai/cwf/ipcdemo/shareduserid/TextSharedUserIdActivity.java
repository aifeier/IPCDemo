package com.ai.cwf.ipcdemo.shareduserid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import com.ai.cwf.common.SharedPreferencesUtils;
import com.ai.cwf.common.Utils;
import com.ai.cwf.ipcdemo.R;

/**
 * Created at 陈 on 2017/3/15.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class TextSharedUserIdActivity extends AppCompatActivity {
    private AppCompatEditText input;
    private AppCompatButton send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("使用sharedUserId共享数据");
        setContentView(R.layout.activity_broadcase);
        input = (AppCompatEditText) findViewById(R.id.input);
        send = (AppCompatButton) findViewById(R.id.send);
        send.setText("保存到SharedPreferences");
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success =
                        SharedPreferencesUtils.getInstance(getApplicationContext()).putString("sp_key"
                                , TextUtils.isEmpty(input.getText().toString()) ? "没有内容" : input.getText().toString());
                if (success) {
                    Utils.showTip(getApplicationContext(), "保存成功", false);
                } else {
                    Utils.showTip(getApplicationContext(), "保存失败", false);
                }
            }
        });
    }
}
