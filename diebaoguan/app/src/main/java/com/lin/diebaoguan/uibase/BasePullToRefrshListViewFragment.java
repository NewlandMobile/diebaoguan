package com.lin.diebaoguan.uibase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.ArticleDetailsActivity;
import com.lin.diebaoguan.adapter.RefreshListAdapter;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Paging;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.lib_volley_https.VolleyListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * It's Created by NewLand-JianFeng on 2017/1/11.
 */

public abstract class BasePullToRefrshListViewFragment extends PullToRefreshBaseFragment implements AdapterView.OnItemClickListener {
    private ListView refreshableView;
    private RefreshListAdapter adapter;
    private List<Result> dataList = new ArrayList<>();
    private int currentOffset = 0;//用于分页
    private int total;//总共数量
    private View view;
    private CommonListVolleyListener volleyListener = new CommonListVolleyListener();
    private CommonListRefreshListener refreshListener = new CommonListRefreshListener();

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
        getData(currentOffset, volleyListener);
        refreshableView.setOnItemClickListener(this);
        //下拉刷新
        basePullToRefreshListView.setOnRefreshListener(refreshListener);
    }

    private class CommonListRefreshListener implements PullToRefreshBase.OnRefreshListener2<ListView> {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            currentOffset = 0;
            getData(currentOffset, volleyListener);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            if (currentOffset >= total) {
                showToast(getString(R.string.alreadyatthebottom));
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
            }
            getData(currentOffset, volleyListener);
        }
    }

    protected abstract void getData(final int Offset, VolleyListener volleyListener);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtils.e("==" + position);
        String title = dataList.get(position - 1).getTitle();
        int docid = dataList.get(position - 1).getDocid();
        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", "" + docid);
        intent.putExtra("position", position - 1);
        intent.putExtra("datalsit", (Serializable) dataList);

        LogUtils.e("===id" + docid + "==" + "title" + title + "  position==" + position);
        startActivity(intent);
    }

    private class CommonListVolleyListener implements VolleyListener {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogUtils.d(volleyError.toString());
            basePullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onResponse(String s) {
            LogUtils.d(s);
            basePullToRefreshListView.onRefreshComplete();
            NormalResponse response = NormalResponse.parseObject(s, NormalResponse.class);
            LogUtils.d(response.toString());
            Result[] results = response.getData().getResult();
            for (Result result : results) {
                dataList.add(result);
            }

            Paging paging = response.getData().getPaging();
            total = paging.getTotal();
            refreshableView.post(new Runnable() {
                @Override
                public void run() {
                    refreshableView.smoothScrollToPosition(currentOffset - 1);
                }
            });
            currentOffset += Const.ROWS;
            adapter = new RefreshListAdapter(getActivity(), dataList);
            refreshableView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}
