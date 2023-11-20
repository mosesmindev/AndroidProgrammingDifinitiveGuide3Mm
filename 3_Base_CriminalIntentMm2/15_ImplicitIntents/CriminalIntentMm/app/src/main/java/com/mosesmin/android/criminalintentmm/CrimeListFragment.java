package com.mosesmin.android.criminalintentmm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @Version: 1.0
 * @Misson&Goal: 代码以交朋友、传福音
 * @Project:CriminalIntentMm
 * @Description: TODO
 * @Author: MosesMin
 * @Date: 2023-10-30 22:03:50
 */
// 代码清单8-11 实现CrimeListFragment（CrimeListFragment.java） -- start
public class CrimeListFragment extends Fragment {
    private static final String TAG = "CrimeListFragment";
    // 代码清单8-11 实现CrimeListFragment（CrimeListFragment.java） -- end

    // 代码清单13-19 保存子标题状态值（CrimeListFragment.java） -- start1
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";
    // 代码清单13-19 保存子标题状态值（CrimeListFragment.java） -- end1

    // 代码清单8-16 为CrimeListFragment配置视图（CrimeListFragment.java） -- start1
    private RecyclerView mCrimeRecyclerView;
    // 代码清单8-16 为CrimeListFragment配置视图（CrimeListFragment.java） -- end1

    // 代码清单8-20 设置Adapter（CrimeListFragment.java） -- start2
    private CrimeAdapter mAdapter;
    // 代码清单8-20 设置Adapter（CrimeListFragment.java） -- end2

    // 代码清单13-15 记录子标题状态（CrimeListFragment.java） -- start
    private boolean mSubtitleVisible;
    // 代码清单13-15 记录子标题状态（CrimeListFragment.java） -- end

    // 代码清单13-7 调用 setHasOptionsMenu 方法（CrimeListFragment.java） -- start
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    // 代码清单13-7 调用 setHasOptionsMenu 方法（CrimeListFragment.java） -- end

    // 代码清单8-16 为CrimeListFragment配置视图（CrimeListFragment.java） -- start2
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 代码清单13-19 保存子标题状态值（CrimeListFragment.java） -- start2
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        // 代码清单13-19 保存子标题状态值（CrimeListFragment.java） -- end2

        // 代码清单8-20 设置Adapter（CrimeListFragment.java） -- start3
        updateUI();
        // 代码清单8-20 设置Adapter（CrimeListFragment.java） -- end3
        return view;
    }
    // 代码清单8-16 为CrimeListFragment配置视图（CrimeListFragment.java） -- end2


    // 代码清单10-9 在 onResume() 方法中刷新列表项（CrimeListFragment.java） -- start1
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    // 代码清单10-9 在 onResume() 方法中刷新列表项（CrimeListFragment.java） -- end1

    // 代码清单13-19 保存子标题状态值（CrimeListFragment.java） -- start3
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }
    // 代码清单13-19 保存子标题状态值（CrimeListFragment.java） -- end3

    // 代码清单13-6 实例化选项菜单（CrimeListFragment.java） -- start
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);

        // 代码清单13-16 更新菜单项（CrimeListFragment.java） -- start1
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
        // 代码清单13-16 更新菜单项（CrimeListFragment.java） -- end1
    }
    // 代码清单13-6 实例化选项菜单（CrimeListFragment.java） -- end

    // 代码清单13-10 响应菜单项选择事件（CrimeListFragment.java） -- start
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.getInstance(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                // 代码清单13-16 更新菜单项（CrimeListFragment.java） -- start2
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                // 代码清单13-16 更新菜单项（CrimeListFragment.java） -- end2
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // 代码清单13-10 响应菜单项选择事件（CrimeListFragment.java） -- end

    // 代码清单13-13 设置工具栏子标题（CrimeListFragment.java） -- start
    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format,crimeCount,0);

        // 代码清单13-17 实现菜单项标题与子标题的联动（CrimeListFragment.java） -- start
        if (!mSubtitleVisible){
            subtitle = null;
        }
        // 代码清单13-17 实现菜单项标题与子标题的联动（CrimeListFragment.java） -- end

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }
    // 代码清单13-13 设置工具栏子标题（CrimeListFragment.java） -- end

    // 代码清单8-17 定义ViewHolder内部类（CrimeListFragment.java） -- start
    private class CrimeHolder extends RecyclerView.ViewHolder
        // 代码清单8-24 检测用户点击事件（CrimeListFragment.java） -- start1
        implements View.OnClickListener{
        // 代码清单8-24 检测用户点击事件（CrimeListFragment.java） -- end1

        // 代码清单8-22 实现bind(Crime)方法（CrimeListFragment.java） -- start1
        private Crime mCrime;
        // 代码清单8-22 实现bind(Crime)方法（CrimeListFragment.java） -- end1

        // 代码清单8-21 在构造方法中实例化视图组件（CrimeListFragment.java） -- start1
        private TextView mTitleTextView;
        private TextView mDateTextView;
        // 代码清单8-21 在构造方法中实例化视图组件（CrimeListFragment.java） -- end1

        // 代码清单9-3 控制图片显示（CrimeListFragment.java） -- start1
        private ImageView mSolvedImageView;
        // 代码清单9-3 控制图片显示（CrimeListFragment.java） -- end1

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            // 代码清单8-24 检测用户点击事件（CrimeListFragment.java） -- start2
            itemView.setOnClickListener(this);
            // 代码清单8-24 检测用户点击事件（CrimeListFragment.java） -- end2

            // 代码清单8-21 在构造方法中实例化视图组件（CrimeListFragment.java） -- start2
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            // 代码清单8-21 在构造方法中实例化视图组件（CrimeListFragment.java） -- end2

            // 代码清单9-3 控制图片显示（CrimeListFragment.java） -- start2
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
            // 代码清单9-3 控制图片显示（CrimeListFragment.java） -- end2
        }

        // 代码清单8-22 实现bind(Crime)方法（CrimeListFragment.java） -- start2
        // 每次有新的Crime要在CrimeHolder中显示时，都要调用bind方法一次。
        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());

            // 代码清单9-3 控制图片显示（CrimeListFragment.java） -- start3
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE:View.GONE);
            // 代码清单9-3 控制图片显示（CrimeListFragment.java） -- end3
        }

        // 代码清单8-24 检测用户点击事件（CrimeListFragment.java） -- start3
        @Override
        public void onClick(View v) {
            /*Toast.makeText(getActivity(),
                    mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();*/
            // 代码清单10-1 启动 CrimeActivity （CrimeListFragment.java） -- start
            // 代码清单10-3 传递 Crime 实例（CrimeListFragment.java） -- start
            //Intent intent = new Intent(getActivity(),CrimeActivity.class);
            // 代码清单11-4 配置启动 CrimePagerActivity （CrimeListFragment.java） -- start
            // Intent intent =CrimeActivity.newIntent(getActivity(),mCrime.getId());
            Intent intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            // 代码清单11-4 配置启动 CrimePagerActivity （CrimeListFragment.java） -- end
            // 代码清单10-3 传递 Crime 实例（CrimeListFragment.java） -- end
            startActivity(intent);
            // 代码清单10-1 启动 CrimeActivity （CrimeListFragment.java） -- end
        }
        // 代码清单8-24 检测用户点击事件（CrimeListFragment.java） -- end3

        // 代码清单8-22 实现bind(Crime)方法（CrimeListFragment.java） -- end2
    }
    // 代码清单8-17 定义ViewHolder内部类（CrimeListFragment.java） -- end

    // 代码清单8-18 创建Adapter内部类（CrimeListFragment.java） -- start
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;
        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }
        // 代码清单8-18 创建Adapter内部类（CrimeListFragment.java） -- end

        // 代码清单8-19 武装CrimeAdapter（CrimeListFragment.java） -- start
        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder: ViewHolder+1");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            // 代码清单8-23 调用bind(Crime)方法（CrimeListFragment.java） -- start
            /*
            修改 CrimeAdapter 类，使用 bind(Crime) 方法。每次 RecyclerView 要求 CrimeHolder
            绑定对应的 Crime 时 ， 都会调用 bind(Crime) 方法
             */
            Crime crime =mCrimes.get(position);
            holder.bind(crime);
            // 代码清单8-23 调用bind(Crime)方法（CrimeListFragment.java） -- end
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
        // 代码清单8-19 武装CrimeAdapter（CrimeListFragment.java） -- end

        // 代码清单14-20 添加 setCrimes(List<Crime>) 方法（CrimeListFragment.java） -- start
        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }
        // 代码清单14-20 添加 setCrimes(List<Crime>) 方法（CrimeListFragment.java） -- end
    }

    // 代码清单8-20 设置Adapter（CrimeListFragment.java） -- start1
    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        // 代码清单10-9 在 onResume() 方法中刷新列表项（CrimeListFragment.java） -- start2
        if (mAdapter == null){
        // 代码清单10-9 在 onResume() 方法中刷新列表项（CrimeListFragment.java） -- end2
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
            // 代码清单10-9 在 onResume() 方法中刷新列表项（CrimeListFragment.java） -- start3
        }else{
            // 代码清单14-21 调用 setCrimes(List<Crime>) 方法（CrimeListFragment.java） -- start
            mAdapter.setCrimes(crimes);
            // 代码清单14-21 调用 setCrimes(List<Crime>) 方法（CrimeListFragment.java） -- end
            mAdapter.notifyDataSetChanged();
        }
        // 代码清单10-9 在 onResume() 方法中刷新列表项（CrimeListFragment.java） -- end3

        // 代码清单13-18 显示最新状态（CrimeListFragment.java） 后退键返回CrimeListActivity 界面更新子标题的记录数刷新  -- start
        updateSubtitle();
        // 代码清单13-18 显示最新状态（CrimeListFragment.java） 后退键返回CrimeListActivity 界面更新子标题的记录数刷新  -- end
    }
    // 代码清单8-20 设置Adapter（CrimeListFragment.java） -- end1

}