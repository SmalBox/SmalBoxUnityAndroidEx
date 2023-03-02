package com.smalbox.androidex;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

public class DirectoryOperator {

    private Activity activity;
    private static DirectoryOperator instance;

    public DirectoryOperator(Activity activity)
    {
        this.activity = activity;
    }
    public static DirectoryOperator Ins(Activity activity)
    {
        if (instance == null)
        {
            instance = new DirectoryOperator(activity);
        }
        return instance;
    }

    /**
     * 创建目录(检查路径是否存在，不存在则创建路径)
     * @param dir 要创建的目录
     */
    public void CreateDirectory(String dir, boolean openLog)
    {
        if (openLog) Toast.makeText(activity, "创建目录" + dir, Toast.LENGTH_SHORT).show();

        // 要创建文件夹的父文件夹的URI
        Uri parentUri = DocumentsContract.buildDocumentUri(
                MediaStore.AUTHORITY,
                //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath()
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()
        );
        // 创建新文件夹的元数据
        ContentValues values = new ContentValues();
        values.put(DocumentsContract.Document.COLUMN_DISPLAY_NAME, dir);
        values.put(DocumentsContract.Document.COLUMN_MIME_TYPE, DocumentsContract.Document.MIME_TYPE_DIR);
        // 插入新文件夹
        Uri uri = activity.getContentResolver().insert(parentUri, values);
    }

    /**
     * 删除目录
     * @param dir 要删除的目录
     */
    public void DeleteDirectory(String dir, boolean openLog)
    {
        if (openLog) Toast.makeText(activity, "删除目录" + dir, Toast.LENGTH_SHORT).show();
    }
}
