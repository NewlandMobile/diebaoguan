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
import com.lin.diebaoguan.uibase.BasePullToRefrshListViewFragment;
import com.lin.diebaoguan.uibase.PullToRefreshBaseFragment;
import com.lin.diebaoguan.network.bean.Paging;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.lib_volley_https.VolleyListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 综合
 */
public class FSBSyntheticalFragment extends BasePullToRefrshListViewFragment {

    public FSBSyntheticalFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), 0, false, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }



    /**
     * 获取数据
     */
    protected void getData(final int offset) {
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


}
