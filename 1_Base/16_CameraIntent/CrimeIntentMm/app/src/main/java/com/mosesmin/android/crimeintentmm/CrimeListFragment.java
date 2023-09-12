package com.mosesmin.android.crimeintentmm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.INotificationSideChannel;
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
import android.widget.Toast;

import java.util.List;

/**
 *
 * @Misson&Goal 代码以交朋友、传福音
 * @ClassName CrimeListFragment
 * @Description TODO  核心的RecyclerView使用示例类
 * @Author MosesMin
 * @Date 2023-08-28 22:30:16
 * @Version 1.0
 *
 */
public class CrimeListFragment extends Fragment {
    private static final String TAG = "CrimeListFragment";

    // 13.4.2 13-19 保存子标题状态值
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";


    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    // 13.4.1 13-15 记录子标题状态
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list,container,false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 13.4.2 13-19 保存子标题状态值
        if (savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    // 13.4.2 13-19 保存子标题状态值
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);

        // 13.4.1  13-16 更新菜单项
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.getInstance(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;

            // 13.4  13-14 调用updateSubtitle()方法响应新增菜单项的单击事件，显示crime条数
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                // 13.4.1  13-16 更新菜单项
                getActivity().invalidateOptionsMenu();
                updateSubtitle();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 13.4 13-13 设置工具栏子标题，子标题需显示crime记录条数
    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        int crimeCount = crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format,crimeCount);

        // 13.4.1 13-17 实现菜单项标题与子标题的联动
        if (!mSubtitleVisible){
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mAdapter == null){
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else{
            // 14.5.2 创建模型层对象 14-21 调用setCrimes(List<Crime>)方法
            mAdapter.setCrimes(crimes);

            mAdapter.notifyDataSetChanged();
        }

        // 13.4.2 解决：子标题显示后，旋转设备，子标题显示的总记录数不会更新以及显示的子标题会消失；
        // 在updateUI方法中调用updateSubtitle方法，问题得以解决；本质是在onResume中调用updateSubtitle
        updateSubtitle();
    }

    /**
     * ViewHolder: 容纳View，每个ItemView都需要有一个ViewHolder
     */
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Crime mCrime;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        public CrimeHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime,parent,false));
            Log.i(TAG, "CrimeHolder: " + this.getClass().getName());
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "onClick: Hosting Activity is :" + getActivity().getClass().getName());
            Intent intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
        }
    }

    /**
     * Adapter：为RecyclerView创建ViewHolder，同时将ItemView所需的数据获取并绑定到ViewHolder
     */
    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>{
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CrimeHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

        // 14.5.2 创建模型层对象 14-20 添加setCrimes(List<Crime>)方法
        public void setCrimes(List<Crime> crimes) {
            mCrimes = crimes;
        }
    }

}