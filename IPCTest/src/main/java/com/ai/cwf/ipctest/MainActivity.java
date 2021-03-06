package com.ai.cwf.ipctest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ai.cwf.common.SharedPreferencesUtils;
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
        findViewById(R.id.request_sp_callback).setOnClickListener(this);
        findViewById(R.id.request_sqlite_callback).setOnClickListener(this);
        try {
            Context anotherContent = this.createPackageContext("com.ai.cwf.ipcdemo", Context.CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.request_callback:
                intent.setAction(TestBroadcastReceiver.ACTION);
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
            case R.id.request_sp_callback:
                try {
                    Context anotherContent = this.createPackageContext("com.ai.cwf.ipcdemo", Context.CONTEXT_IGNORE_SECURITY);
                    Utils.showTip(this, "另一个应用中sp_key的值：" + SharedPreferencesUtils.getInstance(anotherContent).getString("sp_key"), false);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    Utils.showTip(this, "没有找到该应用: " + e.getMessage(), false);
                }
                break;
            case R.id.request_sqlite_callback:
                intent = new Intent(MainActivity.this,TestSQLiteActivity.class);
                startActivity(intent);
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
