package com.mosesmin.android.crimeintentmm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    //public static final String EXTRA_CRIME_ID = "com.mosesmin.android.crimeintentmm.crime_id";
    private static final String EXTRA_CRIME_ID = "com.mosesmin.android.crimeintentmm.crime_id";

    public static Intent newIntent(Context packageConext, UUID crimeId){
        Intent intent = new Intent(packageConext,CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        //return new CrimeFragment();
        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }
}