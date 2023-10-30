package com.mosesmin.android.criminalintentmm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// 代码清单8-9 清理CrimeActivity类（CrimeActivity.java） -- start
public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
// 代码清单8-9 清理CrimeActivity类（CrimeActivity.java） -- end