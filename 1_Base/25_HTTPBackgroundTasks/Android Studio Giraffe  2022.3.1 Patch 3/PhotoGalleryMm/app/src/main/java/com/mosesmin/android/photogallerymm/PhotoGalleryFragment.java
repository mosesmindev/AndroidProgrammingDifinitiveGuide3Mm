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
import android.widget.TextView;

import com.mosesmin.android.photogallerymm.bean.GalleryItem;
import com.mosesmin.android.photogallerymm.net.FlickrFetchr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO  代码清单25-2 一些代码片断（PhotoGalleryFragment.java）
 * @Author: MosesMin
 * @Date: 2023-11-29 21:44:12
 */
public class PhotoGalleryFragment extends Fragment {
    // 代码清单25-5 实现 AsyncTask 工具类方法，第一部分（PhotoGalleryFragment.java）
    private static final String TAG = "PhotoGalleryFragment";

    // 代码清单25-2 一些代码片断（PhotoGalleryFragment.java） -- start
    private RecyclerView mPhotoRecyclerView;
    // 代码清单25-16 实现 setupAdapter() 方法（PhotoGalleryFragment.java）
    private List<GalleryItem> mItems = new ArrayList<>();
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
        // 代码清单25-16 实现 setupAdapter() 方法（PhotoGalleryFragment.java）
        setupAdapter();
        return v;
    }
    // 代码清单25-2 一些代码片断（PhotoGalleryFragment.java） -- end

    /**
     * 代码清单25-16 实现 setupAdapter() 方法（PhotoGalleryFragment.java）
     */
    private void setupAdapter() {
        if (isAdded()) {
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
        }
    }

    /**
     * 代码清单25-14 添加 ViewHolder 实现（PhotoGalleryFragment.java）
     */
    private class PhotoHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        public PhotoHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView;
        }
        public void bindGalleryItem(GalleryItem item) {
            mTitleTextView.setText(item.toString());
        }
    }

    /**
     * 代码清单25-15 添加 RecyclerView.Adapter 实现（PhotoGalleryFragment.java）
     */
    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private List<GalleryItem> mGalleryItems;
        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }
        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            TextView textView = new TextView(getActivity());
            return new PhotoHolder(textView);
        }
        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            photoHolder.bindGalleryItem(galleryItem);
        }
        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    /**
     * 代码清单25-5 实现 AsyncTask 工具类方法，第一部分（PhotoGalleryFragment.java）
     * 注意 参数类型和返回值类型 大写V的Void
     * TODO 使用 AsyncTask 在后台线程上运行代码
     * 调用并测试新添加的网络连接代码。注意，不要直接在 PhotoGallery-
     * Fragment 类中调用 FlickrFetchr.getURLString(String) 方法。正确的做法是，创建一个后
     * 台线程，然后在该线程中运行代码。
     *
     *  代码清单25-17 添加adapter更新代码（PhotoGalleryFragment.java）
            AsyncTask<Void,Void,Void>
            AsyncTask<Void,Void,List<GalleryItem>>
     */
    private class FetchItemsTask extends AsyncTask<Void,Void,List<GalleryItem>>{
        // 代码清单25-17 添加adapter更新代码（PhotoGalleryFragment.java）
        //   protected Void doInBackground(Void... params)
        //   protected List<GalleryItem> doInBackground(Void... params)
        @Override
        protected List<GalleryItem> doInBackground(Void... params) {
            // 代码清单25-9 调用 fetchItems() 方法（PhotoGalleryFragment.java） -- start
            /*try {
                String result = new FlickrFetchr()
                        .getUrlString("https://www.baidu.com");
                Log.i(TAG, "Fetched contents of URL: " + result);
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }*/
            new FlickrFetchr().fetchItems();
            // 代码清单25-9 调用 fetchItems() 方法（PhotoGalleryFragment.java） -- end

            // 代码清单25-17 添加adapter更新代码（PhotoGalleryFragment.java） -- start
            // return null;
            return new FlickrFetchr().fetchItems();
            // 代码清单25-17 添加adapter更新代码（PhotoGalleryFragment.java） -- end
        }

        /**
         *  代码清单25-17 添加adapter更新代码（PhotoGalleryFragment.java）
         * @param items The result of the operation computed by {@link #doInBackground}.
         */
        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            setupAdapter();
        }
    }

}