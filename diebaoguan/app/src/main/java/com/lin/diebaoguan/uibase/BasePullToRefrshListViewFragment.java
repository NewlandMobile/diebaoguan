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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * It's Created by NewLand-JianFeng on 2017/1/11.
 */

public abstract class BasePullToRefrshListViewFragment extends PullToRefreshBaseFragment implements AdapterView.OnItemClickListener {
    protected ListView refreshableView;
    protected RefreshListAdapter adapter;
    protected List<Result> dataList = new ArrayList<>();
    //改用 记页数 传递页数的方式
    private int currentPageOffset = 0;//  当前已加载多少页
    private int totalPage;//总共有多少页
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
        getData(currentPageOffset, volleyListener);
        refreshableView.setOnItemClickListener(this);
        adapter = new RefreshListAdapter(getActivity(), dataList);
        refreshableView.setAdapter(adapter);
        //下拉刷新
        basePullToRefreshListView.setOnRefreshListener(refreshListener);
    }

    private class CommonListRefreshListener implements PullToRefreshBase.OnRefreshListener2<ListView> {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            currentPageOffset = 0;
            getData(currentPageOffset, volleyListener);
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            if (currentPageOffset >= totalPage) {
                showToast(getString(R.string.alreadyatthebottom));
            }
            getData(currentPageOffset, volleyListener);
        }
    }

    protected abstract void getData(final int pageOffset, VolleyListener volleyListener);

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
        intent.putExtra("currentOffset", position - 1);
        intent.putExtra("allItem", dataList.toArray());
        addSpecialArgu(intent);
        startActivity(intent);
    }

    protected void addSpecialArgu(Intent intent) {

    }

    private class CommonListVolleyListener implements VolleyListener {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogUtils.d(volleyError.toString());
            basePullToRefreshListView.onRefreshComplete();
        }

        @Override
        public void onResponse(String s) {
            //LogUtils.d(s);
            basePullToRefreshListView.onRefreshComplete();
            NormalResponse response = NormalResponse.parseObject(s, NormalResponse.class);
            //  LogUtils.d(response.toString());
            Result[] results = response.getData().getResult();
            // 假如是下拉刷新而不是上拉加载更多，需要先清旧数据，再加
            if (currentPageOffset == 0) {
                dataList.clear();
            }
            // 这行是新写法，下次要记住
            Collections.addAll(dataList, results);
            adapter.notifyDataSetChanged();
            Paging paging = response.getData().getPaging();
            totalPage = paging.getPages();
            final int offset = currentPageOffset * Const.ROWS - 1;
            refreshableView.post(new Runnable() {
                @Override
                public void run() {
                    refreshableView.smoothScrollToPosition(offset);
                }
            });
            currentPageOffset += 1;
        }
    }
}
