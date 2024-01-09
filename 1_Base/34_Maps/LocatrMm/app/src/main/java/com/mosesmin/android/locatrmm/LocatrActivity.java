package com.mosesmin.android.locatrmm;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


// 代码清单33-5 托管Locatr fragment（LocatrActivity.java） -- 由继承AppCompatActivity修改为继承SingleFragmentActivity
public class LocatrActivity extends SingleFragmentActivity {
    // 代码清单33-6 检测Play服务（LocatrActivity.java） -- 1
    private static final int REQUEST_ERROR = 0;


    @Override
    protected Fragment createFragment() {
        return LocatrFragment.newInstance();
    }

    /**
     * 代码清单33-6 检测Play服务（LocatrActivity.java） -- 2
     */
    @Override
    protected void onResume() {
        super.onResume();
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability
                    .getErrorDialog(this, errorCode, REQUEST_ERROR,
                            new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    // Leave if services are unavailable.
                                    finish();
                                }
                            });
            errorDialog.show();
        }
    }
}