package com.mosesmin.android.beatboxmm.bean;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:BeatBoxMm
 * @Description: TODO 获取到资源文件名之后，要显示给用户看，最终还需要播放这些声音文件。
 *                    所以，需要创建一个对象，让它管理资源文件名、用户应该看到的文件名以及其他一些相关信息。
 *                    所以，创建一个满足上述需求描述的 Sound 管理类。
 * @Author: MosesMin
 * @Date: 2023-11-27 20:59:48
 */

// 代码清单20-16 创建 Sound 对象（Sound.java）
public class Sound {
    private static final String TAG = "Sound";

    private String mAssetPath;
    private String mName;

    public Sound(String assetPath) {
        mAssetPath = assetPath;
        String[] components = assetPath.split("/");  // 分离出文件名
        String filename = components[components.length - 1];
        mName = filename.replace(".wav", ""); // 删除.wav后缀
    }

    public String getAssetPath() {
        return mAssetPath;
    }

    public String getName() {
        return mName;
    }
}