package com.lin.diebaoguan.dbg.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;
import com.lin.lib_volley_https.VolleyListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 头条界面
 */
public class HeadlineFragment extends PullToRefreshBaseFragment {


    private View view;

    public HeadlineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), R.layout.fragment_headline,true,false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);
//            baseLinearLayout.setVisibility(View.VISIBLE);
//            baseLinearLayout.setBackgroundResource(R.drawable.btn_long_bg);
//            getData();
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

        params.put("module", "api_libraries_sjdbg_startuplogo");
        params.put("returnformat", "json");
        params.put("encoding", "utf8");
//        params.put("isclass", 0 + "");
//        params.put("cid", 2 + "");
//        params.put("offset", 0 + "");
//        params.put("rows", 15 + "");

        CommonUtils.httpGet(params, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("VolleyError", "==" + volleyError.getMessage());
            }

            @Override
            public void onResponse(String s) {
                Log.e("onResponse", "==" + s);


            }
        });
    }
}
