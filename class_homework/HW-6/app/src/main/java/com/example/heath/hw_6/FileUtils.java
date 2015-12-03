package com.example.heath.hw_6;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by heath on 15-11-27.
 */
public class FileUtils {
    public void saveContent(Context context, String fileName, String fileContent) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput
                    (fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(fileContent.getBytes());
            fileOutputStream.close();
            Toast.makeText(context, "Save File Succeed", Toast.LENGTH_SHORT)
                    .show();
        } catch (IOException e) {
            Toast.makeText(context, "Save File Failed", Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    public void delteFile(Context context, String fileName) {
        context.deleteFile(fileName);
        Toast.makeText(context, "Delete File Succeed", Toast.LENGTH_SHORT)
                .show();
    }

    public String getContext(Context context, String fileName) {
        try {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            byte[] contents = new byte[fileInputStream.available()];
            fileInputStream.read(contents);
            fileInputStream.close();
            Toast.makeText(context, "Read File Succeed", Toast.LENGTH_SHORT)
                    .show();
            return new String(contents);
        } catch (IOException e) {
            Toast.makeText(context, "Read File Failed", Toast.LENGTH_SHORT)
                    .show();;
            e.printStackTrace();
            return "";
        }
    }
}
