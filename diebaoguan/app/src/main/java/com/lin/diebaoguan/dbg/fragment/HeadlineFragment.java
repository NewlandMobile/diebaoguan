package com.lin.diebaoguan.dbg.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.uibase.PullToRefreshBaseFragment;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 头条界面
 */
public class HeadlineFragment extends PullToRefreshBaseFragment {


    private View view;
    private List<String> list = new ArrayList<>();

    public HeadlineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument(getActivity(), 0, false, true);
        initList();
    }

    private void initList() {
        for (int i = 0; i < 20; i++) {
            list.add("内容");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = super.onCreateView(inflater, container, savedInstanceState);

            ListView listview = basePullToRefreshListView.getRefreshableView();
            View headView = getActivity().getLayoutInflater().inflate(R.layout.view_ad, null);
            listview.addHeaderView(headView);
            listview.setAdapter(new MyAdapter());
//            ListView listview = (ListView) view.findViewById(R.id.headline_listview);
//            listview.setAdapter(new MyAdapter());
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


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView view = (TextView) getActivity().getLayoutInflater().inflate(android.R.layout.simple_expandable_list_item_1, null);
            view.setText(list.get(position));
            return view;
        }
    }
}
