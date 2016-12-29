package com.lin.diebaoguan.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class FengShangBiaoFragment extends EvolveFragment {


    public FengShangBiaoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), getResources().getString(R.string.fengshanbiao), Const.FSB_TOPICS, Const.FSB_Fragments);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }
}
