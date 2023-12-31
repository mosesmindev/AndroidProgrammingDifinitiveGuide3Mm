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

    // 代码清单28-6 添加存储图片ID的 preference 常量（QueryPreferences.java） -- 1
    private static final String PREF_LAST_RESULT_ID = "lastResultId";

    // 代码清单29-3 添加定时器状态preference（QueryPreferences.java） -- start
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";

    public static boolean isAlarmOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_IS_ALARM_ON, false);
    }
    public static void setAlarmOn(Context context, boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_ALARM_ON, isOn)
                .apply();

    }
    // 代码清单29-3 添加定时器状态preference（QueryPreferences.java） -- end

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

    /**
     * 代码清单28-6 添加存储图片ID的 preference 常量（QueryPreferences.java） -- 2
     * @param context
     * @return
     */
    public static String getLastResultId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LAST_RESULT_ID, null);
    }

    /**
     * 代码清单28-6 添加存储图片ID的 preference 常量（QueryPreferences.java） -- 3
     * @param context
     * @param lastResultId
     */
    public static void setLastResultId(Context context, String lastResultId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_RESULT_ID, lastResultId)
                .apply();
    }

}