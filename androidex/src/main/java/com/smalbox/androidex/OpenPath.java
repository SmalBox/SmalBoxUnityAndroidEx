package com.smalbox.androidex;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;

public class OpenPath {

    private Activity activity;
    private static OpenPath instance;

    public OpenPath(Activity activity)
    {
        this.activity = activity;
    }
    public static OpenPath Ins(Activity activity)
    {
        if (instance == null)
        {
            instance = new OpenPath(activity);
        }
        return instance;
    }

    /**
     * 分享文本到其他应用
     * @param text 要分享的文本
     */
    public void Share(String text)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text); // 发送了这段文本
        intent.setType("text/plain");
        activity.startActivity(Intent.createChooser(intent, "分享文本"));
    }

    /**
     * 分享路径
     * @param path 要分享的路径
     */
    public void SharePath(String path)
    {
        //Intent intent = new Intent(Intent.ACTION_VIEW);
        Intent intent = new Intent();
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + path);
        intent.setDataAndType(uri, "file/*");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        try {
            Toast.makeText(activity, "分享路径:" + path, Toast.LENGTH_SHORT).show();
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "分享异常：" + e, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 启动文件管理器打开指定页面
     * @param packageName 文件管理器包名
     * @param activityPath 文件管理器页面活动名
     * @param path 基于Android默认存储路径的相对路径
     */
    public void OpenFileExplorer(String packageName, String activityPath, String path)
    {
        packageName = packageName == "" ? "com.android.fileexplorer" : packageName;
        activityPath = activityPath == "" ? "com.android.fileexplorer.activity.FileActivity" : activityPath;

        Intent intent = new Intent();
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + path);
        intent.setDataAndType(uri, "file/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        ComponentName comp = new ComponentName(packageName, activityPath);
        intent.setComponent(comp);
        try {
            // 判断是否有这个activity
            if (intent.resolveActivityInfo(
                    activity.getPackageManager(),
                    PackageManager.MATCH_DEFAULT_ONLY) != null)
            {
                Toast.makeText(activity, "打开文件管理器", Toast.LENGTH_SHORT).show();
                activity.startActivity(intent);
            }
            else
            {
                Toast.makeText(activity, "未找到文件管理器", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开应用
     * @param packageName 应用包名
     */
    public void OpenApp(String packageName)
    {
        packageName = packageName == "" ? "com.cyanogenmod.filemanager" : packageName;
        PackageManager packageManager = activity.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent != null)
        {
            Toast.makeText(activity, "打开应用" + packageName, Toast.LENGTH_SHORT).show();
            activity.startActivity(intent);
        }
        else
        {
            Toast.makeText(activity, "打开"+ packageName +"失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检查应用是否安装
     * @param packageName 完整包名
     * @return 返回是否安装
     */
    public boolean CheckAppInstalled(String packageName)
    {
        if (TextUtils.isEmpty(packageName))
        {
            return false;
        }
        try {
            activity.getPackageManager().getPackageInfo(packageName, 0);
        }catch (Exception e)
        {
            return false;
        }
        return true;
    }


    /**
     * 选取内容(测试中)
     * @param path
     */
    public void Open(String path)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(
                Uri.fromFile(new File(path).getParentFile()),
                "*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivityForResult(intent, 1);
    }

    /**
     * 选择文件(测试中)
     */
    public void SelectFile()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
        intent.setDataAndType(uri, "*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        activity.startActivity(Intent.createChooser(intent, "选择文件"));
    }
}
