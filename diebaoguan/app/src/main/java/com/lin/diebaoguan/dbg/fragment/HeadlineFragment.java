package com.lin.diebaoguan.dbg.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;

/**
 * 头条界面
 */
public class HeadlineFragment extends PullToRefreshBaseFragment {


    private View view;

    public HeadlineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
            baseLinearLayout.setVisibility(View.VISIBLE);
            baseLinearLayout.setBackgroundResource(R.drawable.btn_long_bg);
        }
        return view;
    }
}
