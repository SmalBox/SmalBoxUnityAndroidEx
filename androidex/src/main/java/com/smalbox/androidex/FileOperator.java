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

    public void WriteAllByte(String dirPath, String fileName, String mimeType, byte[] content)
    {
        MediaStoreUtils.SaveImageToMediaStore(
                activity,
                dirPath,
                fileName,
                mimeType,
                content);
    }

    public void ReadAllByte(String path)
    {
    }
}
