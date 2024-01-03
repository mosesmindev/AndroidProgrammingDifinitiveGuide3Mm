package com.mosesmin.android.draganddrawmm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:DragAndDrawMm
 * @Description: TODO 代码清单31-3 初始的 BoxDrawingView 视图类（BoxDrawingView.java）
 * @Author: MosesMin
 * @Date: 2024-01-03 22:32:27
 */
public class BoxDrawingView extends View{
    private static final String TAG = "BoxDrawingView";

    // 代码清单31-7 添加拖曳生命周期方法（BoxDrawingView.java） -- 1start
    private Box mCurrentBox;
    private List<Box> mBoxen = new ArrayList<>();
    // 代码清单31-7 添加拖曳生命周期方法（BoxDrawingView.java） -- 1end

    // 代码清单31-8 创建Paint（BoxDrawingView.java） -- 1start
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;
    // 代码清单31-8 创建Paint（BoxDrawingView.java） -- 1end

    // Used when creating the view in code
    public BoxDrawingView(Context context) {
        this(context, null);
    }
    // Used when inflating the view from XML
    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 代码清单31-8 创建Paint（BoxDrawingView.java） -- 2start
        // Paint the boxes a nice semitransparent red (ARGB)
        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);
        // Paint the background off-white
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
        // 代码清单31-8 创建Paint（BoxDrawingView.java） -- 2end
    }

    /**
     * 代码清单31-9 覆盖 onDraw(Canvas) 方法（BoxDrawingView.java）
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // Fill the background
        canvas.drawPaint(mBackgroundPaint);
        for (Box box : mBoxen) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);
            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }

    // 代码清单31-5 实现 BoxDrawingView 视图类（BoxDrawingView.java） -- start
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                // 代码清单31-7 添加拖曳生命周期方法（BoxDrawingView.java） -- 2start
                // Reset drawing state
                mCurrentBox = new Box(current);
                mBoxen.add(mCurrentBox);
                // 代码清单31-7 添加拖曳生命周期方法（BoxDrawingView.java） -- 2end
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                // 代码清单31-7 添加拖曳生命周期方法（BoxDrawingView.java） -- 3start
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(current);
                    invalidate();
                }
                // 代码清单31-7 添加拖曳生命周期方法（BoxDrawingView.java） -- 3end
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                // 代码清单31-7 添加拖曳生命周期方法（BoxDrawingView.java） -- 4
                mCurrentBox = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                // 代码清单31-7 添加拖曳生命周期方法（BoxDrawingView.java） -- 5
                mCurrentBox = null;
                break;
        }
        Log.i(TAG, action + " at x=" + current.x +
                ", y=" + current.y);
        return true;
    }
    // 代码清单31-5 实现 BoxDrawingView 视图类（BoxDrawingView.java） -- end
}
