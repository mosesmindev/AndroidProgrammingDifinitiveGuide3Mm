package com.mosesmin.android.criminalintentmm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    // 代码清单8-16 为CrimeListFragment配置视图（CrimeListFragment.java） -- start
    private RecyclerView mCrimeRecyclerView;
    // 代码清单8-20 设置Adapter（CrimeListFragment.java） -- start2
    private CrimeAdapter mAdapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        // 代码清单8-20 设置Adapter（CrimeListFragment.java） -- end2
        return view;
    }
    // 代码清单8-16 为CrimeListFragment配置视图（CrimeListFragment.java） -- end

    // 代码清单8-17 定义ViewHolder内部类（CrimeListFragment.java） -- start
    private class CrimeHolder extends RecyclerView.ViewHolder{
        public CrimeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
        }
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

        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
        // 代码清单8-19 武装CrimeAdapter（CrimeListFragment.java） -- end
    }

    // 代码清单8-20 设置Adapter（CrimeListFragment.java） -- start1
    private void updateUI(){
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }
    // 代码清单8-20 设置Adapter（CrimeListFragment.java） -- end1

}