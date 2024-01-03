package com.mosesmin.android.draganddrawmm;

import android.graphics.PointF;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:DragAndDrawMm
 * @Description: TODO 代码清单31-6 添加 Box 类（Box.java）
 * @Author: MosesMin
 * @Date: 2024-01-03 22:36:10
 */
public class Box {
    private static final String TAG = "Box";

    private PointF mOrigin;
    private PointF mCurrent;

    public Box(PointF origin) {
        mOrigin = origin;
        mCurrent = origin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

}