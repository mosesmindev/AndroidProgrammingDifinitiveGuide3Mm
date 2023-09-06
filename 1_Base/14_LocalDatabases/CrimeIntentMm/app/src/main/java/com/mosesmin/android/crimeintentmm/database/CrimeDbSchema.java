package com.mosesmin.android.crimeintentmm.database;

/**
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName CrimeDbSchema
 * @Description TODO
 * @Author MosesMin
 * @Date 2023-09-06 00:03:17
 * @Version 1.0
 */

// 14.1 定义schema
public class CrimeDbSchema {
    private static final String TAG = "CrimeDbSchema";

    // 14.1 定义schema  14-1 定义CrimeTable内部类
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        // 14.1 定义schema  14-2 定义数据表字段
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}