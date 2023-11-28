package com.mosesmin.android.beatboxmm;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mosesmin.android.beatboxmm.bean.BeatBox;
import com.mosesmin.android.beatboxmm.bean.Sound;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:BeatBoxMm
 * @Description: TODO  视图模型类
 * @Author: MosesMin
 * @Date: 2023-11-27 21:26:08
 */
// 代码清单20-20 创建 SoundViewModel 类（SoundViewModel.java）
// 代码清单20-26 绑定数据观察--几行代码就搞定（SoundViewModel.java）
// -- 1 在视图模型里继承 BaseObservable 类； 实现 Observable 接口
public class SoundViewModel extends BaseObservable {
    private static final String TAG = "SoundViewModel";

    private Sound mSound;
    private BeatBox mBeatBox; // 播放声音文件的 BeatBox 对象

    public SoundViewModel(BeatBox beatBox) {
        mBeatBox = beatBox;
    }

    /**
     * 代码清单20-21 添加绑定方法（SoundViewModel.java）
     * 布局，需要该方法获取按钮要用的文件名。
     * @return
     */
    public String getTitle() {
        return mSound.getName();
    }

    // 代码清单20-26 绑定数据观察--几行代码就搞定（SoundViewModel.java）
    // -- 2 使用 @Bindable 注解视图模型里可绑定的属性；
    @Bindable
    public Sound getSound() {
        return mSound;
    }

    public void setSound(Sound sound) {
        mSound = sound;
        // 代码清单20-26 绑定数据观察--几行代码就搞定（SoundViewModel.java）
        // -- 3 每次可绑定的属性值改变时，就调用 notifyChange() 方法或 notifyPropertyChanged(int) 方法。
        notifyChange();
    }


    /**
     * 测试类中操作生成的 空 onButtonClicked 方法
     * 代码清单21-12 创建 onButtonClicked() 方法（SoundViewModel.java）
     *
     */
    public void onButtonClicked() {
        // 代码清单21-14 实现 onButtonClicked() 方法（SoundViewModel.java）
        mBeatBox.play(mSound);
    }
}