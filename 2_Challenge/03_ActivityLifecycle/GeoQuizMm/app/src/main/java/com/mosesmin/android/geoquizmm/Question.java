package com.mosesmin.android.geoquizmm;

/**
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName Question
 * @Description TODO
 * @Author MosesMin
 * @Date 2023-08-17 8:28
 * @Version 1.0
 */
public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId,boolean answerTrue){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}