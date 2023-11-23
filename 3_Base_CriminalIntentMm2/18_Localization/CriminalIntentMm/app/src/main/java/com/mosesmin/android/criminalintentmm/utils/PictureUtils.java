package com.mosesmin.android.criminalintentmm.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-21 23:17:29
 */
public class PictureUtils {
    private static final String TAG = "PictureUtils";

    // 代码清单16-9 创建 getScaledBitmap(...) 方法（PictureUtils.java） -- start
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        // Read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        // Figure out how much to scale down by
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            float heightScale = srcHeight / destHeight;
            float widthScale = srcWidth / destWidth;
            inSampleSize = Math.round(heightScale > widthScale ? heightScale :
                    widthScale);
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        // Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options);
    }
    // 代码清单16-9 创建 getScaledBitmap(...) 方法（PictureUtils.java） -- end

    // 代码清单16-10 编写合理的缩放方法（PictureUtils.java） -- start
    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay()
                .getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }
    // 代码清单16-10 编写合理的缩放方法（PictureUtils.java） -- end
}