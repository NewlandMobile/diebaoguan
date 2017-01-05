package com.lin.diebaoguan.dbg.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PriceFragment extends PullToRefreshBaseFragment {


    private View view;
    private List<String> list = new ArrayList<>();
    private ListView refreshableView;

    public PriceFragment() {
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
                    NormalResponse response = NormalResponse.
                            parseObject(s, NormalResponse.class);
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

    public void getData() {
        long timeMillis = System.currentTimeMillis();
        Log.e("timeMillis", "==" + timeMillis);
        Map<String, String> params = new HashMap<>();

        params.put("apiid", 3 + "");
        params.put("timestamp", "" + timeMillis / 1000);
        String token1 = CommonUtils.md5("d19cf361181f5a169c107872e1f5b722" + timeMillis / 1000);
        params.put("token1", token1);

        params.put("module", "api_libraries_sjdbg_articlelist");
        params.put("returnformat", "json");
        params.put("encoding", "utf8");
        params.put("isclass", 0 + "");
        params.put("cid", 2 + "");
        params.put("offset", 0 + "");
        params.put("rows", 15 + "");

        CommonUtils.httpGet(params, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("VolleyError", "==" + volleyError);
            }

            @Override
            public void onResponse(String s) {
                Log.e("onResponse", "==" + s);

            }
        });
    }
}
