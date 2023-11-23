package com.mosesmin.android.criminalintentmm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mosesmin.android.criminalintentmm.database.CrimeDbSchema.CrimeTable;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-20 20:32:22
 */
                            // 代码清单14-3 创建 CrimeBaseHelper 类（CrimeBaseHelper.java） -- start
public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "CrimeBaseHelper";

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 代码清单14-5 编写SQL创建初始代码（CrimeBaseHelper.java） -- start
                                                    // 代码清单14-6 创建crime数据表
                                                    // （CrimeBaseHelper.java） -- start
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        CrimeTable.Cols.UUID + ", " +
                        CrimeTable.Cols.TITLE + ", " +
                        CrimeTable.Cols.DATE + ", " +
                        // 代码清单15-4 添加嫌疑人数据库字段（CrimeBaseHelper.java） -- start
                        CrimeTable.Cols.SOLVED + ", " +
                        CrimeTable.Cols.SUSPECT +
                        // 代码清单15-4 添加嫌疑人数据库字段（CrimeBaseHelper.java） -- end
                        ")"
        );
        // 代码清单14-6 创建crime数据表（CrimeBaseHelper.java） -- end
        // 代码清单14-5 编写SQL创建初始代码（CrimeBaseHelper.java） -- end
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
// 代码清单14-3 创建 CrimeBaseHelper 类（CrimeBaseHelper.java） -- end