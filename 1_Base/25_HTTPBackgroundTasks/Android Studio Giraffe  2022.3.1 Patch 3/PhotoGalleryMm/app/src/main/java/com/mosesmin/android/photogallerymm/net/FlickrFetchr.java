package com.mosesmin.android.photogallerymm.net;

import android.net.Uri;
import android.util.Log;

import com.mosesmin.android.photogallerymm.bean.GalleryItem;

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
     * 代码清单25-8 添加 fetchItems() 方法（FlickrFetchr.java） -- 从 Flickr 获取 JSON 数据
     * https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=
     7e72cd002216b4e9072a3cbe1b9d187e&format=json&nojsoncallback=1

     代码清单25-13 调用 parseItems(...) 方法（FlickrFetchr.java）
        public void fetchItems()
        public List<GalleryItem> fetchItems()
     */
    public List<GalleryItem> fetchItems(){
        // 代码清单25-13 调用 parseItems(...) 方法（FlickrFetchr.java）
        List<GalleryItem> items = new ArrayList<>();
        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
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
            items.add(item);
        }
    }
}