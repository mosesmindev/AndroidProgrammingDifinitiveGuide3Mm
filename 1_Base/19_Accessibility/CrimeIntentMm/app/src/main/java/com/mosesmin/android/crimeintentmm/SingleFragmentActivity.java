package com.mosesmin.android.crimeintentmm;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 *
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName SingleFragmentActivity
 * @Description TODO 封装的单例FragmentActivity
 * @Author MosesMin
 * @Date 2023-08-28 22:28:01
 * @Version 1.0
 *
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    private static final String TAG = "SingleFragmentActivity";

    protected abstract Fragment createFragment();

    // 17-1 增加SingleFragmentActivity类的灵活性
    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 17-1 增加SingleFragmentActivity类的灵活性
        setContentView(getLayoutResId());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }
}