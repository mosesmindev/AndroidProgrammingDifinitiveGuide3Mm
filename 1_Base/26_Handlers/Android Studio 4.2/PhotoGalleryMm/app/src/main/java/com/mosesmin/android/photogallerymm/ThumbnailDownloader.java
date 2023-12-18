package com.mosesmin.android.photogallerymm;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-12-12 22:55:22
 */
// 代码清单26-4 初始线程代码（ThumbnailDownloader.java） -- start
public class ThumbnailDownloader<T> extends HandlerThread {
    private static final String TAG = "ThumbnailDownloader";
    // 代码清单26-7 添加一些常量和成员变量（ThumbnailDownloader.java） -- 1start
    public static final int MESSAGE_DOWNLOAD = 0;
    // 代码清单26-7 添加一些常量和成员变量（ThumbnailDownloader.java） -- 1end
    private Boolean mHasQuit = false;

    // 代码清单26-7 添加一些常量和成员变量（ThumbnailDownloader.java） -- 2start
    private Handler mRequestHandler;
    private ConcurrentMap<T,String> mRequestMap = new ConcurrentHashMap<>();
    // 代码清单26-7 添加一些常量和成员变量（ThumbnailDownloader.java） -- 2end

    public ThumbnailDownloader(){
        super(TAG);
    }

    /**
     * 线程退出方法
     * @return
     */
    @Override
    public boolean quit(){
        mHasQuit = true;
        return super.quit();
    }

    /**
     * 存根方法 ：存根方法是指在一个类中定义的、但不实现的方法。
     * 存根方法通常用于在类的设计阶段，以便在编写代码之前对其进行测试。
     * @param target
     * @param url
     */
    public void queueThumbnail(T target,String url){
        Log.i(TAG, "Got a URL:" + url);
        // 代码清单26-8 获取和发送消息（ThumbnailDownloader.java） -- start
        if (url == null){
            mRequestMap.remove(target);
        }else{
            mRequestMap.put(target,url);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD,target).sendToTarget();
        }
        // 代码清单26-8 获取和发送消息（ThumbnailDownloader.java） -- end
    }
}
// 代码清单26-4 初始线程代码（ThumbnailDownloader.java） -- end