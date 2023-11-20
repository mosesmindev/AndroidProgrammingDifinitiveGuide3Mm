package com.mosesmin.android.criminalintentmm;

import android.content.Context;

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

    // 代码清单8-2 创建可容纳Crime对象的List（CrimeLab.java） -- start
    private List<Crime> mCrimes;

    public static CrimeLab getInstance(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();
        // 代码清单13-9 再见，随机crime记录！（CrimeLab.java） -- start
        // 代码清单8-3 生成100个crime（CrimeLab.java） -- start
        // 代码清单8-3 生成100个crime（CrimeLab.java） -- end
        // 代码清单13-9 再见，随机crime记录！（CrimeLab.java） -- end
    }
    // 代码清单8-1 创建单例（CrimeLab.java） -- end

    // 代码清单13-8 添加新的crime（CrimeLab.java） -- start
    public void addCrime(Crime c){
        mCrimes.add(c);
    }
    // 代码清单13-8 添加新的crime（CrimeLab.java） -- end

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime crime:mCrimes) {
            if (crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
    // 代码清单8-2 创建可容纳Crime对象的List（CrimeLab.java） -- end

}
