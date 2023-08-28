package com.mosesmin.android.crimeactivitymm;

import java.util.Date;
import java.util.UUID;

/**
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName Crime
 * @Description TODO
 * @Author MosesMin
 * @Date 2023-08-27 19:11
 * @Version 1.0
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime(){
        mId = UUID.randomUUID();//利用Android框架中的Java工具类--UUID，产生唯一ID值。
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
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
}