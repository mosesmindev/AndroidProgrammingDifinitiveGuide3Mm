package com.mosesmin.android.crimeintentmm;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName Crime
 * @Description TODO
 * @Author MosesMin
 * @Date 2023-08-28 0:38
 * @Version 1.0
 *
 */
public class Crime {
    private static final String TAG = "Crime";

    private UUID mId; //Android框架中的java工具类--UUID，生成可靠的唯一ID
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    //15.2 添加嫌疑人信息至模型层 15-2 添加mSuspect成员变量
    private String mSuspect;

    // 14.5.1 使用CursorWrapper  14-15 新增Crime构造方法
    public Crime() {
       this(UUID.randomUUID());
    }

    // 14.5.1 使用CursorWrapper  14-15 新增Crime构造方法
    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }



    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getSuspect() {
        return mSuspect;
    }
    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    //16.2.2 指定照片存放位置 16-5 添加文件名获取方法
    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}