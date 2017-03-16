package com.ai.cwf.common;

import android.content.Context;

/**
 * Created at 陈 on 2017/3/15.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class SQLiteUtils {
    private static SQLiteUtils instance;

    private SimpleSQLiteOpenHelper mDB;

    public static SQLiteUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SharedPreferencesUtils.class) {
                instance = new SQLiteUtils(context);
            }
        }
        return instance;
    }

    public SQLiteUtils(Context context) {
        mDB = new SimpleSQLiteOpenHelper(context);
    }

    public SimpleSQLiteOpenHelper getDB() {
        return mDB;
    }
}
