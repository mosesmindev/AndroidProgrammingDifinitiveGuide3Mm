package com.mosesmin.android.photogallerymm;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mosesmin.android.photogallerymm.net.FlickrFetchr;

import java.io.IOException;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-29 21:44:12
 */

// 代码清单25-2 一些代码片断（PhotoGalleryFragment.java）
public class PhotoGalleryFragment extends Fragment {
    // 代码清单25-5 实现 AsyncTask 工具类方法，第一部分（PhotoGalleryFragment.java）
    private static final String TAG = "PhotoGalleryFragment";

    // 代码清单25-2 一些代码片断（PhotoGalleryFragment.java） -- start
    private RecyclerView mPhotoRecyclerView;
    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        // 代码清单25-6 实现 AsyncTask 工具类方法，第二部分（PhotoGalleryFragment.java）
        new FetchItemsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        mPhotoRecyclerView = v.findViewById(R.id.photo_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        return v;
    }
    // 代码清单25-2 一些代码片断（PhotoGalleryFragment.java） -- end

    /**
     * 代码清单25-5 实现 AsyncTask 工具类方法，第一部分（PhotoGalleryFragment.java）
     * 注意 参数类型和返回值类型 大写V的Void
     * TODO 使用 AsyncTask 在后台线程上运行代码
     * 调用并测试新添加的网络连接代码。注意，不要直接在 PhotoGallery-
     * Fragment 类中调用 FlickrFetchr.getURLString(String) 方法。正确的做法是，创建一个后
     * 台线程，然后在该线程中运行代码。
     */
    private class FetchItemsTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            // 代码清单25-9 调用 fetchItems() 方法（PhotoGalleryFragment.java）
            /*try {
                String result = new FlickrFetchr()
                        .getUrlString("https://www.baidu.com");
                Log.i(TAG, "Fetched contents of URL: " + result);
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }*/
            new FlickrFetchr().fetchItems();
            return null;
        }
    }

}