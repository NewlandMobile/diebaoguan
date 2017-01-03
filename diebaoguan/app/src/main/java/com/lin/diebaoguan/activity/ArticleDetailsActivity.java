package com.lin.diebaoguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.BaseRedTitleBarActivity;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.send.ArticleDetailDS;
import com.lin.lib_volley_https.VolleyListener;

/**
 * 文章详情界面
 */
public class ArticleDetailsActivity extends BaseRedTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(getString(R.string.articdetail), true, true, false, R.layout.activity_article_details);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String docid = intent.getStringExtra("id");
        LogUtils.e("docid: " + docid);
        TextView text_title = (TextView) findViewById(R.id.detail_title);
        text_title.setText("" + title);

        ArticleDetailDS sendParam = new ArticleDetailDS();
        sendParam.setModule("api_libraries_sjdbg_detail");
        sendParam.initTimePart();
        sendParam.setDocid(docid);
        sendParam.setSize("" + 500);
        sendParam.setOffset("" + 0);
        CommonUtils.httpGet(sendParam.parseParams(), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.e(volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
                LogUtils.e(s);
//                ArticleDetailresponse response = ArticleDetailresponse.parseObject(s, ArticleDetailresponse.class);
//                Data data = response.getData();
            }
        });

    }

}
