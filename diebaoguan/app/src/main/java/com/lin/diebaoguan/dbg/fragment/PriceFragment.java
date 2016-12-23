package com.lin.diebaoguan.dbg.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PriceFragment extends PullToRefreshBaseFragment {


    private View view;

    public PriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }
        return view;
    }
}
