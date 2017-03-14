package com.ai.cwf.ipctest;

import android.app.Application;

/**
 * Created at 陈 on 2017/3/14.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        if (instance == null) {
            synchronized (App.class) {
                if (instance == null) {
                    instance = new App();
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
