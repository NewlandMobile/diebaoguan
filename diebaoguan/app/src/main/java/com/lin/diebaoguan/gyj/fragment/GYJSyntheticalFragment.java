package com.lin.diebaoguan.gyj.fragment;


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
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.ArticleDetailsActivity;
import com.lin.diebaoguan.adapter.RefreshListAdapter;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Paging;
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


    private ListView refreshableView;
    private View view;
    private List<Result> dataList = new ArrayList<>();
    private int currentPageOffset = 0;//  当前已加载多少页
    private int totalPage;//总共有多少页
    private RefreshListAdapter adapter;
    private String uid;//用户uid

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
            basePullToRefreshListView.setOnRefreshListener(new CommonListRefreshListener());
            adapter = new RefreshListAdapter(getActivity(), dataList);
            refreshableView.setAdapter(adapter);
            getData(0, volleyListener);
        }
        return view;
    }

    private void getData(int offset, VolleyListener volleyListener) {
        CommonUtils.fetchDataAtGyjPage(GYJSyntheticalFragment.this, 1, offset, volleyListener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Result result = dataList.get(position);
        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
        intent.putExtra("currentOffset", position - 1);
        intent.putExtra("allItem", dataList.toArray());
        intent.putExtra("ActivityLog", "GYJSYNTH");
        startActivity(intent);

//        if (MyAppication.getInstance().hasLogined()) {
//            uid = MyAppication.getUid();
//        }
//        ArticleDetailDS sendParam = new ArticleDetailDS();
//        sendParam.setModule("api_libraries_sjdbg_detail");
//        sendParam.initTimePart();
//        sendParam.setDocid("" + result.getPicid());
//        sendParam.setSize("" + 500);
//        sendParam.setOffset("" + 0);
//        if (MyAppication.getInstance().hasLogined()) {
//            sendParam.setUid(uid);
//        }
//        CommonUtils.httpGet(sendParam.parseParams(), new VolleyListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                LogUtils.e(volleyError.toString());
//                Toast.makeText(getActivity(), getString(R.string.getdatafail) + volleyError.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(String s) {
//                LogUtils.e(s);
//            }
//        });


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

    private VolleyListener volleyListener = new VolleyListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogUtils.e(volleyError.toString());
        }

        @Override
        public void onResponse(String s) {
            LogUtils.e(s);
            basePullToRefreshListView.onRefreshComplete();
            NormalResponse response = NormalResponse.parseObject(s, NormalResponse.class);
            LogUtils.d(response.toString());
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
    };

}
