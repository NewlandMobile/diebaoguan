package com.lin.diebaoguan.dbg.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lin.diebaoguan.adapter.RefreshListAdapter;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 体验界面
 */
public class ExperienceFragment extends PullToRefreshBaseFragment {


    private View view;
    private List<String> list = new ArrayList<>();
    private ListView refreshableView;


    public ExperienceFragment() {
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
            refreshableView = basePullToRefreshListView.getRefreshableView();
            refreshableView.setAdapter(new RefreshListAdapter(getActivity(), list));
        }
        return view;
    }
}
