package com.smalbox.androidex;

import android.app.Activity;

public class FileOperator {

    private Activity activity;
    private static FileOperator instance;

    public FileOperator(Activity activity)
    {
        this.activity = activity;
    }
    public static FileOperator Ins(Activity activity)
    {
        if (instance == null)
        {
            instance = new FileOperator(activity);
        }
        return instance;
    }

    public void WriteAllByte(String path, byte[] content)
    {
    }

    public void ReadAllByte(String path)
    {
    }
}
