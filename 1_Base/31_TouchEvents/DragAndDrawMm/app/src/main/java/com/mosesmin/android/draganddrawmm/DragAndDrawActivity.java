package com.mosesmin.android.draganddrawmm;

import android.support.v4.app.Fragment;
import android.os.Bundle;

/**
 * 代码清单31-1 修改activity（DragAndDrawActivity.java）
 */
public class DragAndDrawActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return DragAndDrawFragment.newInstance();
    }


}