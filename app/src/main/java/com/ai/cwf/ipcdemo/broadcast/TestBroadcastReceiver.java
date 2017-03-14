package com.ai.cwf.ipcdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ai.cwf.ipcdemo.App;

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
        if (intent.getAction().equals(ACTION)) {
            if (intent.getBooleanExtra(BroadcastReceiverData.DATA_NEED_CALLBACK_BOOLEAN, false)) {
                Intent mIntent = new Intent(ACTION);
                mIntent.putExtra(BroadcastReceiverData.DATA_CALLBACK_STRING, "这个是我的返回数据");
                mIntent.putExtra(BroadcastReceiverData.DATA_NEED_CALLBACK_BOOLEAN, false);
                App.getInstance().sendBroadcast(mIntent);
            }
        }
    }
}
