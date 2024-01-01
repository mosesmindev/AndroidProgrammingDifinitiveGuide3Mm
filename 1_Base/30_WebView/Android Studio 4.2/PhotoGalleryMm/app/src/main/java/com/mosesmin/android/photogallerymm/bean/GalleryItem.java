package com.mosesmin.android.photogallerymm.bean;

import android.net.Uri;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO 为PhotoGallery应用创建的模型对象类名为 GalleryItem
 * 代码清单25-10 创建模型对象类（GalleryItem.java）
 * @Author: MosesMin
 * @Date: 2023-11-30 22:12:55
 */
public class GalleryItem  {
    private static final String TAG = "GalleryItem";

    private String mCaption;
    private String mId;
    private String mUrl;
    // 代码清单30-1 添加创建图片URL的代码（GalleryItem.java） -- 1
    private String mOwner;

    @Override
    public String toString() {
        return mCaption;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    // 代码清单30-1 添加创建图片URL的代码（GalleryItem.java） -- 2start
    public String getOwner() {
        return mOwner;
    }
    public void setOwner(String owner) {
        mOwner = owner;
    }

    public Uri getPhotoPageUri() {
        return Uri.parse("http://www.flickr.com/photos/")
                .buildUpon()
                .appendPath(mOwner)
                .appendPath(mId)
                .build();
    }
    // 代码清单30-1 添加创建图片URL的代码（GalleryItem.java） -- 2end
}