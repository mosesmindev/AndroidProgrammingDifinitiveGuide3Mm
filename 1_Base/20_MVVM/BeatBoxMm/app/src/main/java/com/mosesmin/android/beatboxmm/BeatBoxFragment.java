package com.mosesmin.android.beatboxmm;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mosesmin.android.beatboxmm.bean.BeatBox;
import com.mosesmin.android.beatboxmm.bean.Sound;
import com.mosesmin.android.beatboxmm.databinding.FragmentBeatBoxBinding;
import com.mosesmin.android.beatboxmm.databinding.ListItemSoundBinding;

import java.util.List;

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

    // 代码清单20-15 创建 BeatBox 实例（BeatBoxFragment.java）
    private BeatBox mBeatBox;

    // 代码清单20-2 创建 BeatBoxFragment （BeatBoxFragment.java）
    public static BeatBoxFragment newInstance(){
        return new BeatBoxFragment();
    }

    // 代码清单20-15 创建 BeatBox 实例（BeatBoxFragment.java）
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBeatBox = new BeatBox(getActivity());
    }

    // 代码清单20-6 实例化绑定类（BeatBoxFragment.java）
    // 不用 findViewById(...) 方法，转而使用数据绑定获取视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentBeatBoxBinding binding = DataBindingUtil
                .inflate(inflater,R.layout.fragment_beat_box,container,false);
        // 代码清单20-7 配置 RecyclerView （BeatBoxFragment.java）
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        // 代码清单20-19 传入声音资源（BeatBoxFragment.java） -- start
        // 代码清单20-11 使用 SoundAdapter （BeatBoxFragment.java）
        // binding.recyclerView.setAdapter(new SoundAdapter());
        binding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));
        // 代码清单20-19 传入声音资源（BeatBoxFragment.java） -- end
        return binding.getRoot();
    }

    // 代码清单20-9 创建 SoundHolder （BeatBoxFragment.java）
    private class SoundHolder extends RecyclerView.ViewHolder{
        private ListItemSoundBinding mBinding;

        private SoundHolder(ListItemSoundBinding binding){
            super(binding.getRoot());
            mBinding = binding;
            // 代码清单20-24 关联使用视图模型（BeatBoxFragment.java） --  创建并添加了一个视图模型
            mBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        /**
         * 代码清单20-24 关联使用视图模型（BeatBoxFragment.java）
         * 绑定方法  -- 更新视图模型要用到的数据。
         * @param sound
         */
        public void bind(Sound sound) {
            mBinding.getViewModel().setSound(sound);
            mBinding.executePendingBindings(); // 使布局立即刷新，帮助RecyclerView 的表现更为流畅。
        }
    }

    // 代码清单20-10 创建 SoundAdapter （BeatBoxFragment.java）
    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder>{
        // 代码清单20-18 绑定 Sound 列表（BeatBoxFragment.java） -- 让 SoundAdapter 与 Sound 列表关联
        private List<Sound> mSounds;

        /**
         * 代码清单20-18 绑定 Sound 列表（BeatBoxFragment.java）
         * -- 让 SoundAdapter 与 Sound 列表关联
         * @param sounds
         */
        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            // 绑定类 ： ListItemSoundBinding 指向布局文件
            ListItemSoundBinding binding = DataBindingUtil.
                    inflate(inflater,R.layout.list_item_sound,parent,false);
            return new SoundHolder(binding);
        }

        @Override
        public void onBindViewHolder(SoundHolder holder, int position) {
            // 代码清单20-25 调用 bind(Sound) 方法（BeatBoxFragment.java） --使用视图模型
            Sound sound = mSounds.get(position);
            holder.bind(sound);
        }

        @Override
        public int getItemCount() {
            // 代码清单20-18 绑定 Sound 列表（BeatBoxFragment.java）
            // -- 让 SoundAdapter 与 Sound 列表关联
            return mSounds.size();
        }
    }
}