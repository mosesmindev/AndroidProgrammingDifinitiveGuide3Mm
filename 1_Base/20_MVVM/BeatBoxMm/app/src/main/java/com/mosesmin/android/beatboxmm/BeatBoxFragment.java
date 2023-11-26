package com.mosesmin.android.beatboxmm;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mosesmin.android.beatboxmm.databinding.FragmentBeatBoxBinding;
import com.mosesmin.android.beatboxmm.databinding.ListItemSoundBinding;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:BeatBoxMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-26 18:37:00
 */
// 代码清单20-2 创建 BeatBoxFragment （BeatBoxFragment.java）
public class BeatBoxFragment extends Fragment {
    private static final String TAG = "BeatBoxFragment";

    // 代码清单20-2 创建 BeatBoxFragment （BeatBoxFragment.java）
    public static BeatBoxFragment newInstance(){
        return new BeatBoxFragment();
    }

    // 代码清单20-6 实例化绑定类（BeatBoxFragment.java）
    // 不用 findViewById(...) 方法，转而使用数据绑定获取视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding = DataBindingUtil
                .inflate(inflater,R.layout.fragment_beat_box,container,false);
        // 代码清单20-7 配置 RecyclerView （BeatBoxFragment.java）
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        // 代码清单20-11 使用 SoundAdapter （BeatBoxFragment.java）
        binding.recyclerView.setAdapter(new SoundAdapter());
        return binding.getRoot();
    }

    // 代码清单20-9 创建 SoundHolder （BeatBoxFragment.java）
    private class SoundHolder extends RecyclerView.ViewHolder{
        private ListItemSoundBinding mBinding;

        private SoundHolder(ListItemSoundBinding binding){
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    // 代码清单20-10 创建 SoundAdapter （BeatBoxFragment.java）
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{
        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemSoundBinding binding = DataBindingUtil.
                    inflate(inflater,R.layout.list_item_sound,parent,false);
            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}