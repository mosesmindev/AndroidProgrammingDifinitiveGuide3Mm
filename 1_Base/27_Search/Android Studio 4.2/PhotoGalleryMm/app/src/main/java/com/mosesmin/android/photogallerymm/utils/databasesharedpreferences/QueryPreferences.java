package com.mosesmin.android.photogallerymm.utils.databasesharedpreferences;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO  代码清单27-11 管理保存的查询字符串（QueryPreferences.java） -- 用于读取和写入查询字符串
 * @Author: MosesMin
 * @Date: 2023-12-27 22:43:13
 */
public class QueryPreferences {
    private static final String TAG = "QueryPreferences";

    private static final String PREF_SEARCH_QUERY = "searchQuery";
    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null);
    }
    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }

}