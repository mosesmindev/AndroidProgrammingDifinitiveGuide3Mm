package com.mosesmin.android.photogallerymm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.mosesmin.android.photogallerymm.utils.background.service.PollService;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO  新建一个 VisibleFragment 抽象类，继承 Fragment 类，
 *                  如代码清单29-7所示。该类是一个隐藏前台通知的通用型fragment。
 * @Author: MosesMin
 * @Date: 2023-12-31 14:59:20
 */
public abstract class VisibleFragment extends Fragment {
    private static final String TAG = "VisibleFragment";

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(PollService.ACTION_SHOW_NOTIFICATION);
        getActivity().registerReceiver(mOnShowNotification, filter,
                //代码清单29-11 broadcast receiver的使用权限（VisibleFragment.java） -- 添加参数PollService.PERM_PRIVATE, null
                PollService.PERM_PRIVATE, null);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mOnShowNotification);
    }

    private BroadcastReceiver mOnShowNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 代码清单29-12 返回一个简单结果码（VisibleFragment.java） -- start
            /*
            Toast.makeText(getActivity(),
                    "Got a broadcast:" + intent.getAction(),
                    Toast.LENGTH_LONG)
                    .show();
            */
            // If we receive this, we're visible, so cancel the notification
            Log.i(TAG, "canceling notification");
            setResultCode(Activity.RESULT_CANCELED);
            // 代码清单29-12 返回一个简单结果码（VisibleFragment.java） -- end
        }
    };
}