package com.mosesmin.android.criminalintentmm;

import java.util.Date;
import java.util.UUID;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO 模型层Model 数据
 * @Author: MosesMin
 * @Date: 2023-10-25 22:44:54
 */
// 7.3.4 创建Crime类  代码清单7-3  Crime类的新增代码（Crime.java）
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime(){
        // 代码清单14-15 新增 Crime 构造方法（Crime.java） -- start1
        this(UUID.randomUUID());
        // 代码清单14-15 新增 Crime 构造方法（Crime.java） -- end1
    }

    // 代码清单14-15 新增 Crime 构造方法（Crime.java） -- start2
    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }
    // 代码清单14-15 新增 Crime 构造方法（Crime.java） -- end2

    // 代码清单7-4 已生成的getter方法与setter方法（Crime.java） -- start
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
    // 代码清单7-4 已生成的getter方法与setter方法（Crime.java） -- end
}