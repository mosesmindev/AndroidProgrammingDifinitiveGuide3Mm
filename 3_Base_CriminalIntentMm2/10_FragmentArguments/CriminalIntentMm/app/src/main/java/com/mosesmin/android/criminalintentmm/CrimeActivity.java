package com.mosesmin.android.criminalintentmm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

// 代码清单8-9 清理CrimeActivity类（CrimeActivity.java） -- start
public class CrimeActivity extends SingleFragmentActivity {
            // 代码清单10-7 使用 newInstance(UUID) 方法（CrimeActivity.java） -- start1
    /*
    // 代码清单10-2 创建 newIntent 方法（CrimeActivity.java） -- start
    public static final String EXTRA_CRIME_ID =
            "com.mosesmin.android.criminalintentmm.crime_id";
     */
    private static final String EXTRA_CRIME_ID =
            "com.mosesmin.android.criminalintentmm.crime_id";
            // 代码清单10-7 使用 newInstance(UUID) 方法（CrimeActivity.java） -- end1

    public static Intent newIntent(Context packageContext , UUID crimeId){
        Intent intent = new Intent(packageContext,CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }
    // 代码清单10-2 创建 newIntent 方法（CrimeActivity.java） -- end

    @Override
    protected Fragment createFragment() {
        // 代码清单10-7 使用 newInstance(UUID) 方法（CrimeActivity.java） -- start2
        //return new CrimeFragment();
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
        // 代码清单10-7 使用 newInstance(UUID) 方法（CrimeActivity.java） -- end2
    }
}
// 代码清单8-9 清理CrimeActivity类（CrimeActivity.java） -- end