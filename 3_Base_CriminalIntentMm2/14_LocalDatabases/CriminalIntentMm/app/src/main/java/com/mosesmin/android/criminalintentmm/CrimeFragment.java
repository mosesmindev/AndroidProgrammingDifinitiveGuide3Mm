package com.mosesmin.android.criminalintentmm;

// 代码清单7-9 导入支持库版 Fragment（CrimeFragment.java）
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

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
    // 代码清单10-6 编写 newInstance(UUID) 方法（CrimeFragment.java） -- start1
    public static final String ARG_CRIME_ID = "crime_id";
    // 代码清单10-6 编写 newInstance(UUID) 方法（CrimeFragment.java） -- end1
    // 代码清单12-3 显示 DialogFragment （CrimeFragment.java） -- start1
    private static final String DIALOG_DATE = "DialogDate";
    // 代码清单12-3 显示 DialogFragment （CrimeFragment.java） -- end1

    // 代码清单12-8 设置目标fragment（CrimeFragment.java） -- start1
    public static final int REQUEST_CODE = 0;
    // 代码清单12-8 设置目标fragment（CrimeFragment.java） -- end1


    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    // 代码清单10-6 编写 newInstance(UUID) 方法（CrimeFragment.java） -- start2
    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    // 代码清单10-6 编写 newInstance(UUID) 方法（CrimeFragment.java） -- end2

    // 代码清单7-10 覆盖 Fragment.onCreate(Bundle) 方法（CrimeFragment.java）
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 代码清单10-4 获取extra数据并取得 Crime 对象（CrimeFragment.java） -- start
        //mCrime = new Crime();

        // 代码清单10-8 从argument中获取crime ID（CrimeFragment.java） -- start1
        /*UUID crimeId = (UUID) getActivity().getIntent()
                .getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);*/
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        // 代码清单10-8 从argument中获取crime ID（CrimeFragment.java） -- end1
        mCrime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
        // 代码清单10-4 获取extra数据并取得 Crime 对象（CrimeFragment.java） -- end
    }

    // 代码清单14-11  Crime 数据刷新（CrimeFragment.java） -- start
    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.getInstance(getActivity())
                .updateCrime(mCrime);
    }
    // 代码清单14-11  Crime 数据刷新（CrimeFragment.java） -- end

    // 代码清单7-11 覆盖onCreateView(...)方法（CrimeFragment.java） -- start
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime,container,false);

        // 代码清单7-12 生成并使用EditText组件（CrimeFragment.java）-- start
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        // 代码清单10-5 更新视图对象（CrimeFragment.java） -- start1
        mTitleField.setText(mCrime.getTitle());
        // 代码清单10-5 更新视图对象（CrimeFragment.java） -- end1
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
        // 代码清单12-13 使用 updateDate() 私有方法（CrimeFragment.java） -- start3
        updateDate();
        // 代码清单12-13 使用 updateDate() 私有方法（CrimeFragment.java） -- end3
        // 代码清单12-3 显示 DialogFragment （CrimeFragment.java） -- start2
        // mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                // 代码清单12-6 添加 newInstance() 方法（CrimeFragment.java） -- start
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mCrime.getDate());
                // 代码清单12-8 设置目标fragment（CrimeFragment.java） -- start2
                dialog.setTargetFragment(CrimeFragment.this,REQUEST_CODE);
                // 代码清单12-8 设置目标fragment（CrimeFragment.java） -- end2
                // 代码清单12-6 添加 newInstance() 方法（CrimeFragment.java） -- end
                dialog.show(manager, DIALOG_DATE);
            }
        });
        // 代码清单12-3 显示 DialogFragment （CrimeFragment.java） -- end2

        // 代码清单7-13 设置Button文字（CrimeFragment.java） -- end

        // 代码清单7-14 监听CheckBox的变化（CrimeFragment.java） -- start
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        // 代码清单10-5 更新视图对象（CrimeFragment.java） -- start2
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        // 代码清单10-5 更新视图对象（CrimeFragment.java） -- end2
        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        // 代码清单7-14 监听CheckBox的变化（CrimeFragment.java） -- end
        return v;
    }

    // 代码清单12-11 响应 DatePicker 对话框（CrimeFragment.java） -- start
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_CODE){
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            // 代码清单12-12 高亮选取日期按钮更新代码（CrimeFragment.java） -- start
            // mDateButton.setText(mCrime.getDate().toString());  // Refactor → Extract Method...
            // 代码清单12-12 高亮选取日期按钮更新代码（CrimeFragment.java） -- end
            // 代码清单12-13 使用 updateDate() 私有方法（CrimeFragment.java） -- start2
            updateDate();
            // 代码清单12-13 使用 updateDate() 私有方法（CrimeFragment.java） -- end2
        }
    }


    // 代码清单12-13 使用 updateDate() 私有方法（CrimeFragment.java） -- start1
    private void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }
    // 代码清单12-13 使用 updateDate() 私有方法（CrimeFragment.java） -- end1
    // 代码清单12-11 响应 DatePicker 对话框（CrimeFragment.java） -- end
    // 代码清单7-11 覆盖onCreateView(...)方法（CrimeFragment.java） -- end
}