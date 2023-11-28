package com.mosesmin.android.nerdlaunchermm;

// 代码清单24-1 另一个 SingleFragmentActivity （NerdLauncherActivity.java）
public class NerdLauncherActivity extends SingleFragmentActivity {

    @Override
    protected NerdLauncherFragment createFragment() {
        return NerdLauncherFragment.newInstance();
    }

}