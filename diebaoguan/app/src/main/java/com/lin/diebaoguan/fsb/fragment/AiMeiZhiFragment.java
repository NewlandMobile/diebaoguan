package com.lin.diebaoguan.fsb.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.lin.diebaoguan.MyApplication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.AmzDetailActivity;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.IMAGEUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Data;
import com.lin.diebaoguan.network.bean.Paging;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.diebaoguan.network.send.AiMeiZhiDS;
import com.lin.diebaoguan.network.send.PicDetailDS;
import com.lin.diebaoguan.uibase.PullToRefreshBaseFragment;
import com.lin.lib_volley_https.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AiMeiZhiFragment extends PullToRefreshBaseFragment implements AdapterView.OnItemClickListener {


    private View view;
    private ListView refreshableView;
    private List<Result> list = new ArrayList<>();
    private MyAdapter adapter;
    private int currentPageOffset = 0;//  当前已加载多少页
    private int totalPage;//总共有多少页

    public AiMeiZhiFragment() {
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
            view = super.onCreateView(inflater, container, savedInstanceState);
            refreshableView = basePullToRefreshListView.getRefreshableView();
            refreshableView.setBackground(getResources().getDrawable(R.drawable.gyj_bj));
            adapter = new MyAdapter();
            refreshableView.setAdapter(adapter);
            refreshableView.setOnItemClickListener(this);
            getData(0);
        }
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String docid = ((Result) adapter.getItem(position - 1)).getDocid();
        PicDetailDS params = new PicDetailDS();
        params.setDocid(docid);
        params.setModule("api_libraries_sjdbg_detail");
        params.setUid(MyApplication.getUid());
        params.setSize("" + 500);
        params.initTimePart();
        CommonUtils.httpGet(this, params.parseParams(), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.d(volleyError.toString());
                showToast(getString(R.string.getdatafail));
            }

            @Override
            public void onResponse(String s) {
                LogUtils.d(s);
                JSONObject response = null;
                try {
                    response = new JSONObject(s);
                    String status = response.getString("status");
                    if (status.equals("1")) {
                        JSONObject data = response.getJSONObject("data");
                        String info = data.getString("info");
                        Intent intent = new Intent(getActivity(), AmzDetailActivity.class);
                        intent.putExtra("info", info);
                        startActivity(intent);
                    } else {
                        showToast("文章内容解析失败！");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


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
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.item_amz, null);
            ImageView image = (ImageView) inflate.findViewById(R.id.itemamz_image);
            TextView text = (TextView) inflate.findViewById(R.id.itemamz_text);
            Result result = list.get(position);
            IMAGEUtils.displayImage(result.getPic(), image);
            text.setText(result.getTitle());
            //下拉刷新
            basePullToRefreshListView.setOnRefreshListener(new CommonListRefreshListener());
            return inflate;
        }
    }

    private class CommonListRefreshListener implements PullToRefreshBase.OnRefreshListener2<ListView> {
        @Override
        public void onPullStartToRefresh(PullToRefreshBase<ListView> refreshView) {
            currentPageOffset = 0;
            getData(currentPageOffset);
        }

        @Override
        public void onPullEndToRefresh(PullToRefreshBase<ListView> refreshView) {
            if (currentPageOffset >= totalPage) {
                showToast(getString(R.string.alreadyatthebottom));
            }
            getData(currentPageOffset);
        }
    }

    private void getData(int pageOffset) {
        AiMeiZhiDS sendTemplate = new AiMeiZhiDS();
        sendTemplate.setModule("api_libraries_sjdbg_aimeizhi");
        sendTemplate.initTimePart();
        sendTemplate.setOffset("" + pageOffset * Const.ROWS);
        sendTemplate.setRows("" + Const.ROWS);
        CommonUtils.httpGet(AiMeiZhiFragment.this, sendTemplate.parseParams(), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.d(volleyError.toString());
                showToast(getString(R.string.getdatafail));
                basePullToRefreshListView.onRefreshComplete();
            }

            @Override
            public void onResponse(String s) {
                LogUtils.d(s);
                basePullToRefreshListView.onRefreshComplete();
                NormalResponse normalResponse = NormalResponse.parseObject(s, NormalResponse.class);
                int status = normalResponse.getStatus();
                if (status == 1) {
                    Data data = normalResponse.getData();
                    Result[] result = data.getResult();
                    if (currentPageOffset == 0) {
                        list.clear();
                    }
                    // 这行是新写法，下次要记住
                    Collections.addAll(list, result);
                    adapter.notifyDataSetChanged();
                    Paging paging = data.getPaging();
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
        });
    }
}
