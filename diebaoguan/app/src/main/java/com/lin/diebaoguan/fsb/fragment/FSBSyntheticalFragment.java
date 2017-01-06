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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.ArticleDetailsActivity;
import com.lin.diebaoguan.adapter.RefreshListAdapter;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;
import com.lin.diebaoguan.network.bean.Paging;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.diebaoguan.network.send.NormalDS;
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
    private int currentOffset = 0;//用于分页
    private int total;//总共数量

    public FSBSyntheticalFragment() {
    }

    private ListView refreshableView;
    private View view;
    private NormalDS sendParams = new NormalDS();

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
                    Toast.makeText(getActivity(), R.string.alreadyatthebottom, Toast.LENGTH_SHORT).show();
                }
                getData(currentOffset);
            }
        });
    }

    /**
     * 获取数据
     */
    private void getData(final int offset) {
        CommonUtils.fetchDataAtFsbOrDbg(offset, FSBSyntheticalFragment.this, true, 1, new VolleyListener() {
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
                        refreshableView.smoothScrollToPosition(offset - 1);
                    }
                });
                currentOffset += Const.ROWS;
                adapter = new RefreshListAdapter(getActivity(), dataList);
                refreshableView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtils.e("==" + position);
        String title = dataList.get(position-1).getTitle();
        int docid = dataList.get(position-1).getDocid();
        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("id", "" + docid);
        LogUtils.e("===id" + docid + "==" + "title" + title);
        startActivity(intent);
    }
}
