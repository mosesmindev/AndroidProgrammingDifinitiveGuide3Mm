package com.mosesmin.android.nerdlaunchermm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return v;
    }
}