package com.mosesmin.android.photogallerymm.utils.background.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.mosesmin.android.photogallerymm.PhotoGalleryActivity;
import com.mosesmin.android.photogallerymm.R;
import com.mosesmin.android.photogallerymm.bean.GalleryItem;
import com.mosesmin.android.photogallerymm.utils.databasesharedpreferences.QueryPreferences;
import com.mosesmin.android.photogallerymm.utils.net.FlickrFetchr;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:PhotoGalleryMm
 * @Description: TODO 代码清单28-1 创建 PollService （PollService.java）
 *                      IntentService 是Android中的常用服务
 * @Author: MosesMin
 * @Date: 2023-12-28 08:47:53
 */
public class PollService extends IntentService {
    private static final String TAG = "PollService";

    // 代码清单28-8 添加定时方法（PollService.java） -- 1start
    // Set interval to 1 minute
    private static final long POLL_INTERVAL_MS = TimeUnit.MINUTES.toMillis(1);
    // 代码清单28-8 添加定时方法（PollService.java） -- 1end

    // 代码清单29-6 发送broadcast intent（PollService.java） -- 1
    public static final String ACTION_SHOW_NOTIFICATION =
            "com.mosesmin.android.photogallerymm.SHOW_NOTIFICATION";

    // 代码清单29-10 发送带有权限的broadcast（PollService.java） --1
    public static final String PERM_PRIVATE =
            "com.mosesmin.android.photogallerymm.PRIVATE";

    // 代码清单29-13 发送有序broadcast（PollService.java） -- 1start
    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String NOTIFICATION = "NOTIFICATION";
    // 代码清单29-13 发送有序broadcast（PollService.java） -- 1end


    // 代码清单29-13 发送有序broadcast（PollService.java） -- 1start
    // 代码清单29-13 发送有序broadcast（PollService.java） -- 1end

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    /**
     * 代码清单28-8 添加定时方法（PollService.java） -- 2
     * @param context
     * @param isOn
     */
    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(),POLL_INTERVAL_MS, pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }

        // 代码清单29-4 存储定时器状态（PollService.java）
        QueryPreferences.setAlarmOn(context, isOn);
    }

    /**
     * 代码清单28-10 添加 isServiceAlarmOn() 方法（PollService.java）
     * @param context
     * @return
     */
    public static boolean isServiceAlarmOn(Context context) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    public PollService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // 代码清单28-4 检查后台网络的可用性（PollService.java） -- 1start
        if (!isNetworkAvailableAndConnected()) {
            return;
        }
        // 代码清单28-4 检查后台网络的可用性（PollService.java） -- 1end

        // 代码清单28-7 检查最新返回结果（PollService.java） -- start
        // Log.i(TAG, "Received an intent: " + intent);
        String query = QueryPreferences.getStoredQuery(this);
        String lastResultId = QueryPreferences.getLastResultId(this);
        List<GalleryItem> items;
        if (query == null) {
            items = new FlickrFetchr().fetchRecentPhotos();
        } else {
            items = new FlickrFetchr().searchPhotos(query);
        }
        if (items.size() == 0) {
            return;
        }
        String resultId = items.get(0).getId();
        if (resultId.equals(lastResultId)) {
            Log.i(TAG, "Got an old result: " + resultId);
        } else {
            Log.i(TAG, "Got a new result: " + resultId);

            // 代码清单28-17 添加通知信息（PollService.java） -- start
            Resources resources = getResources();
            Intent i = PhotoGalleryActivity.newIntent(this);
            PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker(resources.getString(R.string.new_pictures_title))
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle(resources.getString(R.string.new_pictures_title))
                    .setContentText(resources.getString(R.string.new_pictures_text))
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();

            // 代码清单29-13 发送有序broadcast（PollService.java） -- 2start
            /*
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(this);
            notificationManager.notify(0, notification);
            // 代码清单28-17 添加通知信息（PollService.java） -- end

            // 代码清单29-6 发送broadcast intent（PollService.java） -- 2start
            // 代码清单29-10 发送带有权限的broadcast（PollService.java） --2 添加参数PERM_PRIVATE
            sendBroadcast(new Intent(ACTION_SHOW_NOTIFICATION),PERM_PRIVATE);
            // 代码清单29-6 发送broadcast intent（PollService.java） -- 2end
            */
            showBackgroundNotification(0, notification);
            // 代码清单29-13 发送有序broadcast（PollService.java） -- 2end
        }
        QueryPreferences.setLastResultId(this, resultId);
        // 代码清单28-7 检查最新返回结果（PollService.java） -- end
    }

    /**
     * 代码清单29-13 发送有序broadcast（PollService.java） -- 3
     * @param requestCode
     * @param notification
     */
    private void showBackgroundNotification(int requestCode, Notification notification) {
        Intent i = new Intent(ACTION_SHOW_NOTIFICATION);
        i.putExtra(REQUEST_CODE, requestCode);
        i.putExtra(NOTIFICATION, notification);
        sendOrderedBroadcast(i, PERM_PRIVATE, null, null,
                Activity.RESULT_OK, null, null);
    }


    /**
     * 代码清单28-4 检查后台网络的可用性（PollService.java） -- 2
     * @return
     */
    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }
}