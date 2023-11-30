package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:My Application
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-11-30 21:27:24
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }
}