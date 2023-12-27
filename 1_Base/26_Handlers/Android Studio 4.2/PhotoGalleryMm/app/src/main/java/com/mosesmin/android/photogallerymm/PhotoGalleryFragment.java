package com.mosesmin.android.photogallerymm;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mosesmin.android.photogallerymm.bean.GalleryItem;
import com.mosesmin.android.photogallerymm.net.FlickrFetchr;

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

    // 代码清单26-5 创建 ThumbnailDownloader （PhotoGalleryFragment.java） --1
    private ThumbnailDownloader<PhotoHolder> mThumbnailDownloader;

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        // 代码清单25-6 实现 AsyncTask 工具类方法，第二部分（PhotoGalleryFragment.java）
        new FetchItemsTask().execute();

        // 代码清单26-11 使用反馈H andler （PhotoGalleryFragment.java） --1
        Handler responseHandler = new Handler();
        // 代码清单26-5 创建 ThumbnailDownloader （PhotoGalleryFragment.java） -- 2start
        mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);

        // 代码清单26-11 使用反馈H andler （PhotoGalleryFragment.java） --2
        mThumbnailDownloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder>() {
                    @Override
                    public void onThumbnailDownloaded(PhotoHolder photoHolder, Bitmap bitmap) {
                        Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                        photoHolder.bindDrawable(drawable);
                    }
                }
        );
        mThumbnailDownloader.start();
        mThumbnailDownloader.getLooper();
        Log.i(TAG, "Background thread started");
        // 代码清单26-5 创建 ThumbnailDownloader （PhotoGalleryFragment.java） -- 2end
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery,
                container, false);
        mPhotoRecyclerView = v.findViewById(R.id.photo_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        // 代码清单25-16 实现 setupAdapter() 方法（PhotoGalleryFragment.java）
        setupAdapter();
        return v;
    }
    // 代码清单25-2 一些代码片断（PhotoGalleryFragment.java） -- end

    /**
     * 代码清单26-14 调用清理方法（PhotoGalleryFragment.java）
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbnailDownloader.clearQueue();
    }

    // 代码清单26-5 创建 ThumbnailDownloader （PhotoGalleryFragment.java） -- 3start
    @Override
    public void onDestroy() {
        super.onDestroy();
        //mThumbnailDownloader.quit();
        Log.i(TAG, "Background thread destroyed");
    }
    // 代码清单26-5 创建 ThumbnailDownloader （PhotoGalleryFragment.java） -- 3end

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
        // 代码清单26-1 更新 PhotoHolder （PhotoGalleryFragment.java）
        private ImageView mItemImageView;
        public PhotoHolder(View itemView) {
            super(itemView);
            // 代码清单26-1 更新 PhotoHolder （PhotoGalleryFragment.java）
            mItemImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
        }

        /**
         * 代码清单26-1 更新 PhotoHolder （PhotoGalleryFragment.java）
         * @param drawable
         */
        public void bindDrawable(Drawable drawable) {
            mItemImageView.setImageDrawable(drawable);
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
            // 代码清单26-2 更新 PhotoAdapter 的 onCreateViewHolder() 方法（PhotoGalleryFragment.java）
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_gallery,
                    viewGroup, false);
            return new PhotoHolder(view);
        }
        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            // 代码清单26-3 绑定默认图片（PhotoGalleryFragment.java）
            Drawable placeholder = getResources().
                    getDrawable(R.drawable.christians_praying_god_on_mountain);
            photoHolder.bindDrawable(placeholder);

            // 代码清单26-6 让 ThumbnailDownloader 跑起来（PhotoGalleryFragment.java）
            mThumbnailDownloader.queueThumbnail(photoHolder,galleryItem.getUrl());
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
            // 代码清单25-17 添加adapter更新代码（PhotoGalleryFragment.java） -- start

            // 代码清单25-9 调用 fetchItems() 方法（PhotoGalleryFragment.java） -- start
            /*try {
                String result = new FlickrFetchr()
                        .getUrlString("https://www.baidu.com");
                Log.i(TAG, "Fetched contents of URL: " + result);
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }*/
            // new FlickrFetchr().fetchItems();
            // 代码清单25-9 调用 fetchItems() 方法（PhotoGalleryFragment.java） -- end

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

