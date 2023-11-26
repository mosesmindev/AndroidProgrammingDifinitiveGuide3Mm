package com.mosesmin.android.beatboxmm.bean;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:BeatBoxMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-26 22:36:20
 */
// 代码清单20-12 创建 BeatBox 类（BeatBox.java）
public class BeatBox {
    private static final String TAG = "BeatBox"; // 用于日志记录

    private static final String SOUNDS_FOLDER = "sample_sounds"; // 用于存储声音资源文件目录名

    // 代码清单20-13 获取 AssetManager 备用（BeatBox.java）
    // 使用 AssetManager 类访问assets。可以从 Context 中获取它。
    private AssetManager mAssets;

    /**
     * @param context
     * 在访问assets时，可以不用关心究竟使用哪个 Context 对象。
     * 因为，在实践中的任何场景下，所有 Context 中的 AssetManager 都管理着同一套assets资源。
     */
    public BeatBox(Context context){
        mAssets = context.getAssets();
        // 代码清单20-14 查看 assets 资源（BeatBox.java）
        loadSounds();
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
    }
}