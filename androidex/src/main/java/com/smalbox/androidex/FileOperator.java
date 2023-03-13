package com.smalbox.androidex;

import android.app.Activity;
import android.widget.Toast;

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

    public void WriteImageAllByte(
            String dirPath,
            MediaStoreUtils.MediaStoreImage rootType,
            String fileName,
            byte[] content)
    {
        // 根据图片名，设置mimetype
        String mimeType;
        String lowerName = fileName.toLowerCase();
        String[] splitName = lowerName.split("\\.");
        if (splitName.length > 1)
        {
            String imgType = splitName[splitName.length - 1];
            switch (imgType)
            {
                case "png":
                    mimeType = "image/png";
                    break;
                case "jpg":
                case "jpeg":
                    mimeType = "image/jpeg";
                    break;
                default:
                    Toast.makeText(activity, "图片文件名异常:" + fileName, Toast.LENGTH_SHORT)
                            .show();
                    return;
            }
        }
        else
        {
            Toast.makeText(activity, "图片文件名异常:" + fileName, Toast.LENGTH_SHORT).show();
            return;
        }
        // 调用存储
        MediaStoreUtils.SaveImageToMediaStore(
                activity,
                rootType,
                dirPath,
                fileName,
                mimeType,
                content,
                false);
    }

    public void ReadAllByte(String path)
    {
    }
}
