package com.mosesmin.android.beatboxmm.bean;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:BeatBoxMm
 * @Description: TODO 资源管理类
 *                      在应用中进行定位、管理记录以及播放 assets资源。
 * @Author: MosesMin
 * @Date: 2023-11-26 22:36:20
 */
// 代码清单20-12 创建 BeatBox 类（BeatBox.java）
public class BeatBox {
    private static final String TAG = "BeatBox"; // 用于日志记录

    private static final String SOUNDS_FOLDER = "sample_sounds"; // 用于存储声音资源文件目录名
    // 实现音频播放功能，这需要创建一个 SoundPool 对象
    // 代码清单21-1 创建 SoundPool 对象（BeatBox.java）
    private static final int MAX_SOUNDS = 5;

    // 代码清单20-13 获取 AssetManager 备用（BeatBox.java）
    // 使用 AssetManager 类访问assets。可以从 Context 中获取它。
    private AssetManager mAssets;

    // 代码清单20-17 创建 Sound 列表（BeatBox.java）
    private List<Sound> mSounds = new ArrayList<>();

    // 实现音频播放功能，这需要创建一个 SoundPool 对象
    // 代码清单21-1 创建 SoundPool 对象（BeatBox.java）
    private SoundPool mSoundPool;

    /**
     * @param context
     * 在访问assets时，可以不用关心究竟使用哪个 Context 对象。
     * 因为，在实践中的任何场景下，所有 Context 中的 AssetManager 都管理着同一套assets资源。
     */
    public BeatBox(Context context){
        mAssets = context.getAssets();

        // 实现音频播放功能，这需要创建一个 SoundPool 对象
        // 代码清单21-1 创建 SoundPool 对象（BeatBox.java）

        // This old constructor is deprecated, but we need it for compatibility.
        // 这个旧的构造函数已弃用，但我们需要它来保持兼容性。向下兼容到API 19 -- Android4.4
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);

        // 代码清单20-14 查看 assets 资源（BeatBox.java）
        loadSounds();
    }

    /**
     * 代码清单21-5 播放音频（BeatBox.java）
     * @param sound
     */
    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }



    /**
     * 音频播放完毕，应调用 SoundPool.release()方法释放 SoundPool 。
     * 代码清单21-16 释放 SoundPool （BeatBox.java）  -- 释放音频
     */
    public void release() {
        mSoundPool.release();
    }

    // 代码清单20-14 查看 assets 资源（BeatBox.java）
    /**
     * 要取得assets中的资源清单，可以使用 list(String) 方法。
     */
    private void loadSounds(){
        String[] soundNames;
        try{
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds.");
        }catch (IOException ioe){
            Log.e(TAG, "Could not list assets", ioe);
            return;
        }

        // 代码清单20-17 创建 Sound 列表（BeatBox.java）
        for (String filename : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                // 调用 load(Sound) 方法载入全部音频文件
                // 代码清单21-4 载入全部音频文件（BeatBox.java）
                load(sound);

                mSounds.add(sound);
            } catch (IOException ioe) {
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

    // 代码清单21-3 加载音频（BeatBox.java）
    private void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1); // 把文件载入 SoundPool 待播。
        sound.setSoundId(soundId);
    }

    // 代码清单20-17 创建 Sound 列表（BeatBox.java）
    public List<Sound> getSounds() {
        return mSounds;
    }
}