package com.mosesmin.android.criminalintentmm;

// 代码清单7-9 导入支持库版 Fragment（CrimeFragment.java）
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import static android.widget.CompoundButton.*;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-10-25 23:23:43
 */
// 代码清单7-8 继承 Fragment类（CrimeFragment.java）
public class CrimeFragment extends Fragment {
    private static final String TAG = "CrimeFragment";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    // 代码清单7-10 覆盖 Fragment.onCreate(Bundle) 方法（CrimeFragment.java）
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }

    // 代码清单7-11 覆盖onCreateView(...)方法（CrimeFragment.java） -- start
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime,container,false);

        // 代码清单7-12 生成并使用EditText组件（CrimeFragment.java）-- start
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank 这个空格故意留空
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This space intentionally left blank 这个空格故意留空  This one too
            }
        });
        // 代码清单7-12 生成并使用EditText组件（CrimeFragment.java）-- end

        // 代码清单7-13 设置Button文字（CrimeFragment.java） -- start
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());
        mDateButton.setEnabled(false);
        // 代码清单7-13 设置Button文字（CrimeFragment.java） -- end

        // 代码清单7-14 监听CheckBox的变化（CrimeFragment.java） -- start
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        // 代码清单7-14 监听CheckBox的变化（CrimeFragment.java） -- end

        return v;
    }
    // 代码清单7-11 覆盖onCreateView(...)方法（CrimeFragment.java） -- end
}