package com.mosesmin.android.beatboxmm;

import com.mosesmin.android.beatboxmm.bean.BeatBox;
import com.mosesmin.android.beatboxmm.bean.Sound;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:BeatBoxMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-27 22:40:25
 */
// 代码清单21-7 空测试类（SoundViewModelTest.java）
public class SoundViewModelTest {
    // 代码清单21-8 创建虚拟 BeatBox 对象（SoundViewModelTest.java）
    private BeatBox mBeatBox;

    // 代码清单21-9 创建 SoundViewModel 测试对象（SoundViewModelTest.java） -- start1
    private Sound mSound;  //  Sound 是简单的数据对象，不容易出问题，这里就 不 虚拟它了（书里P342写的“这里就虚拟它了” 大概率写错了）
    private SoundViewModel mSubject;
    // 代码清单21-9 创建 SoundViewModel 测试对象（SoundViewModelTest.java） -- end1

    /**
     * @throws Exception
     * 以 @Before 注解的包含公共代码的方法会在
     * 所有测试之前运行一次。按照约定，所有单元测试类都要有以 @Before 注解的 setUp() 方法。
     */
    @Before
    public void setUp() throws Exception {
        // 代码清单21-8 创建虚拟 BeatBox 对象（SoundViewModelTest.java）
        mBeatBox = mock(BeatBox.class);

        // 代码清单21-9 创建 SoundViewModel 测试对象（SoundViewModelTest.java） -- start2
        mSound = new Sound("assetPath");
        mSubject = new SoundViewModel(mBeatBox); // 习惯约定  命名为 mSubject 表示是要测试的对象
        mSubject.setSound(mSound);
        // 代码清单21-9 创建 SoundViewModel 测试对象（SoundViewModelTest.java） -- end2
    }

    /**
     * 代码清单21-10 测试标题属性（SoundViewModelTest.java）
     * 断定 SoundViewModel 里的 getTitle() 属性和Sound 里的 getName() 属性是有关系的。
     */
    @Test
    public void exposesSoundNameAsTitle() {
        assertThat(mSubject.getTitle(), is(mSound.getName()));
    }

    /**
     * 刚才做了测试热身，现在处理关键实现任务：整合 SoundViewModel 和 BeatBox.play(Sound) 方
     * 法。
     * 实践中，通常的做法是，在写（实现任务的）新方法之前，先写一个测试验证这个方法的预期结果。
     *
     * 实现任务是需要在 SoundViewModel 类里写 onButtonClicked() 方法去调用 BeatBox.play(Sound) 方法。
     * 所以，先写一个测试方法调用 onButtonClicked() 方法。
     *
     * 代码清单21-11 测试 onButtonClicked() 方法（SoundViewModelTest.java）
     */
    @Test
    public void callsBeatBoxPlayOnButtonClicked() {
        mSubject.onButtonClicked();

        // 代码清单21-13 验证 BeatBox.play(Sound) 方法是否调用（SoundViewModelTest.java）
        verify(mBeatBox).play(mSound);
    }
}