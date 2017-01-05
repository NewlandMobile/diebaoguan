package com.lin.diebaoguan.fsb.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lin.diebaoguan.activity.ArticleDetailsActivity;
import com.lin.diebaoguan.adapter.RefreshListAdapter;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.diebaoguan.network.send.DieBaoGuanAndFengShangBiaoDS;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 综合
 */
public class FSBSyntheticalFragment extends PullToRefreshBaseFragment implements AdapterView.OnItemClickListener {

    private RefreshListAdapter adapter;
    private List<Result> dataList = new ArrayList<>();

    public FSBSyntheticalFragment() {
    }

    private ListView refreshableView;
    private View view;
    private DieBaoGuanAndFengShangBiaoDS sendParams = new DieBaoGuanAndFengShangBiaoDS();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), 0, false, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
            initView();
        }
        return view;
    }

    private void initView() {
        refreshableView = basePullToRefreshListView.getRefreshableView();
        getData(false);
        refreshableView.setOnItemClickListener(this);
        //下拉刷新
        basePullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                basePullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                basePullToRefreshListView.onRefreshComplete();
            }
        });
    }

    /**
     * 获取数据
     */
    private void getData(final boolean isRefresh) {
        CommonUtils.fetchDataFromNetWork(FSBSyntheticalFragment.this, true, 1, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.d(volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
                LogUtils.d(s);
                NormalResponse response = NormalResponse.
                        parseObject(s, NormalResponse.class);
                LogUtils.d(response.toString());
                Result[] results = response.getData().getResult();
                for (Result result : results) {
                    dataList.add(result);
                }
                adapter = new RefreshListAdapter(getActivity(), dataList);
                refreshableView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                //TODO 刷新判断
//                if(isRefresh){
//                    basePullToRefreshListView.dele
//                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String title = dataList.get(position).getTitle();
        int docid = dataList.get(position).getDocid();
        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", "" + docid);
        startActivity(intent);
    }
}
