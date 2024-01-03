package com.mosesmin.android.draganddrawmm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:DragAndDrawMm
 * @Description: TODO  代码清单31-2 创建 DragAndDrawFragment （DragAndDrawFragment.java）
 * @Author: MosesMin
 * @Date: 2024-01-03 22:23:31
 */
public class DragAndDrawFragment extends Fragment {
    private static final String TAG = "DragAndDrawFragment";

    public static DragAndDrawFragment newInstance() {
        return new DragAndDrawFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drag_and_draw, container, false);
        return v;
    }
}