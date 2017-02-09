package com.compain.libraryshare.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jie.du on 17/2/8.
 */
public class BitmapLoader {
    /**
     * 根据bitmap保存图片到本地
     *
     * @param bitmap
     * @return
     */
    public static String saveBitmapToLocal(Context context, Bitmap bitmap) {
        if (null == bitmap) {
            return null;
        }
        String filePath;
        FileOutputStream fileOutput = null;
        File imgFile = null;
        try {
            File desDir = context.getFilesDir();
            //使用随机数使得发送的图片的缩略图文件名不相同
            imgFile = new File(desDir.getAbsoluteFile() + "/" + String.valueOf(bitmap.hashCode()) + ".png");
            imgFile.createNewFile();
            fileOutput = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutput);
            fileOutput.flush();
            filePath = imgFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            filePath = null;
        } catch (IOException e) {
            e.printStackTrace();
            filePath = null;
        } finally {
            if (null != fileOutput) {
                try {
                    fileOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }
}
