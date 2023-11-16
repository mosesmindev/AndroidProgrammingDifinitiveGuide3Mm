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
}
