package com.smalbox.androidex;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MediaStoreUtils {

    private static final String DIRECTORY_NAME = "Box";

    public static Uri createImageFile(Context context) throws IOException {
        // Create a filename for the image using timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "IMG_" + timeStamp + ".jpg";

        // Get the pictures directory
        File picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // Create a new directory if it doesn't exist
        File boxDir = new File(picturesDir, DIRECTORY_NAME);
        if (!boxDir.exists()) {
            if (!boxDir.mkdirs()) {
                throw new FileNotFoundException("Failed to create directory: " + boxDir.getAbsolutePath());
            }
        }

        // Create the file in the Box directory
        File file = new File(boxDir, fileName);
        if (!file.createNewFile()) {
            throw new FileNotFoundException("Failed to create file: " + file.getAbsolutePath());
        }

        // Insert the file into the MediaStore
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_MODIFIED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
        ContentResolver resolver = context.getContentResolver();
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        return uri;
    }

    public static void saveImageToMediaStore(Context context, Uri imageUri, byte[] imageData) throws IOException {
        OutputStream outputStream = context.getContentResolver().openOutputStream(imageUri);
        if (outputStream != null) {
            outputStream.write(imageData);
            outputStream.flush();
            outputStream.close();
        } else {
            throw new FileNotFoundException("Failed to open output stream for URI: " + imageUri.toString());
        }
    }

    /**
     * Media方式存储图片
     * @param activity 活动的 Activity
     * @param dirPath 媒体路径下的路径。如："/SmalBox/"
     * @param fileName 图片文件名。如："box.png"
     * @param mimeType 图片mime类型。如："image/png"
     * @param imageData 图片byte数据。
     */
    public static void SaveImageToMediaStore(
            Activity activity,
            String dirPath,
            String fileName,
            String mimeType,
            byte[] imageData)
    {
        Toast.makeText(activity, "1.开始文件存储", Toast.LENGTH_SHORT).show();

        ContentResolver resolver = activity.getContentResolver();
        ContentValues values = new ContentValues();

// Set the file directory path (change this to the desired directory path)
        dirPath = Environment.DIRECTORY_DCIM + dirPath;

// Create the directory if it does not exist
        File dir = new File(Environment.getExternalStoragePublicDirectory(dirPath).getPath());
        if (!dir.exists()) {
            dir.mkdirs();
            Toast.makeText(activity, "2.路径不存在-创建路径", Toast.LENGTH_SHORT).show();
        }

// Set the file details
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        values.put(MediaStore.Images.Media.RELATIVE_PATH, dirPath);

// Insert the image into MediaStore database
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Toast.makeText(activity, "3.插入数据", Toast.LENGTH_SHORT).show();

// Wait for the image to be fully prepared, then write the image data to the output stream
        try {
            OutputStream outputStream = resolver.openOutputStream(uri);
            if (outputStream != null) {
                // Write the image data to the output stream
                //Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.my_photo);
                //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.write(imageData);
                Toast.makeText(activity, "4.写入数据", Toast.LENGTH_SHORT).show();

                outputStream.flush();
                outputStream.close();
                Toast.makeText(activity, "5.完成", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}