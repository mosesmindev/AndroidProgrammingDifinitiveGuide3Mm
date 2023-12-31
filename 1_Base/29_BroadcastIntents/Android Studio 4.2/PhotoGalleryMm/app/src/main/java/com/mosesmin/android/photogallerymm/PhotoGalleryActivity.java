package com.mosesmin.android.photogallerymm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * 代码清单25-1 activity的调整（PhotoGalleryActivity.java）
 */
public class PhotoGalleryActivity extends SingleFragmentActivity {
    /**
     * 代码清单28-16 添加 newIntent(...) 静态方法（PhotoGalleryActivity.java）
     * 添加一个 newIntent(...) 静态方法，该静态方法会返回一个可用来启动 PhotoGalleryActivity 的 Intent 实例。
     * @param context
     * @return
     */
    public static Intent newIntent(Context context) {
        return new Intent(context, PhotoGalleryActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }
}