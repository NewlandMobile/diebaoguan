package com.lin.diebaoguan.fsb.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.diebaoguan.uibase.PullToRefreshBaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AiMeiZhiFragment extends PullToRefreshBaseFragment {


    private View view;

    public AiMeiZhiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), 0, false, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);

        }
        return view;
    }
}
