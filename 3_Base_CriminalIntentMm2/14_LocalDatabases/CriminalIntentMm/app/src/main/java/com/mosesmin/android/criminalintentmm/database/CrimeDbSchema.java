package com.mosesmin.android.criminalintentmm.database;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-20 20:30:31
 */
public class CrimeDbSchema {
    private static final String TAG = "CrimeDbSchema";

    // 代码清单14-1 定义 CrimeTable 内部类（CrimeDbSchema.java） -- start
    public static final class CrimeTable {
        public static final String NAME = "crimes";

        // 代码清单14-2 定义数据表字段（CrimeDbSchema.java） -- start
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
        // 代码清单14-2 定义数据表字段（CrimeDbSchema.java） -- end
    }
    // 代码清单14-1 定义 CrimeTable 内部类（CrimeDbSchema.java） -- end
}