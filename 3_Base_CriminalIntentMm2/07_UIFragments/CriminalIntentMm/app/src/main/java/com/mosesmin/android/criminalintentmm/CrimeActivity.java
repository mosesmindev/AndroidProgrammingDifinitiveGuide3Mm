package com.mosesmin.android.criminalintentmm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CrimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        // 代码清单7-15 获取FragmentManager（CrimeActivity.java）
        FragmentManager fm = getSupportFragmentManager();

        // 代码清单7-16 添加一个CrimeFragment（CrimeActivity.java） -- start
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null){
            fragment = new CrimeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
        // 代码清单7-16 添加一个CrimeFragment（CrimeActivity.java） -- end
    }
}