package com.mosesmin.android.criminalintentmm;

import android.content.Intent;
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
public class CrimeListActivity extends SingleFragmentActivity
    // 代码清单17-7 实现回调接口（CrimeListActivity.java） -- start1
    implements CrimeListFragment.Callbacks,
    // 代码清单17-7 实现回调接口（CrimeListActivity.java） -- end1

    // 代码清单17-12 刷新显示crime列表（CrimeListActivity.java） -- start1
    CrimeFragment.Callbacks {
    // 代码清单17-12 刷新显示crime列表（CrimeListActivity.java） -- end1

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

    // 代码清单17-7 实现回调接口（CrimeListActivity.java） -- start2
    @Override
    public void onCrimeSelected(Crime crime) {
        // 代码清单17-8 有条件的 CrimeFragment 启动（CrimeListActivity.java） -- start
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
        // 代码清单17-8 有条件的 CrimeFragment 启动（CrimeListActivity.java） -- end
    }
    // 代码清单17-7 实现回调接口（CrimeListActivity.java） -- end2

    // 代码清单17-2 使用双版面布局（CrimeListActivity.java） -- end

    // 代码清单17-12 刷新显示crime列表（CrimeListActivity.java） -- start2
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
    // 代码清单17-12 刷新显示crime列表（CrimeListActivity.java） -- end2
}
