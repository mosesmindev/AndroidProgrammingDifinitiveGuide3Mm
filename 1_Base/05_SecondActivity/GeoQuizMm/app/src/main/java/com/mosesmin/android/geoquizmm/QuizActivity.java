package com.mosesmin.android.geoquizmm;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int RESULT_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBankArray = new Question[]{
            new Question(R.string.question_china,false),
            new Question(R.string.question_australia,true),
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,true),
            new Question(R.string.question_africa,true),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called!");
        Log.d(TAG, "onCreate(Bundle) called -- QeoQuizVersion:" + getQeoQuizVersion());
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }

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

        mQuestionTextView = findViewById(R.id.question_text_view);

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1)%mQuestionBankArray.length;
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CheatActivity
                //Intent intent = new Intent(QuizActivity.this,CheatActivity.class);
                boolean answerIsTrue = mQuestionBankArray[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this,answerIsTrue);
                //startActivity(intent);
                startActivityForResult(intent,RESULT_CODE_CHEAT);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == RESULT_CODE_CHEAT){
            if (data == null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
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
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
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
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerTrue = mQuestionBankArray[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (mIsCheater){
            messageResId = R.string.judgment_toast;
        }else{
            if (userPressedTrue == answerTrue){
                messageResId = R.string.correct_toast;
            }else{
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    private String getQeoQuizVersion() {
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            return "GeoQuiz_" + packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "getQeoQuizVersion: " + e.toString());
            e.printStackTrace();
        }
        return "GeoQuiz_V1.0.0_20230818";
    }
}