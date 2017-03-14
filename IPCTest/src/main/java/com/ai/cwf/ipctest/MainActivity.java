package com.ai.cwf.ipctest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ai.cwf.common.Utils;
import com.ai.cwf.ipctest.receiver.BroadcastReceiverData;
import com.ai.cwf.ipctest.receiver.TestBroadcastReceiver;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.request_callback).setOnClickListener(this);
        findViewById(R.id.request_activity_callback).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.request_callback:
                Intent intent = new Intent(TestBroadcastReceiver.ACTION);
                intent.putExtra(BroadcastReceiverData.DATA_NEED_CALLBACK_BOOLEAN, true);
                sendBroadcast(intent);
                break;
            case R.id.request_activity_callback:
                if (Utils.checkActionIsExist(this, "com.ai.cwf.ipcdemo.test.activity")) {
                    Intent mI = new Intent("com.ai.cwf.ipcdemo.test.activity");
                    startActivityForResult(mI, 0);
                } else {
                    Utils.showTip(this, "不存在该应用", false);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (data.hasExtra("data")) {
                        Utils.showTip(this, data.getStringExtra("data"), false);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
