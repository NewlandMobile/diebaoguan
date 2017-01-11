package com.lin.diebaoguan.uibase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.ArticleDetailsActivity;
import com.lin.diebaoguan.adapter.RefreshListAdapter;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * It's Created by NewLand-JianFeng on 2017/1/11.
 */

public abstract class BasePullToRefrshListViewFragment extends PullToRefreshBaseFragment implements AdapterView.OnItemClickListener {
    protected ListView refreshableView;
    protected RefreshListAdapter adapter;
    protected List<Result> dataList = new ArrayList<>();
    protected int currentOffset = 0;//用于分页
    protected int total;//总共数量
    protected View view;

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
        getData(0);
        refreshableView.setOnItemClickListener(this);
        //下拉刷新
        basePullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentOffset = 0;
                getData(0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (currentOffset >= total) {
                    showToast(getString(R.string.alreadyatthebottom));
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                }
                getData(currentOffset);
            }
        });
    }

    protected abstract void getData(final int Offset);

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
}
