package com.mosesmin.android.criminalintentmm;

import android.support.v4.app.Fragment;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-10-30 22:01:25
 */

// 代码清单8-10 实现CrimeListActivity （CrimeListActivity.java） -- start
public class CrimeListActivity extends SingleFragmentActivity{
    private static final String TAG = "CrimeListActivity";

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
    // 代码清单8-10 实现CrimeListActivity （CrimeListActivity.java） -- end

    // 代码清单17-2 使用双版面布局（CrimeListActivity.java） -- start
    @Override
    protected int getLayoutResId() {
        // 代码清单17-4 再次切换布局（CrimeListActivity.java） -- start
        // return R.layout.activity_twopane;
        return R.layout.activity_masterdetail;
        // 代码清单17-4 再次切换布局（CrimeListActivity.java） -- end
    }
    // 代码清单17-2 使用双版面布局（CrimeListActivity.java） -- end
}
