package com.smalbox.androidex;

import android.app.Activity;
import android.widget.Toast;

/**
 * Android Log 助手
 */
public class LogUtil {

    private Activity activity;
    private static LogUtil instance;

    public LogUtil(Activity activity)
    {
        this.activity = activity;
    }
    public static LogUtil Ins(Activity activity)
    {
        if (instance == null)
        {
            instance = new LogUtil(activity);
        }
        return instance;
    }

    public void Log(String text)
    {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }
}
