package com.mosesmin.android.nerdlaunchermm;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:NerdLauncherMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-28 22:45:53
 */
// 代码清单24-2  NerdLauncherFragment 初始类（NerdLauncherFragment.java）
public class NerdLauncherFragment extends Fragment {
    // 代码清单24-3 向 PackageManager 查询activity总数（NerdLauncherFragment.java）
    private static final String TAG = "NerdLauncherFragment";

    private RecyclerView mRecyclerView;

    public static NerdLauncherFragment newInstance(){
        return new NerdLauncherFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nerd_launcher,container,false);
        mRecyclerView = v.findViewById(R.id.app_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 代码清单24-3 向 PackageManager 查询activity总数（NerdLauncherFragment.java）
        setupAdapter();
        return v;
    }

    // 代码清单24-3 向 PackageManager 查询activity总数（NerdLauncherFragment.java）
    private void setupAdapter() {
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = getActivity().getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(startupIntent, 0);
        // 代码清单24-4 对activity标签排序（NerdLauncherFragment.java）
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            public int compare(ResolveInfo a, ResolveInfo b) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(
                        a.loadLabel(pm).toString(),
                        b.loadLabel(pm).toString());
            }
        });
        Log.i(TAG, "Found " + activities.size() + " activities.");
        // 代码清单24-7 为 RecyclerView 设置adapter（NerdLauncherFragment.java）
        mRecyclerView.setAdapter(new ActivityAdapter(activities));
    }

    // 代码清单24-5 实现 ViewHolder （NerdLauncherFragment.java）
    private class ActivityHolder extends RecyclerView.ViewHolder
        // 代码清单24-8 启动目标activity（NerdLauncherFragment.java）
        // -- 实现view.OnClickListener 点击onClick启动
            implements View.OnClickListener{
        private ResolveInfo mResolveInfo;
        private TextView mNameTextView;
        public ActivityHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView;
            // 代码清单24-8 启动目标activity（NerdLauncherFragment.java）
            // -- 实现view.OnClickListener 点击onClick启动
            mNameTextView.setOnClickListener(this);
        }

        public void bindActivity(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            String appName = mResolveInfo.loadLabel(pm).toString();
            mNameTextView.setText(appName);
        }

        // 代码清单24-8 启动目标activity（NerdLauncherFragment.java）
        // -- 实现view.OnClickListener 点击onClick启动
        @Override
        public void onClick(View v) {
            ActivityInfo activityInfo = mResolveInfo.activityInfo;
            Intent i = new Intent(Intent.ACTION_MAIN)
                    .setClassName(activityInfo.applicationInfo.packageName,
                            activityInfo.name)
                    // 代码清单24-9 为intent添加新任务标志（NerdLauncherFragment.java）
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

    // 代码清单24-6 实现 RecyclerView.Adapter （NerdLauncherFragment.java）
    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder> {
        private final List<ResolveInfo> mActivities;
        public ActivityAdapter(List<ResolveInfo> activities) {
            mActivities = activities;
        }
        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ActivityHolder(view);
        }
        @Override
        public void onBindViewHolder(ActivityHolder holder, int position) {
            ResolveInfo resolveInfo = mActivities.get(position);
            holder.bindActivity(resolveInfo);
        }
        @Override
        public int getItemCount() {
            return mActivities.size();
        }
    }


}