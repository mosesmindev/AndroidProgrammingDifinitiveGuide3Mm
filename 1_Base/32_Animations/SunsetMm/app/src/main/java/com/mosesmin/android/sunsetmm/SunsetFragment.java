package com.mosesmin.android.sunsetmm;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:SunsetMm
 * @Description: TODO 代码清单32-4 创建 SunsetFragment 类（SunsetFragment.java）
 * @Author: MosesMin
 * @Date: 2024-01-03 22:52:16
 */

public class SunsetFragment extends Fragment {
    private static final String TAG = "SunsetFragment";
    // 代码清单32-6 获取视图引用（SunsetFragment.java） -- 1start
    private View mSceneView;
    private View mSunView;
    private View mSkyView;
    // 代码清单32-6 获取视图引用（SunsetFragment.java） -- 1end

    // 代码清单32-11 取出日落色彩资源（SunsetFragment.java） -- 1start
    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;
    // 代码清单32-11 取出日落色彩资源（SunsetFragment.java） -- 1end

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);
        // 代码清单32-6 获取视图引用（SunsetFragment.java） -- 2start
        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);
        // 代码清单32-6 获取视图引用（SunsetFragment.java） -- 2end

        // 代码清单32-11 取出日落色彩资源（SunsetFragment.java） -- 2start
        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);
        // 代码清单32-11 取出日落色彩资源（SunsetFragment.java） -- 2end

        // 代码清单32-9 响应触摸，执行动画（SunsetFragment.java） -- start
        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
        // 代码清单32-9 响应触摸，执行动画（SunsetFragment.java） -- end
        return view;
    }

    /**
     * 代码清单32-7 获取视图的顶部坐标位置（SunsetFragment.java）
     */
    private void startAnimation() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

        // 代码清单32-8 创建模拟太阳的animator对象（SunsetFragment.java） -- start
        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd)
                .setDuration(3200);
        // 代码清单32-10 添加加速特效（SunsetFragment.java） -- start
        heightAnimator.setInterpolator(new AccelerateInterpolator());
        // 代码清单32-10 添加加速特效（SunsetFragment.java） -- end

        // 代码清单32-12 实现天空的色彩变换（SunsetFragment.java） -- 1start
        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        // 代码清单32-13 使用 ArgbEvaluator （SunsetFragment.java） -- start
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());
        // 代码清单32-13 使用 ArgbEvaluator （SunsetFragment.java） -- end

        // 代码清单32-12 实现天空的色彩变换（SunsetFragment.java） -- 1end

        // 代码清单32-14 创建夜空动画（SunsetFragment.java） -- start
        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());
        /*
        heightAnimator.start();
        // 代码清单32-8 创建模拟太阳的animator对象（SunsetFragment.java） -- end

        // 代码清单32-12 实现天空的色彩变换（SunsetFragment.java） -- 2
        sunsetSkyAnimator.start();
        */
        // 代码清单32-14 创建夜空动画（SunsetFragment.java） -- end

        // 代码清单32-15 创建动画集（SunsetFragment.java） -- start
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator);
        animatorSet.start();
        // 代码清单32-15 创建动画集（SunsetFragment.java） -- end
    }
}