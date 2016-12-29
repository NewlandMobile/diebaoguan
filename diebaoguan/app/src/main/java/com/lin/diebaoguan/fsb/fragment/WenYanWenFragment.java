package com.lin.diebaoguan.fsb.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lin.diebaoguan.adapter.RefreshListAdapter;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WenYanWenFragment extends PullToRefreshBaseFragment {

    private List<String> list = new ArrayList<>();
    private ListView refreshableView;
    private View view;

    public WenYanWenFragment() {
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
