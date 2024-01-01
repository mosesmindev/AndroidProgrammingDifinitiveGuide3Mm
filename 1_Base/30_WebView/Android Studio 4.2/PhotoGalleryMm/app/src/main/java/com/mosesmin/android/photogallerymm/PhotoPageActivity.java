package com.mosesmin.android.photogallerymm;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO  代码清单30-6 创建显示网页的activity（PhotoPageActivity.java）
 * @Author: MosesMin
 * @Date: 2023-12-31 22:09:54
 */
public class PhotoPageActivity extends SingleFragmentActivity {
    private static final String TAG = "PhotoPageActivity";

    public static Intent newIntent(Context context, Uri photoPageUri) {
        Intent i = new Intent(context, PhotoPageActivity.class);
        i.setData(photoPageUri);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        return PhotoPageFragment.newInstance(getIntent().getData());
    }
}