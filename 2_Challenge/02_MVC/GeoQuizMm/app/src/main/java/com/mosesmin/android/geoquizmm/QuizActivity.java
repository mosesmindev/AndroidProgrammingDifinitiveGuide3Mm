package com.mosesmin.android.geoquizmm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
//    private Button mNextButton;
//    private Button mPrevButton;
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
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = findViewById(R.id.question_text_view);
        //int question = mQuestionBankArray[mCurrentIndex].getTextResId();
        //mQuestionTextView.setText(question);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1)%mQuestionBankArray.length;
                updateQuestion();
            }
        });
        updateQuestion();

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
                mCurrentIndex = (mCurrentIndex + 1)%mQuestionBankArray.length;
                //int question = mQuestionBankArray[mCurrentIndex].getTextResId();
                //mQuestionTextView.setText(question);
                updateQuestion();
            }
        });
        updateQuestion();

        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex>0){
                    mCurrentIndex = (mCurrentIndex - 1)%mQuestionBankArray.length;
                }else{
                    mCurrentIndex = mQuestionBankArray.length -1;
                }
                //int question = mQuestionBankArray[mCurrentIndex].getTextResId();
                //mQuestionTextView.setText(question);
                updateQuestion();
            }
        });
        updateQuestion();
    }

    private void updateQuestion(){
        int question = mQuestionBankArray[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerTrue = mQuestionBankArray[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if (userPressedTrue == answerTrue){
            messageResId = R.string.correct_toast;
        }else{
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }
}
