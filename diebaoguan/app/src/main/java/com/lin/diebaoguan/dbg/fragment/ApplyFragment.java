package com.lin.diebaoguan.dbg.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;

/**
 * 价格界面
 */
public class ApplyFragment extends PullToRefreshBaseFragment {


    private View view;

    public ApplyFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), R.layout.fragment_apply);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        return view;
    }
}
