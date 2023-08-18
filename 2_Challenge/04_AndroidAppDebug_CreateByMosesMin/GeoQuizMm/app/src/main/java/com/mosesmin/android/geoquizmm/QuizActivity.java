package com.mosesmin.android.geoquizmm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String QUESTIONS_INDEX_KEY = "index";
    private static final String QUESTIONS_ANSWERED_KEY = "answered";

    private Button mTrueButton;
    private Button mFalseButton;

    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private TextView mQuestionTextView;

    private Question[] mQuestionBankArray = new Question[]{
            new Question(R.string.question_china,true),
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,true),
            new Question(R.string.question_africa,true),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)
    };

    // challenge1  step1  // challenge2  step1
    private boolean[] mQuestionsAnsweredArray = new boolean[mQuestionBankArray.length];
    // challenge2  step2
    private int mQestionsTrueAnswer = 0;
    
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called!");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(QUESTIONS_INDEX_KEY,0);
            // challenge1  step5
            mQuestionsAnsweredArray = savedInstanceState.getBooleanArray(QUESTIONS_ANSWERED_KEY);
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1)%mQuestionBankArray.length;
                updateQuestion();
            }
        });

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });



        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mCurrentIndex>-1)&&(mCurrentIndex<(mQuestionBankArray.length-1))){
                    mCurrentIndex = (mCurrentIndex + 1)%mQuestionBankArray.length;
                    Log.d(TAG, "NextButton CurrentIndex1: " + mCurrentIndex);
                    updateQuestion();
                }else{
                    mCurrentIndex = 0;
                    updateQuestion();
                    Log.d(TAG, "NextButton CurrentIndex2: " + mCurrentIndex);
                }
            }
        });

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex>0){
                    mCurrentIndex = (mCurrentIndex - 1)%mQuestionBankArray.length;
                    Log.d(TAG, "PrevButton CurrentIndex1: " + mCurrentIndex);
                    updateQuestion();
                }else{
                    mCurrentIndex = mQuestionBankArray.length - 1;
                    Log.d(TAG, "PrevButton CurrentIndex2: " + mCurrentIndex);
                    updateQuestion();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called!");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called!");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called!");
    }

    /**
     * @param savedInstanceState
     * 保存mCurrentIndex数据以应对设备旋转
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState!");
        savedInstanceState.putInt(QUESTIONS_INDEX_KEY,mCurrentIndex);

        // challenge1  step4
        savedInstanceState.putBooleanArray(QUESTIONS_ANSWERED_KEY, mQuestionsAnsweredArray);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called!");
    }

    private void updateQuestion(){
        int question = mQuestionBankArray[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        // challenge1  step3
        mTrueButton.setEnabled(!mQuestionsAnsweredArray[mCurrentIndex]);
        mFalseButton.setEnabled(!mQuestionsAnsweredArray[mCurrentIndex]);
//        mTrueButton.setVisibility(View.INVISIBLE);
//        mFalseButton.setVisibility(View.INVISIBLE);

        // challenge2  step4
        int resultResId = (mQestionsTrueAnswer*100)/mQuestionBankArray.length;
        // Each question will show the percentage of correct answers.
        //Toast.makeText(this, Integer.toString(resultResId)+"% correct answers", Toast.LENGTH_LONG).show();
        // The percentage of correct answers is displayed on the last question.
        if (mCurrentIndex == (mQuestionBankArray.length - 1)){
            Toast.makeText(this, Integer.toString(resultResId)+"% correct answers", Toast.LENGTH_LONG).show();
        }
        
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerTrue = mQuestionBankArray[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (userPressedTrue == answerTrue){
            messageResId = R.string.correct_toast;
            // challenge2  step3
            mQestionsTrueAnswer += 1;
        }else{
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();

        // challenge1  step2
        mQuestionsAnsweredArray[mCurrentIndex] = true;
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }
}
