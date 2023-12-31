package com.mosesmin.android.photogallerymm.utils.background.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.mosesmin.android.photogallerymm.utils.net.FlickrFetchr;

import java.io.IOException;
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
/*
ThumbnailDownloader 类使用了 <T> 泛型参数。 ThumbnailDownloader 类的使用者（这
里指 PhotoGalleryFragment）， 需要使用某些对象来识别每次下载，并确定该使用已下载图片
更新哪个UI元素。有了泛型参数，实施起来方便了很多。    
 */
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

    // 代码清单26-10 处理消息（ThumbnailDownloader.java） -- 1start
    private Handler mResponseHandler;
    private ThumbnailDownloadListener<T> mThumbnailDownloadListener;

    public interface ThumbnailDownloadListener<T> {
        void onThumbnailDownloaded(T target, Bitmap thumbnail);
    }

    public void setThumbnailDownloadListener(ThumbnailDownloadListener<T> listener) {
        mThumbnailDownloadListener = listener;
    }
    // 代码清单26-10 处理消息（ThumbnailDownloader.java） -- 1end

    // 代码清单26-10 处理消息（ThumbnailDownloader.java） -- 2
    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    /**
     * 代码清单26-9 处理消息（ThumbnailDownloader.java） -- 1
     */
    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mRequestHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD){
                    T target = (T) msg.obj;
                    Log.i(TAG, "Got a request for URL: " + mRequestMap.get(target));
                    handleRequest(target);
                }
            }
        };
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
     * @param target T target 标识具体那次下载
     * @param url  下载链接
     */
    public void queueThumbnail(T target,String url){
        Log.i(TAG, "Got a URL:" + url);
        // 代码清单26-8 获取和发送消息（ThumbnailDownloader.java） -- start
        if (url == null){
            mRequestMap.remove(target);
        }else{
            mRequestMap.put(target,url);
            /*
            从 mRequestHandler 直接获取到消息后， mRequestHandler 也就自动成为了这个新 Message
            对象的 target 。这表明 mRequestHandler 会负责处理从消息队列中取出的这个消息。这个消息
            的 what 属性是 MESSAGE_DOWNLOAD 。它的 obj 属性是传递给 queueThumbnail(...) 方法的 T
            target 值（这里指 PhotoHolder ）。
            新消息就代表指定为 T target （ RecyclerView 中的 PhotoHolder ）的下载请求。
             */
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD,target).sendToTarget();
        }
        // 代码清单26-8 获取和发送消息（ThumbnailDownloader.java） -- end
    }



    /**
     * 代码清单26-13 添加清理方法（ThumbnailDownloader.java）
     */
    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
        mRequestMap.clear();
    }

    /**
     * 代码清单26-9 处理消息（ThumbnailDownloader.java） --2
     * @param target
     */
    private void handleRequest(final T target) {
        try {
            final String url = mRequestMap.get(target);
            if (url == null) {
                return;
            }
            byte[] bitmapBytes = new FlickrFetchr().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory
                    .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            Log.i(TAG, "Bitmap created");

            // 代码清单26-12 图片下载与显示（ThumbnailDownloader.java） -- start
            mResponseHandler.post(new Runnable() {
                public void run() {
                    if (mRequestMap.get(target) != url ||
                            mHasQuit) {
                        return;
                    }
                    mRequestMap.remove(target);
                    mThumbnailDownloadListener.onThumbnailDownloaded(target, bitmap);
                }
            });
            // 代码清单26-12 图片下载与显示（ThumbnailDownloader.java） -- end
        } catch (IOException ioe) {
            Log.e(TAG, "Error downloading image", ioe);
        }
    }
}
// 代码清单26-4 初始线程代码（ThumbnailDownloader.java） -- end