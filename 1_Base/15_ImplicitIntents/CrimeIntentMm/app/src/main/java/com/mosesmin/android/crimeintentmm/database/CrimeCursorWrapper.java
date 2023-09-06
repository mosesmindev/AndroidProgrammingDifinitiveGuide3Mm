package com.mosesmin.android.crimeintentmm.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.mosesmin.android.crimeintentmm.Crime;
import com.mosesmin.android.crimeintentmm.database.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName CrimeCursorWrapper
 * @Description TODO
 * @Author MosesMin
 * @Date 2023-09-06 08:07:12
 * @Version 1.0
 *
 */

// 14.5.1 使用CursorWrapper  14-13 创建CrimeCursorWrapper类 创建 Cursor子类、封装 Cursor 的对象
// Cursor是个神奇的表数据处理工具。其功能就是封装数据表中的原始字段值。
public class CrimeCursorWrapper extends CursorWrapper {
    private static final String TAG = "CrimeCursorWrapper";

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    // 14.5.1 使用CursorWrapper 14-14 新增getCrime()方法
    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        // 14.5.1 使用CursorWrapper  14-16 完成getCrime()方法
        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        return crime;
    }
}