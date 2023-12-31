package com.mosesmin.android.criminalintentmm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-10-30 21:32:45
 */
// 代码清单8-7 创建一个Activity抽象类（SingleFragmentActivity.java） -- start
public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static final String TAG = "SingleFragmentActivity";
    // 代码清单8-8 添加一个通用超类（SingleFragmentActivity.java） -- start
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        Log.d(TAG, "onCreate: fragment = " + fragment); // output: null
        if (fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
        // 代码清单8-8 添加一个通用超类（SingleFragmentActivity.java） -- end
    }
}
// 代码清单8-7 创建一个Activity抽象类（SingleFragmentActivity.java） -- end