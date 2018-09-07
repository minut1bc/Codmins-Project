package com.ibl.apps.myphotokeyboard.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;

import java.util.List;
import java.util.TimerTask;

/**
 * Created by iblinfotech on 01/06/17.
 */

public class InputMethodChangedReceiver extends TimerTask {
    ActivityManager activityManager;
    Context f45c;
    boolean fromapp;

    public InputMethodChangedReceiver(Context con, boolean isFromApp) {
        this.f45c = con;
        this.activityManager = (ActivityManager) this.f45c.getSystemService("activity");
    }

    public void run() {
        String appProcesses = "com.android.settings";
        try {
            List<ActivityManager.RunningTaskInfo> tasks = this.activityManager.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                ((ActivityManager.RunningTaskInfo) tasks.get(1)).topActivity.getPackageName().equals(this.f45c.getPackageName());
            }
            if (Build.VERSION.SDK_INT > 20) {
                appProcesses = ((ActivityManager.RunningAppProcessInfo) this.activityManager.getRunningAppProcesses().get(0)).processName;
            } else {
                appProcesses = ((ActivityManager.RunningTaskInfo) this.activityManager.getRunningTasks(1).get(0)).topActivity.getPackageName();
            }
        } catch (Exception e) {
            try {
                appProcesses = ((ActivityManager.RunningTaskInfo) this.activityManager.getRunningTasks(0).get(0)).topActivity.getPackageName();
            } catch (Exception e2) {
            }
        }
        if (appProcesses.equals("com.android.settings") && KeyboardIsEnabled(this.f45c)) {
//            Intent i1 = new Intent(this.f45c, StartActivity.class);
//            i1.setFlags(268468224);
//            this.f45c.startActivity(i1);
//            cancel();
        }
    }

    private boolean KeyboardIsEnabled(Context context) {
        if (((InputMethodManager) context.getSystemService("input_method")).getEnabledInputMethodList().toString().contains(context.getPackageName())) {
            return true;
        }
        return false;
    }
}
