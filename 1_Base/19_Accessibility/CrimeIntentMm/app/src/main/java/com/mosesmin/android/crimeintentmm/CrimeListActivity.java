package com.mosesmin.android.crimeintentmm;

import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 *
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName CrimeListActivity
 * @Description TODO
 * @Author MosesMin
 * @Date 2023-08-28 22:31:17
 * @Version 1.0
 *
 */
// 17-7 实现回调接口
// 17-12 刷新显示crime列表
public class CrimeListActivity extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks{
    private static final String TAG = "CrimeListActivity";

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    // 17-2 使用双版面布局
    @Override
    protected int getLayoutResId() {
        // 17-4 再次切换布局
        return R.layout.activity_masterdetail;
    }

    // 17-7 实现回调接口
    @Override
    public void onCrimeSelected(Crime crime) {
        // 17-8 有条件的CrimeFragment启动
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    // 17-12 刷新显示crime列表
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
}