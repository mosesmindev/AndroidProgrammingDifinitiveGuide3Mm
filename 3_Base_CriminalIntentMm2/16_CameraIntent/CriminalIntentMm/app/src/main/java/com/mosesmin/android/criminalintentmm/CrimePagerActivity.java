package com.mosesmin.android.criminalintentmm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-15 22:02:44
 */
public class CrimePagerActivity extends AppCompatActivity {
    private static final String TAG = "CrimePagerActivity";
    // 代码清单11-3 创建 newIntent 方法（CrimePagerActivity.java） -- start1
    public static final String EXTRA_CRIME_ID = "com.mosesmin.android.criminalintentmm.crime_id";
    // 代码清单11-3 创建 newIntent 方法（CrimePagerActivity.java） -- end1

    // 代码清单11-2 设置pager adapter（CrimePagerActivity.java） -- start1
    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    // 代码清单11-2 设置pager adapter（CrimePagerActivity.java） -- end1

    // 代码清单11-3 创建 newIntent 方法（CrimePagerActivity.java） -- start2
    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext,CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }
    // 代码清单11-3 创建 newIntent 方法（CrimePagerActivity.java） -- end2

    // 代码清单11-1 创建 ViewPager （CrimePagerActivity.java） -- start
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        // 代码清单11-3 创建 newIntent 方法（CrimePagerActivity.java） -- start3
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        // 代码清单11-3 创建 newIntent 方法（CrimePagerActivity.java） -- end3

        // 代码清单11-2 设置pager adapter（CrimePagerActivity.java） -- start2
        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);
        mCrimes = CrimeLab.getInstance(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });
        // 代码清单11-2 设置pager adapter（CrimePagerActivity.java） -- end2

        // 代码清单11-6 设置初始分页显示项（CrimePagerActivity.java）-- start
        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        // 代码清单11-6 设置初始分页显示项（CrimePagerActivity.java）-- end
    }
    // 代码清单11-1 创建 ViewPager （CrimePagerActivity.java） -- end
}