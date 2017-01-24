package com.lin.diebaoguan.menu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.activity.GyjOriginalDetailsActivity;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Data;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.diebaoguan.network.send.NormalDS;
import com.lin.diebaoguan.uibase.BaseCollectFragment;
import com.lin.lib_volley_https.VolleyListener;

/**
 * 图片收藏界面
 */
public class PicFragment extends BaseCollectFragment implements AdapterView.OnItemClickListener {
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCid(3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
            refreshableView.setOnItemClickListener(this);
        }
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Result result = dataList.get(position - 1);
        fetchDetailWithPicId(Integer.valueOf(result.getDocid()).intValue());
    }

    private void fetchDetailWithPicId(int picid) {
        NormalDS params = new NormalDS();
        params.setPicid(picid);
        params.setModule("api_libraries_sjdbg_tudetail");
        params.setUid(MyAppication.getUid());
        params.setSize(500);
        CommonUtils.normalGetWayFetch(params, this, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.d(volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
                LogUtils.d(s);
                NormalResponse response = NormalResponse.parseObject(s, NormalResponse.class);
                LogUtils.d(response.toString());
                gotoDetaiActivity(response);
            }
        });
    }

    private void gotoDetaiActivity(NormalResponse response) {
        if (response == null)
            return;
        Data data = response.getData();
        if (response.getData() == null)
            return;
        Intent detailIntent = new Intent(getActivity(), GyjOriginalDetailsActivity.class);
        detailIntent.putExtra("Data", data);
        startActivity(detailIntent);
    }


}
