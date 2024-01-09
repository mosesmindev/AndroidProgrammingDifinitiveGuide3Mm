package com.mosesmin.android.locatrmm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mosesmin.android.locatrmm.bean.GalleryItem;
import com.mosesmin.android.locatrmm.utils.net.FlickrFetchr;

import java.io.IOException;
import java.util.List;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:LocatrMm
 * @Description: TODO 代码清单33-3 创建 LocatrFragment （LocatrFragment.java）
 * @Author: MosesMin
 * @Date: 2024-01-06 22:27:41
 */
// 代码清单34-3 改用 SupportMapFragment （LocatrFragment.java） -- 1由继承fragment 改为继承SupportMapFragment
public class LocatrFragment extends SupportMapFragment {
    private static final String TAG = "LocatrFragment";

    // 代码清单33-17 添加权限常量（LocatrFragment.java） -- start
    private static final String[] LOCATION_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    // 代码清单33-17 添加权限常量（LocatrFragment.java） -- end

    // 代码清单33-20 要求授权（LocatrFragment.java） -- 1
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    // 代码清单33-8 创建 GoogleApiClient （LocatrFragment.java） -- 1
    private GoogleApiClient mClient;

    // 代码清单34-9 获取 GoogleMap （LocatrFragment.java） -- 1
    private GoogleMap mMap;

    // 代码清单34-7 添加地图数据（LocatrFragment.java） -- start
    private Bitmap mMapImage;
    private GalleryItem mMapItem;
    private Location mCurrentLocation;
    // 代码清单34-7 添加地图数据（LocatrFragment.java） -- end

    // 代码清单34-3 改用 SupportMapFragment （LocatrFragment.java） -- 2start
    // private ImageView mImageView;
    // 代码清单34-3 改用 SupportMapFragment （LocatrFragment.java） -- 2end



    public static LocatrFragment newInstance() {
        return new LocatrFragment();
    }

    /**
     * 代码清单33-4 添加菜单（LocatrFragment.java） -- 1
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // 代码清单33-8 创建 GoogleApiClient （LocatrFragment.java） -- 2start
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                // 代码清单33-11 监听连接事件（LocatrFragment.java） -- start
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                    }
                    @Override
                    public void onConnectionSuspended(int i) {
                    }
                })
                // 代码清单33-11 监听连接事件（LocatrFragment.java） -- end
                .build();
        // 代码清单33-8 创建 GoogleApiClient （LocatrFragment.java） -- 2end

        // 代码清单34-9 获取 GoogleMap （LocatrFragment.java） -- 2start
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                // 代码清单34-12 调用 updateUI() 方法（LocatrFragment.java） -- 放大地图 1
                updateUI();
            }
        });
        // 代码清单34-9 获取 GoogleMap （LocatrFragment.java） -- 2end
    }

    // 代码清单34-3 改用 SupportMapFragment （LocatrFragment.java） -- 3start
    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_locatr, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image);
        return v;
    }
    */
    // 代码清单34-3 改用 SupportMapFragment （LocatrFragment.java） -- 3end

    // 代码清单33-9 连接和断开连接（LocatrFragment.java） -- start
    /**
     *代码清单33-9 连接和断开连接（LocatrFragment.java） -- 连接Google Play服务客户端
     */
    @Override
    public void onStart() {
        super.onStart();
        getActivity().invalidateOptionsMenu();
        mClient.connect();
    }

    /**
     * 代码清单33-9 连接和断开连接（LocatrFragment.java） -- 断连Google Play服务客户端
     */
    @Override
    public void onStop() {
        super.onStop();
        mClient.disconnect();
    }
    // 代码清单33-9 连接和断开连接（LocatrFragment.java） -- end

    /**
     * 代码清单33-4 添加菜单（LocatrFragment.java） -- 2
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_locatr, menu);

        // 代码清单33-10 更新菜单按钮状态（LocatrFragment.java） -- start
        MenuItem searchItem = menu.findItem(R.id.action_locate);
        searchItem.setEnabled(mClient.isConnected());
        // 代码清单33-10 更新菜单按钮状态（LocatrFragment.java） -- end
    }

    /**
     * 代码清单33-16 关联使用搜索按钮（LocatrFragment.java）
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_locate:
                // 代码清单33-19 添加权限检查（LocatrFragment.java） -- 增加if判断权限条件
                if (hasLocationPermission()){
                    findImage();
                }
                // 代码清单33-20 要求授权（LocatrFragment.java） -- 2 增加else请求权限部分
                else{
                    requestPermissions(LOCATION_PERMISSIONS,
                            REQUEST_LOCATION_PERMISSIONS);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 代码清单33-21 针对授权结果做出反馈（LocatrFragment.java）
     *      -- 针对33-20 要求授权 请求权限的授权结果做出反馈
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {
                    findImage();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 代码清单33-14 创建定位请求（LocatrFragment.java）
     */
    private void findImage() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        // 代码清单33-15 发送定位请求（LocatrFragment.java） -- start
        LocationServices.FusedLocationApi
                .requestLocationUpdates(mClient, request, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.i(TAG, "Got a fix: " + location);
                        // 代码清单33-22 编写 SearchTask 内部类（LocatrFragment.java） -- 1
                        new SearchTask().execute(location);
                    }
                });
        // 代码清单33-15 发送定位请求（LocatrFragment.java） -- end
    }

    /**
     * 代码清单33-18 权限检查（LocatrFragment.java）
     * @return
     */
    private boolean hasLocationPermission() {
        int result = ContextCompat
                .checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 代码清单34-11 放大（LocatrFragment.java） -- 地图放大
     */
    private void updateUI() {
        if (mMap == null || mMapImage == null) {
            return;
        }
        LatLng itemPoint = new LatLng(mMapItem.getLat(), mMapItem.getLon());
        LatLng myPoint = new LatLng(
                mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

        // 代码清单34-13 地图标注（LocatrFragment.java） -- start
        BitmapDescriptor itemBitmap = BitmapDescriptorFactory.fromBitmap(mMapImage);
        MarkerOptions itemMarker = new MarkerOptions()
                .position(itemPoint)
                .icon(itemBitmap);
        MarkerOptions myMarker = new MarkerOptions()
                .position(myPoint);
        mMap.clear();
        mMap.addMarker(itemMarker);
        mMap.addMarker(myMarker);
        // 代码清单34-13 地图标注（LocatrFragment.java） -- end

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(itemPoint)
                .include(myPoint)
                .build();
        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds, margin);
        mMap.animateCamera(update);
    }

    /**
     * 代码清单33-22 编写 SearchTask 内部类（LocatrFragment.java） -- 2
     */
    private class SearchTask extends AsyncTask<Location,Void,Void> {
        private GalleryItem mGalleryItem;
        // 代码清单33-23 下载并显示图片（LocatrFragment.java） -- 1
        private Bitmap mBitmap;
        // 代码清单34-8 保存查询结果（LocatrFragment.java） --1
        private Location mLocation;

        @Override
        protected Void doInBackground(Location... params) {
            // 代码清单34-8 保存查询结果（LocatrFragment.java） --2
            mLocation = params[0];

            FlickrFetchr fetchr = new FlickrFetchr();
            List<GalleryItem> items = fetchr.searchPhotos(params[0]);
            if (items.size() == 0) {
                return null;
            }
            mGalleryItem = items.get(0);
            // 代码清单33-23 下载并显示图片（LocatrFragment.java） -- 2start
            try {
                byte[] bytes = fetchr.getUrlBytes(mGalleryItem.getUrl());
                mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            } catch (IOException ioe) {
                Log.i(TAG, "Unable to download bitmap", ioe);
            }
            // 代码清单33-23 下载并显示图片（LocatrFragment.java） -- 2end
            return null;
        }

        /**
         * 代码清单33-23 下载并显示图片（LocatrFragment.java） -- 3
         * @param result
         */
        @Override
        protected void onPostExecute(Void result) {
            // 代码清单34-3 改用 SupportMapFragment （LocatrFragment.java） -- 4start
            // mImageView.setImageBitmap(mBitmap);
            // 代码清单34-3 改用 SupportMapFragment （LocatrFragment.java） -- 4end

            // 代码清单34-8 保存查询结果（LocatrFragment.java） -- 3start
            mMapImage = mBitmap;
            mMapItem = mGalleryItem;
            mCurrentLocation = mLocation;
            // 代码清单34-8 保存查询结果（LocatrFragment.java） -- 3end

            // 代码清单34-12 调用 updateUI() 方法（LocatrFragment.java） -- 放大地图 2
            updateUI();
        }
    }

}