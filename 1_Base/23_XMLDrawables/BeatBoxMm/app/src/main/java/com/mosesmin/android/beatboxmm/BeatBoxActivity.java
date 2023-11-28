package com.mosesmin.android.beatboxmm;

import android.support.v4.app.Fragment;

// 代码清单20-3 实现 BeatBoxActivity （BeatBoxActivity.java）
public class BeatBoxActivity extends SingleFragmentActivity {

    // 代码清单20-3 实现 BeatBoxActivity （BeatBoxActivity.java）
    @Override
    protected Fragment createFragment() {
        return BeatBoxFragment.newInstance();
    }
}