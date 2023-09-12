package com.mosesmin.android.crimeintentmm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mosesmin.android.crimeintentmm.database.CrimeDbSchema.CrimeTable;

/**
 *
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName CrimeBaseHelper
 * @Description TODO
 * @Author MosesMin
 * @Date 2023-09-06 00:05:22
 * @Version 1.0
 *
 */

// 14.2 创建初始数据库 14-3 创建CrimeBaseHelper类
public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "CrimeBaseHelper";

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";
    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 14.2 创建初始数据库  14-5 编写SQL创建初始代码
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                //15.2 添加嫌疑人信息至模型层 15-4 添加嫌疑人数据库字段
                CrimeTable.Cols.SUSPECT +
                ")"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}