package com.lin.diebaoguan.dbg.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.adapter.RefreshListAdapter;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.DieBaoGuanAndFengShangBiaoResponse;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 价格界面
 */
public class ApplyFragment extends PullToRefreshBaseFragment {


    private View view;
    private List<String> list = new ArrayList<>();
    private ListView refreshableView;

    public ApplyFragment() {
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
            final ProgressDialog progressDialog = CommonUtils.showProgressDialog(getActivity());
            progressDialog.show();
            view = super.onCreateView(inflater, container, savedInstanceState);
            refreshableView = basePullToRefreshListView.getRefreshableView();
            //获取数据
            final List<Result> list = new ArrayList<>();
            CommonUtils.fetchDataFromNetWork(false, 4, new VolleyListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtils.d(volleyError.toString());
                }

                @Override
                public void onResponse(String s) {
                    LogUtils.d(s);
                    DieBaoGuanAndFengShangBiaoResponse response = DieBaoGuanAndFengShangBiaoResponse.
                            parseObject(s, DieBaoGuanAndFengShangBiaoResponse.class);
                    LogUtils.d(response.toString());
                    Result[] results = response.getData().getResult();
                    for (Result result : results) {
                        list.add(result);
                    }
                    refreshableView.setAdapter(new RefreshListAdapter(getActivity(), list));
                    progressDialog.dismiss();
                }
            });
        }
        return view;
    }
}
