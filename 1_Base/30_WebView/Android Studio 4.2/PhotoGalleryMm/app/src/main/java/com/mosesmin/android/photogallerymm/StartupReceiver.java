package com.mosesmin.android.photogallerymm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mosesmin.android.photogallerymm.utils.background.service.PollService;
import com.mosesmin.android.photogallerymm.utils.databasesharedpreferences.QueryPreferences;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO  代码清单29-1 第一个broadcast receiver（StartupReceiver.java）
 * @Author: MosesMin
 * @Date: 2023-12-31 01:15:45
 */
public class StartupReceiver extends BroadcastReceiver {
    private static final String TAG = "StartupReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received broadcast intent: " + intent.getAction());

        // 代码清单29-5 设备重启后启动定时器（StartupReceiver.java） -- start
        boolean isOn = QueryPreferences.isAlarmOn(context);
        PollService.setServiceAlarm(context, isOn);
        // 代码清单29-5 设备重启后启动定时器（StartupReceiver.java） -- end
    }
}