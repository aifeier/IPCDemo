package com.ai.cwf.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

/**
 * Created at 陈 on 2017/3/15.
 * 通过设置android:sharedUserId获取对应的context,可以共享数据
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils instance;
    private SharedPreferences mSP;

    public static SharedPreferencesUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SharedPreferencesUtils.class) {
                instance = new SharedPreferencesUtils(context);
            }
        }
        return instance;
    }

    private SharedPreferencesUtils(Context context) {
        mSP = context.getSharedPreferences("share_sp_data", Context.MODE_PRIVATE);
    }

    public SharedPreferences getSp() {
        return mSP;
    }

    public boolean putString(String key, @Nullable String value) {
        mSP.edit().putString(key, value).apply();
        return true;
    }

    public String getString(String key) {
        return mSP.getString(key, "");
    }
}
