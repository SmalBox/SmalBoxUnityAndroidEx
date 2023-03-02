package com.smalbox.androidex;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

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
}