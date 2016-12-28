package com.lin.diebaoguan.dbg.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.fragment.RefreshListFragment;
import com.lin.lib_volley_https.VolleyListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PriceFragment extends RefreshListFragment {


    private View view;
    private ListView listView;

    public PriceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.refresh_listview);
        getData();
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
