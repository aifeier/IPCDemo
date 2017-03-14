package com.ai.cwf.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;

/**
 * Created at 陈 on 2017/3/14.
 * 公共类
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class Utils {

    private static Toast mToast;
    private static Handler mHandler = new Handler(Looper.getMainLooper());


    /* check user-defined intent action is exist in this device
    *  user-defined activity action in another app
    *  if exist can user this action to start an activity in another app
    * */
    public static boolean checkActionIsExist(Context context, String actionName) {
        Intent intent = new Intent();
        intent.setAction(actionName);
        PackageManager packageManager = context.getPackageManager();
        ResolveInfo resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo != null && resolveInfo.activityInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    /*check the app is installed in this device*/
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            //System.out.println("没有安装");
            return false;
        } else {
            //System.out.println("已经安装");
            return true;
        }
    }

    /*install apk form SDCard*/
    public static void installApk(Activity activity, String apkFilePath) {
        if (!apkFilePath.endsWith(".apk")) {
            showTip(activity, "请指定正确的文件路径", false);
            return;
        }
        File file = new File(apkFilePath);
        if (!file.isFile() || !file.exists()) {
            showTip(activity, "没有找到安装包", false);
            return;
        }
        Intent intentt = new Intent();
        intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentt.setAction(Intent.ACTION_VIEW);
        intentt.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        activity.startActivity(intentt);
    }

    /*全局显示Toast*/
    public static void showTip(final Context context, final String msg, final boolean timeLong) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, msg,
                            timeLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                } else {
                    mToast.setText(msg);
                }
                mToast.show();
            }
        });
    }
}
