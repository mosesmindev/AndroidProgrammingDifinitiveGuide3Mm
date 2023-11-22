package com.mosesmin.android.criminalintentmm.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.mosesmin.android.criminalintentmm.Crime;
import com.mosesmin.android.criminalintentmm.database.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-20 21:01:16
 */

// 代码清单14-13 创建 CrimeCursorWrapper 类（CrimeCursorWrapper.java） -- start
public class CrimeCursorWrapper extends CursorWrapper {
    private static final String TAG = "CrimeCursorWrapper";

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    // 代码清单14-14 新增 getCrime() 方法（CrimeCursorWrapper.java） -- start
    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        // 代码清单15-6 读取嫌疑人信息（CrimeCursorWrapper.java） -- start1
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));
        // 代码清单15-6 读取嫌疑人信息（CrimeCursorWrapper.java） -- end1

        // 代码清单14-16 完成 getCrime() 方法（CrimeCursorWrapper.java） -- start
        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        // 代码清单15-6 读取嫌疑人信息（CrimeCursorWrapper.java） -- start2
        crime.setSuspect(suspect);
        // 代码清单15-6 读取嫌疑人信息（CrimeCursorWrapper.java） -- end2
        return crime;

        // return null;
        // 代码清单14-16 完成 getCrime() 方法（CrimeCursorWrapper.java） -- end
    }
    // 代码清单14-14 新增 getCrime() 方法（CrimeCursorWrapper.java） -- end
}
// 代码清单14-13 创建 CrimeCursorWrapper 类（CrimeCursorWrapper.java） -- end
