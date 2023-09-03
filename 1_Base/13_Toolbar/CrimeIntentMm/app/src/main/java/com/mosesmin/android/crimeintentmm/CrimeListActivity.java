package com.mosesmin.android.crimeintentmm;

import android.support.v4.app.Fragment;

/**
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName CrimeListActivity
 * @Description TODO
 * @Author MosesMin
 * @Date 2023-08-28 22:31:17
 * @Version 1.0
 */
public class CrimeListActivity extends SingleFragmentActivity {
    private static final String TAG = "CrimeListActivity";

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}