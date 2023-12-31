package com.mosesmin.android.photogallerymm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.mosesmin.android.photogallerymm.bean.GalleryItem;
import com.mosesmin.android.photogallerymm.utils.background.service.PollService;
import com.mosesmin.android.photogallerymm.utils.databasesharedpreferences.QueryPreferences;
import com.mosesmin.android.photogallerymm.utils.net.FlickrFetchr;
import com.mosesmin.android.photogallerymm.utils.background.thread.ThumbnailDownloader;

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
// 代码清单29-8 设置fragment为可见（PhotoGalleryFragment.java） -- 将继承Fragment修改为继承VisibleFragment
public class PhotoGalleryFragment extends VisibleFragment {
    // 代码清单25-5 实现 AsyncTask 工具类方法，第一部分（PhotoGalleryFragment.java）
    private static final String TAG = "PhotoGalleryFragment";

    // 代码清单25-2 一些代码片断（PhotoGalleryFragment.java） -- start
    private RecyclerView mPhotoRecyclerView;
    // 代码清单25-16 实现 setupAdapter() 方法（PhotoGalleryFragment.java）
    private List<GalleryItem> mItems = new ArrayList<>();

    // 代码清单26-5 创建 ThumbnailDownloader （PhotoGalleryFragment.java） --1
    /*
    ThumbnailDownloader 的泛型参数支持任何对象，但在这里， PhotoHolder 最合适，因为该
    视图是最终显示下载图片的地方。
     */
    private ThumbnailDownloader<PhotoHolder> mThumbnailDownloader;

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        // 代码清单27-8 覆盖 onCreateOptionsMenu(...) 方法（PhotoGalleryFragment.java） --1
        setHasOptionsMenu(true);

        // 代码清单27-10 使用 updateItems() 封装方法（PhotoGalleryFragment.java） -- start
        // 代码清单25-6 实现 AsyncTask 工具类方法，第二部分（PhotoGalleryFragment.java）
        //new FetchItemsTask().execute();
        updateItems();
        // 代码清单27-10 使用 updateItems() 封装方法（PhotoGalleryFragment.java） -- end

        // 代码清单28-9 添加定时器启动代码 （PhotoGalleryFragment.java） -- start
        // 代码清单28-3 添加服务启动代码（PhotoGalleryFragment.java） -- start
        //Intent i = PollService.newIntent(getActivity());
        //getActivity().startService(i);
        // 代码清单28-3 添加服务启动代码（PhotoGalleryFragment.java） -- end
        // PollService.setServiceAlarm(getActivity(), true);
        // 代码清单28-9 添加定时器启动代码 （PhotoGalleryFragment.java） -- end

        // 代码清单26-11 使用反馈Handler （PhotoGalleryFragment.java） --1
        Handler responseHandler = new Handler();
        // 代码清单26-5 创建 ThumbnailDownloader （PhotoGalleryFragment.java） -- 2start
        mThumbnailDownloader = new ThumbnailDownloader<>(responseHandler);

        // 代码清单26-11 使用反馈Handler （PhotoGalleryFragment.java） --2
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
     * 代码清单27-8 覆盖 onCreateOptionsMenu(...) 方法（PhotoGalleryFragment.java） -- 2
     * @param menu
     * @param menuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_photo_gallery, menu);

        // 代码清单27-9 日志记录 SearchView.OnQueryTextListener 事件（PhotoGalleryFragment.java） --1start
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "QueryTextSubmit: " + s);
                // 代码清单27-12 存储用户提交的查询信息（PhotoGalleryFragment.java）
                QueryPreferences.setStoredQuery(getActivity(), s);
                updateItems();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(TAG, "QueryTextChange: " + s);
                return false;
            }
        });
        // 代码清单27-9 日志记录 SearchView.OnQueryTextListener 事件（PhotoGalleryFragment.java） --1end

        // 代码清单27-15 默认显示已保存查询信息（PhotoGalleryFragment.java） -- start
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = QueryPreferences.getStoredQuery(getActivity());
                searchView.setQuery(query, false);
            }
        });
        // 代码清单27-15 默认显示已保存查询信息（PhotoGalleryFragment.java） -- end

        /*
         代码清单28-14 菜单项切换（PhotoGalleryFragment.java）
         -- 检查定时器的开关状态，然后相应地更新menu_item_toggle_polling
         的标题文字，反馈正确的信息给用户
          -- start
         */

        MenuItem toggleItem = menu.findItem(R.id.menu_item_toggle_polling);
        if (PollService.isServiceAlarmOn(getActivity())) {
            toggleItem.setTitle(R.string.stop_polling);
        } else {
            toggleItem.setTitle(R.string.start_polling);
        }

        /*
         代码清单28-14 菜单项切换（PhotoGalleryFragment.java）
         -- 检查定时器的开关状态，然后相应地更新menu_item_toggle_polling
         的标题文字，反馈正确的信息给用户
          -- end
         */
    }

    /**
     * 代码清单27-13 清除查询信息（PhotoGalleryFragment.java）
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                QueryPreferences.setStoredQuery(getActivity(), null);
                updateItems();
                return true;
            // 代码清单28-13 菜单项切换实现（PhotoGalleryFragment.java） -- start
            case R.id.menu_item_toggle_polling:
                boolean shouldStartAlarm = !PollService.isServiceAlarmOn(getActivity());
                PollService.setServiceAlarm(getActivity(), shouldStartAlarm);
                /*
                代码清单28-15 让选项菜单失效（PhotoGalleryFragment.java）
                接着，在onOptionsItemSelected(MenuItem) 方法中，
                让 PhotoGalleryActivity 刷新工具栏选项菜单，以让菜单栏失效
                 */
                getActivity().invalidateOptionsMenu();
                return true;
            // 代码清单28-13 菜单项切换实现（PhotoGalleryFragment.java） -- end
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 代码清单27-9 日志记录 SearchView.OnQueryTextListener 事件（PhotoGalleryFragment.java） --2
     */
    private void updateItems() {
        // 代码清单27-14 在 FetchItemsTask 中使用保存的查询信息（PhotoGalleryFragment.java） --1
        String query = QueryPreferences.getStoredQuery(getActivity());
        new FetchItemsTask(query).execute();
    }

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
        // 代码清单27-14 在 FetchItemsTask 中使用保存的查询信息（PhotoGalleryFragment.java） --2start
        private String mQuery;

        public FetchItemsTask(String query) {
            mQuery = query;
        }
        // 代码清单27-14 在 FetchItemsTask 中使用保存的查询信息（PhotoGalleryFragment.java） --2end

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
            // 代码清单27-5 硬编码的搜索字符串（PhotoGalleryFragment.java） -- start
            // return new FlickrFetchr().fetchItems();
            // 代码清单25-17 添加adapter更新代码（PhotoGalleryFragment.java） -- end
            // String query = "robot"; // Just for testing
            if (mQuery == null) {
                return new FlickrFetchr().fetchRecentPhotos();
            } else {
                return new FlickrFetchr().searchPhotos(mQuery);
            }

            // 代码清单27-5 硬编码的搜索字符串（PhotoGalleryFragment.java） -- end
        }

        /**
         * 代码清单25-17 添加adapter更新代码（PhotoGalleryFragment.java）
         * @param items The result of the operation computed by {@link #doInBackground}.
         */
        @Override
        protected void onPostExecute(List<GalleryItem> items) {
            mItems = items;
            setupAdapter();
        }
    }


}

