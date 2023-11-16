package com.mosesmin.android.criminalintentmm;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-15 22:40:55
 */
                                // 代码清单12-2 创建 DialogFragment （DatePickerFragment.java） -- start
public class DatePickerFragment extends DialogFragment {
    private static final String TAG = "DatePickerFragment";
    // 代码清单12-9 回调目标fragment（DatePickerFragment.java） -- start1
    public static final String EXTRA_DATE = "com.mosesmin.android.criminalintentmm.date";
    // 代码清单12-9 回调目标fragment（DatePickerFragment.java） -- end1

    // 代码清单12-5 添加 newInstance(Date) 方法（DatePickerFragment.java） -- start
    private static final String ARG_DATE = "date";
    private DatePicker mDatePicker;
    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    // 代码清单12-5 添加 newInstance(Date) 方法（DatePickerFragment.java） -- end

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 代码清单12-7 获取 Date 对象并初始化 DatePicker （DatePickerFragment.java） -- start1
        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // 代码清单12-7 获取 Date 对象并初始化 DatePicker （DatePickerFragment.java） -- end1

        // 代码清单12-4 给 AlertDialog 添加 DatePicker （DatePickerFragment.java） -- start1
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
        // 代码清单12-4 给 AlertDialog 添加 DatePicker （DatePickerFragment.java） -- end1

        // 代码清单12-7 获取 Date 对象并初始化 DatePicker （DatePickerFragment.java） -- start2
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year, month, day, null);
        // 代码清单12-7 获取 Date 对象并初始化 DatePicker （DatePickerFragment.java） -- end2

        return new AlertDialog.Builder(getActivity())
                // 代码清单12-4 给 AlertDialog 添加 DatePicker （DatePickerFragment.java） -- start1
                .setView(v)
                // 代码清单12-4 给 AlertDialog 添加 DatePicker （DatePickerFragment.java） -- end1
                .setTitle(R.string.date_picker_title)
                // 代码清单12-10 一切是否都OK？（DatePickerFragment.java） -- start
                //.setPositiveButton(android.R.string.ok, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year,month,day).getTime();
                        sendResult(Activity.RESULT_OK,date);
                    }
                })
                // 代码清单12-10 一切是否都OK？（DatePickerFragment.java） -- end
                .create();
    }

    // 代码清单12-9 回调目标fragment（DatePickerFragment.java） -- start2
    private void sendResult(int resultCode,Date date){
        if (getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
    // 代码清单12-9 回调目标fragment（DatePickerFragment.java） -- end2
    // 代码清单12-2 创建 DialogFragment （DatePickerFragment.java） -- end
}