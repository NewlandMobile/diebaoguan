package com.lin.diebaoguan.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.adapter.RefreshListAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AiMeiFangFragment extends BaseFragment {
    public PullToRefreshListView pullToRefreshListView;//用于实现下拉刷新listview控件

    public AiMeiFangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), getResources().getString(R.string.aimeifang), R.layout.fragment_ai_mei_fang);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.amf_refresh_list);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);//两端刷新
        ListView refreshableView = pullToRefreshListView.getRefreshableView();
        refreshableView.setAdapter(new RefreshListAdapter(getActivity(), new ArrayList<String>()));
        return view;
    }
}
