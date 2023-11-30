package com.mosesmin.android.photogallerymm.net;

import android.net.Uri;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO  网络连接专用类 访问Flickr网站
 * @Author: MosesMin
 * @Date: 2023-11-29 21:47:13
 */
// 代码清单25-3 基本网络连接代码（FlickrFetchr.java）
public class FlickrFetchr {

    // 代码清单25-7 添加一些常量（FlickrFetchr.java） -- 从 Flickr 获取 JSON 数据
    private static final String TAG = "FlickrFetchr";
    private static final String API_KEY = "7e72cd002216b4e9072a3cbe1b9d187e"; // Invalid API Key:4f721bgafa75bf6d2cb9af54f937bb70

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

    // 代码清单25-7 添加一些常量（FlickrFetchr.java） -- 从 Flickr 获取 JSON 数据
    public void fetchItems(){
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
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        }
    }
}