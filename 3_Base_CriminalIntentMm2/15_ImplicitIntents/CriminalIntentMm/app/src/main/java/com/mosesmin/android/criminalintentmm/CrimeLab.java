package com.mosesmin.android.criminalintentmm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mosesmin.android.criminalintentmm.database.CrimeBaseHelper;
import com.mosesmin.android.criminalintentmm.database.CrimeCursorWrapper;
import com.mosesmin.android.criminalintentmm.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-10-30 21:04:05
 */

public class CrimeLab {
    // 代码清单8-1 创建单例（CrimeLab.java） -- start
    private static CrimeLab sCrimeLab;

    // 代码清单14-7 删除 mCrimes 相关代码（CrimeLab.java） -- start1
    // 代码清单8-2 创建可容纳Crime对象的List（CrimeLab.java） -- start
    // 代码清单14-7 删除 mCrimes 相关代码（CrimeLab.java） -- end1

    // 代码清单14-4 打开 SQLiteDatabase （CrimeLab.java） -- start1
    private Context mContext;
    private SQLiteDatabase mDatabase;
    // 代码清单14-4 打开 SQLiteDatabase （CrimeLab.java） -- end1

    public static CrimeLab getInstance(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        // 代码清单14-4 打开 SQLiteDatabase （CrimeLab.java） -- start2
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();
        // 代码清单14-4 打开 SQLiteDatabase （CrimeLab.java） -- end2

        // 代码清单14-7 删除 mCrimes 相关代码（CrimeLab.java） -- start2
        // 代码清单14-7 删除 mCrimes 相关代码（CrimeLab.java） -- end2

        // 代码清单13-9 再见，随机crime记录！（CrimeLab.java） -- start
        // 代码清单8-3 生成100个crime（CrimeLab.java） -- start
        // 代码清单8-3 生成100个crime（CrimeLab.java） -- end
        // 代码清单13-9 再见，随机crime记录！（CrimeLab.java） -- end
    }
    // 代码清单8-1 创建单例（CrimeLab.java） -- end

    // 代码清单13-8 添加新的crime（CrimeLab.java） -- start
    public void addCrime(Crime c){
        // 代码清单14-7 删除 mCrimes 相关代码（CrimeLab.java） -- start3
        // 代码清单14-7 删除 mCrimes 相关代码（CrimeLab.java） -- end3

        // 代码清单14-9 插入记录（CrimeLab.java） -- start
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
        // 代码清单14-9 插入记录（CrimeLab.java） -- end
    }
    // 代码清单13-8 添加新的crime（CrimeLab.java） -- end

    public List<Crime> getCrimes(){
        // 代码清单14-7 删除 mCrimes 相关代码（CrimeLab.java） -- start4
        // return mCrimes;

        // 代码清单14-18 返回crime列表（CrimeLab.java） -- start
        //return new ArrayList<>();
        // 代码清单14-7 删除 mCrimes 相关代码（CrimeLab.java） -- end4

        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
        // 代码清单14-18 返回crime列表（CrimeLab.java） -- end
    }

    public Crime getCrime(UUID id){
        // 代码清单14-7 删除 mCrimes 相关代码（CrimeLab.java） -- start5
        // 代码清单14-7 删除 mCrimes 相关代码（CrimeLab.java） -- end5

        // 代码清单14-19 重写 getCrime(UUID) 方法（CrimeLab.java） -- start
        // return null;
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
        // 代码清单14-19 重写 getCrime(UUID) 方法（CrimeLab.java） -- end
    }
    // 代码清单8-2 创建可容纳Crime对象的List（CrimeLab.java） -- end

    // 代码清单14-10 更新记录（CrimeLab.java） -- start
    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        /* 使用 ? 的话，就不用关心 String 包含什么，代码执行的效果肯定就是我们想要的。因此，建
        议保持这种良好的编码习惯。 */
        mDatabase.update(CrimeTable.NAME, values,
    CrimeTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }
    // 代码清单14-10 更新记录（CrimeLab.java） -- end


    // 代码清单14-12 查询crime记录（CrimeLab.java） -- start
    // 代码清单14-17 使用 Cursor 封装方法（CrimeLab.java） -- start1
    //private Cursor queryCrimes(String whereClause, String[] whereArgs) {
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        // 代码清单14-17 使用 Cursor 封装方法（CrimeLab.java） -- end1
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        // 代码清单14-17 使用 Cursor 封装方法（CrimeLab.java） -- start2
        //return cursor;
        return new CrimeCursorWrapper(cursor);
        // 代码清单14-17 使用 Cursor 封装方法（CrimeLab.java） -- end2
    }
    // 代码清单14-12 查询crime记录（CrimeLab.java） -- end

    // 代码清单14-8 创建 ContentValues （CrimeLab.java） -- start
    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        // 代码清单15-5 写入嫌疑人信息（CrimeLab.java） -- start
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
        // 代码清单15-5 写入嫌疑人信息（CrimeLab.java） -- end
        return values;
    }
    // 代码清单14-8 创建 ContentValues （CrimeLab.java） -- end

}
