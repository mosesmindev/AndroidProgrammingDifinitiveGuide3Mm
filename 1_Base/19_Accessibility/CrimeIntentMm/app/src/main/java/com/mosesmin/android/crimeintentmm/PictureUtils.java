package com.mosesmin.android.crimeintentmm;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CrimeIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-09-12 08:40:55
 */

// 16.4 缩放和显示位图
public class PictureUtils {
    private static final String TAG = "PictureUtils";

    //1  // 16.4 缩放和显示位图  16-9 创建getScaledBitmap(...)方法
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight){
        // Read in the dimension of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        // Figure out how much to scale down by
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth){
            float heightScale = srcHeight / destHeight;
            float widthScale = srcWidth / destWidth;

            inSampleSize = Math.round(heightScale > widthScale ? heightScale:
                    widthScale);
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        // Read in and create final bitmap
        return BitmapFactory.decodeFile(path,options);
    }

    //2  // 16.4 缩放和显示位图 16-10 编写合理的缩放方法
    public static Bitmap getScaledBitmap(String path, Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay()
                .getSize(size);

        return getScaledBitmap(path, size.x, size.y);
    }

    // 3 与工作有关  与学习项目无关   https://blog.csdn.net/jian11058/article/details/103140215
    // @xx 你好，logo有两个问题：1.设置想要的图片大小时候参数文档有吗，
    // 具体我设置几能返回多大的图片，现有的文档里没写这块。
    // 2，有没有一个办法可以无论图片尺寸多大，只返这个台最大的logo回来？

    /**
     * @param bitmap 传入获取的Bitmap图片对象文件
     * @param goalWidth 传入需要给Bitmap图片文件设置的目标宽度
     * @param goalHeight 传入需要给Bitmap图片文件设置的目标宽度
     * @return Bitmap 返回设置好宽高的目标图片对象Bitmap;例如参数传入bitmap、128、128 就能返回一个宽高为128*128的图片对象
     */
    public static Bitmap getFixedSizeBitmap(Bitmap bitmap,int goalWidth,int goalHeight){
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();

        float scaleWidth = ((float) goalWidth)/srcWidth;
        float scaleHeight = ((float) goalHeight)/srcHeight;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap goalBitmap = Bitmap.createBitmap(bitmap, 0, 0, srcWidth, srcHeight, matrix,
                true);
        return goalBitmap;

    }
}