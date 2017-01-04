package com.lin.diebaoguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.BaseRedTitleBarActivity;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Info;
import com.lin.diebaoguan.network.response.ArticleDetailresponse;
import com.lin.diebaoguan.network.send.ArticleDetailDS;
import com.lin.lib_volley_https.VolleyListener;

/**
 * 文章详情界面
 */
public class ArticleDetailsActivity extends BaseRedTitleBarActivity implements View.OnClickListener {

    private WebView webView;
    private RelativeLayout rl1;
    private RelativeLayout rl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(getString(R.string.articdetail), true, true, false, R.layout.activity_article_details);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String docid = intent.getStringExtra("id");
        LogUtils.e("docid: " + docid);
        webView = (WebView) findViewById(R.id.detail_webview);
        TextView textView = (TextView) findViewById(R.id.detail_textview);
        Button btn_share = (Button) findViewById(R.id.detail_share);
        ImageView image_collect = (ImageView) findViewById(R.id.detail_collect);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
        Button btn_send = (Button) findViewById(R.id.detail_send);
        EditText edit_txt = (EditText) findViewById(R.id.detail_edit);

        btn_send.setOnClickListener(this);
        edit_txt.setOnClickListener(this);
        textView.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        image_collect.setOnClickListener(this);
        getData(docid);

    }

    /**
     * 获取数据
     *
     * @param docid
     */
    private void getData(String docid) {
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
                ArticleDetailresponse response = ArticleDetailresponse.parseObject(s, ArticleDetailresponse.class);
                Info info = response.getData().getInfo();
                String date = info.getDate();
                String docUrl = info.getDocUrl();
                LogUtils.e(docUrl);
                //启用支持javascript
                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);
                //WebView加载web资源
                webView.loadUrl(docUrl);
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // TODO Auto-generated method stub
                        //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                        view.loadUrl(url);
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_textview:
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
                break;
            case R.id.detail_collect:
                break;
            case R.id.detail_share:
                break;
        }
    }
}
