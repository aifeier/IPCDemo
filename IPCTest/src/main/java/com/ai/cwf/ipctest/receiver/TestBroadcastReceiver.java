package com.ai.cwf.ipctest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ai.cwf.common.Utils;
import com.ai.cwf.ipctest.App;

/**
 * Created at 陈 on 2017/3/13.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class TestBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.ai.cwf.broadcast.test";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra(BroadcastReceiverData.DATA_TEST))
            Utils.showTip(App.getInstance(), getIntentString(intent, BroadcastReceiverData.DATA_TEST), false);
        if (intent.hasExtra(BroadcastReceiverData.DATA_NEED_CALLBACK_BOOLEAN))
            Utils.showTip(App.getInstance(), getIntentString(intent, BroadcastReceiverData.DATA_CALLBACK_STRING), false);
    }

    private String getIntentString(Intent intent, String key) {
        String data = "";
        if (intent.hasExtra(key)) {
            data = intent.getStringExtra(key);
        }

        return data;
    }
}
