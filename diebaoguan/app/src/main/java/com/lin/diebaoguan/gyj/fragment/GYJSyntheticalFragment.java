package com.lin.diebaoguan.gyj.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.adapter.RefreshListAdapter;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Data;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.diebaoguan.uibase.PullToRefreshBaseFragment;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 综合
 * A simple {@link Fragment} subclass.
 */
public class GYJSyntheticalFragment extends PullToRefreshBaseFragment implements AdapterView.OnItemClickListener {


    private List<String> list = new ArrayList<>();
    private ListView refreshableView;
    private View view;
    private List<Result> dataList = new ArrayList<>();

    public GYJSyntheticalFragment() {
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
            refreshableView.setOnItemClickListener(this);
            getData();
        }
        return view;
    }

    private void getData() {
        CommonUtils.fetchDataAtGyjPage(GYJSyntheticalFragment.this, 1, 0, Const.ROWS, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.e(volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
                LogUtils.e(s);
                NormalResponse response = NormalResponse.parseObject(s, NormalResponse.class);
                Data data = response.getData();
                Result[] result = data.getResult();
                Collections.addAll(dataList, result);
                RefreshListAdapter adapter = new RefreshListAdapter(getActivity(), dataList);
                refreshableView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
