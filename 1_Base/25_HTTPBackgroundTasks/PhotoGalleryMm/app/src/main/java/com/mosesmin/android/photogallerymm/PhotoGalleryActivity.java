package com.mosesmin.android.photogallerymm;

import android.support.v4.app.Fragment;

// 代码清单25-1 activity的调整（PhotoGalleryActivity.java）
public class PhotoGalleryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }
}