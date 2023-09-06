package com.mosesmin.android.crimeintentmm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mosesmin.android.crimeintentmm.database.CrimeBaseHelper;
import com.mosesmin.android.crimeintentmm.database.CrimeCursorWrapper;
import com.mosesmin.android.crimeintentmm.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 *
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName CrimeLab
 * @Description TODO
 * @Author MosesMin
 * @Date 2023-08-28 22:26:48
 * @Version 1.0
 *
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";

    private static CrimeLab sCrimeLab;
    // 14.2 创建初始数据库 14-4 打开SQLiteDatabase
    private Context mContext;
    private SQLiteDatabase mDatabase;

    /**
     * Singleton methods of CrimeLab
     * @param context
     * @return
     */
    public static CrimeLab getInstance(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        // 14.2 创建初始数据库 14-4 打开SQLiteDatabase
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addCrime(Crime c){
        // 14.4.2 插入和更新记录 14-9 插入记录
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    // 14.5.2 创建模型层对象 14-18 返回crime列表
    public List<Crime> getCrimes() {
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
    }

    // 14.5.2 创建模型层对象 14-19 重写getCrime(UUID)方法
    // ? ：问号？是Sql中的占位符：若要创建每次使用不同值的查询，可以在查询中使用参数。参数是在运行查询时所提供值的占位符。
    public Crime getCrime(UUID id){
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
        // 14-19  后涉及的快照的概念：快照是指数据源在某个时间点的数据副本。
    }

    // 14.4.2 插入和更新记录 14-10 更新记录
    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    // 14.5 读取数据库 14-12 查询crime记录
    // 14.5.2 创建模型层对象  14-17 使用Cursor封装方法
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new CrimeCursorWrapper(cursor);
    }

    // 14.4.1 使用ContentValues 写入数据库 14-8 创建ContentValues
    private static ContentValues getContentValues(Crime crime) {ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }

}