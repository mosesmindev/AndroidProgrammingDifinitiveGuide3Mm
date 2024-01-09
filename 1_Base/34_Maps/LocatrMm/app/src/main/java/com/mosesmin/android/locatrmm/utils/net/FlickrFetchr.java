package com.mosesmin.android.locatrmm.utils.net;

import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.mosesmin.android.locatrmm.bean.GalleryItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO  网络连接专用类 访问Flickr网站
 *               代码清单25-3 基本网络连接代码（FlickrFetchr.java）
 * @Author: MosesMin
 * @Date: 2023-11-29 21:47:13
 */
public class FlickrFetchr {
    // 代码清单25-7 添加一些常量（FlickrFetchr.java） -- 从 Flickr 获取 JSON 数据  -- start
    private static final String TAG = "FlickrFetchr";
    private static final String API_KEY = "7e72cd002216b4e9072a3cbe1b9d187e"; // Invalid API Key:4f721bgafa75bf6d2cb9af54f937bb70

    // 代码清单27-1 添加URL常量（FlickrFetchr.java） -- start
    private static final String FETCH_RECENTS_METHOD = "flickr.photos.getRecent";
    private static final String SEARCH_METHOD = "flickr.photos.search";
    private static final Uri ENDPOINT = Uri
            .parse("https://api.flickr.com/services/rest/")
            .buildUpon()
            .appendQueryParameter("api_key", API_KEY)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            // 代码清单34-4 添加经纬度查询参数（FlickrFetchr.java） -- 添加,geo
            .appendQueryParameter("extras", "url_s,geo")
            .build();
    // 代码清单27-1 添加URL常量（FlickrFetchr.java） -- end

    // 代码清单25-7 添加一些常量（FlickrFetchr.java） -- 从 Flickr 获取 JSON 数据  -- end
    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = connection.getInputStream();
        try{
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0){
                out.write(buffer,0,bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    /**
     * 代码清单27-4 添加方法用于下载和搜索（FlickrFetchr.java） -- 1下载
     * @return
     */
    public List<GalleryItem> fetchRecentPhotos() {
        String url = buildUrl(FETCH_RECENTS_METHOD, null);
        return downloadGalleryItems(url);
    }

    /**
     * 代码清单27-4 添加方法用于下载和搜索（FlickrFetchr.java） -- 2搜索
     * @param query
     * @return
     */
    public List<GalleryItem> searchPhotos(String query) {
        String url = buildUrl(SEARCH_METHOD, query);
        return downloadGalleryItems(url);
    }

    /**
     * 代码清单33-13 创建 searchPhotos(Location) 方法（FlickrFetchr.java）
     * @param location
     * @return
     */
    public List<GalleryItem> searchPhotos(Location location) {
        String url = buildUrl(location);
        return downloadGalleryItems(url);
    }

    /**
     * 代码清单27-2 重构Flickr代码（FlickrFetchr.java） -- 重命名 fetchItems() 方法为 downloadGalleryItems(String url)
     *
     * 代码清单25-8 添加 fetchItems() 方法（FlickrFetchr.java） -- 从 Flickr 获取 JSON 数据
     * https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=
     7e72cd002216b4e9072a3cbe1b9d187e&format=json&nojsoncallback=1

     代码清单25-13 调用 parseItems(...) 方法（FlickrFetchr.java）
        public void fetchItems()
        public List<GalleryItem> fetchItems()
     */
    private List<GalleryItem> downloadGalleryItems(String url){
        // 代码清单25-13 调用 parseItems(...) 方法（FlickrFetchr.java）
        List<GalleryItem> items = new ArrayList<>();
        try {
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            // 代码清单25-11 解析JSON数据（FlickrFetchr.java）
            JSONObject jsonBody = new JSONObject(jsonString);
            // 代码清单25-13 调用 parseItems(...) 方法（FlickrFetchr.java）
            parseItems(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        // catch -- 代码清单25-11 解析JSON数据（FlickrFetchr.java）
        } catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        // 代码清单25-13 调用 parseItems(...) 方法（FlickrFetchr.java）
        return items;
    }

    /**
     * 代码清单27-3 添加创建URL的辅助方法（FlickrFetchr.java）
     * @param method
     * @param query
     * @return
     */
    private String buildUrl(String method, String query) {
        Uri.Builder uriBuilder = ENDPOINT.buildUpon()
                .appendQueryParameter("method", method);
        if (method.equals(SEARCH_METHOD)) {
            uriBuilder.appendQueryParameter("text", query);
        }
        return uriBuilder.build().toString();
    }

    /**
     * 代码清单33-12 创建 buildUrl(Location) 方法（FlickrFetchr.java）
     * @param location
     * @return
     */
    private String buildUrl(Location location) {
        return ENDPOINT.buildUpon()
                .appendQueryParameter("method", SEARCH_METHOD)
                .appendQueryParameter("lat", "" + location.getLatitude())
                .appendQueryParameter("lon", "" + location.getLongitude())
                .build().toString();
    }

    /**
     * TODO 代码清单25-12 解析Flickr图片（FlickrFetchr.java）
     * @param items
     * @param jsonBody
     * @throws IOException
     * @throws JSONException
     */
    private void parseItems(List<GalleryItem> items, JSONObject jsonBody)
            throws IOException, JSONException {
        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");
        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);
            GalleryItem item = new GalleryItem();
            item.setId(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));
            if (!photoJsonObject.has("url_s")) {
                continue;
            }
            item.setUrl(photoJsonObject.getString("url_s"));
            // 代码清单30-2 从JSON数据中获取 owner 属性（FlickrFetchr.java）
            item.setOwner(photoJsonObject.getString("owner"));

            // 代码清单34-6 取出经纬度值（FlickrFetchr.java） -- start
            item.setLat(photoJsonObject.getDouble("latitude"));
            item.setLon(photoJsonObject.getDouble("longitude"));
            // 代码清单34-6 取出经纬度值（FlickrFetchr.java） -- end
            items.add(item);
        }
    }

}