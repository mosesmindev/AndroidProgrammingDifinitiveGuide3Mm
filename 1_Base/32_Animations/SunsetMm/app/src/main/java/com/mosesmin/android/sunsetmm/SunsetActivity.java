package com.mosesmin.android.sunsetmm;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// 代码清单32-5 创建 SunsetFragment 类（SunsetActivity.java） -- 1由继承AppCompatActivity修改为继承SingleFragmentActivity
public class SunsetActivity extends SingleFragmentActivity {

    /**
     * 代码清单32-5 创建 SunsetFragment 类（SunsetActivity.java） --2创建SunsetFragment
     * @return
     */
    @Override
    protected Fragment createFragment() {
        return SunsetFragment.newInstance();
    }

}