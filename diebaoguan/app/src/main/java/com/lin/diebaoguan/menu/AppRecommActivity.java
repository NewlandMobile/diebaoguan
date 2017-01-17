package com.lin.diebaoguan.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.adapter.AppRecommentAdapter;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.network.bean.Data;
import com.lin.diebaoguan.network.bean.RecommendAppList;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.diebaoguan.network.send.BaseSendTemplate;
import com.lin.diebaoguan.uibase.BaseRedTitleBarActivity;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 应用推荐
 */
public class AppRecommActivity extends BaseRedTitleBarActivity implements View.OnClickListener {

    private ListView listview;
    private List<RecommendAppList> dataList = new ArrayList<>();
    private AppRecommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("应用推荐", true, false, false, R.layout.activity_app_recomm);
        initView();
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.apprecom_listview);
        adapter = new AppRecommentAdapter(this, dataList);
        listview.setAdapter(adapter);
        getData();
        btn_back.setOnClickListener(this);
    }

    public void getData() {
        BaseSendTemplate sendTemplate = new BaseSendTemplate();
        sendTemplate.setModule("api_libraries_kuruanhui_indexrecommend");
        sendTemplate.initTimePart();
        CommonUtils.httpGet(sendTemplate.parseParams(), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                showToast(getResources().getString(R.string.getdatafail) + volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
                NormalResponse response = NormalResponse.parseObject(s, NormalResponse.class);
                int status = response.getStatus();
                if (status == 1) {
                    Data data = response.getData();
                    RecommendAppList[] recommendAppList = data.getRecommendAppList();
                    Collections.addAll(dataList, recommendAppList);
                    adapter.notifyDataSetChanged();
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baseactivity_back:
                finish();
                break;
        }
    }
}
