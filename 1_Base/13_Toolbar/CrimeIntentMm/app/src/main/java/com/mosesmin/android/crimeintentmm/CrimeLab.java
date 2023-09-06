package com.mosesmin.android.crimeintentmm;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName CrimeLab
 * @Description TODO
 * @Author MosesMin
 * @Date 2023-08-28 22:26:48
 * @Version 1.0
 */
public class CrimeLab {
    private static final String TAG = "CrimeLab";

    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

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
        mCrimes = new ArrayList<>();
    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public List<Crime> getCrimes() {
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

}