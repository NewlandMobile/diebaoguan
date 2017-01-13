package com.lin.diebaoguan.dbg.fragment;


import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.uibase.BasePullToRefrshListViewFragment;
import com.lin.lib_volley_https.VolleyListener;

/**
 * 头条界面
 */
public class HeadlineFragment extends BasePullToRefrshListViewFragment {


    private View view;
    private ViewPager viewPager;

    public HeadlineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
            View headView = getActivity().getLayoutInflater().inflate(R.layout.view_ad, null);
            viewPager = (ViewPager) headView.findViewById(R.id.headline_viewpager);
            viewPager.setAdapter(new MyAdapter());
            refreshableView.addHeaderView(headView);
            adapter.notifyDataSetChanged();
        }
        return view;
    }

    @Override
    protected void getData(int pageOffset, VolleyListener volleyListener) {
        CommonUtils.fetchDataAtFsbOrDbg(pageOffset, this, true, 4, volleyListener);
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}