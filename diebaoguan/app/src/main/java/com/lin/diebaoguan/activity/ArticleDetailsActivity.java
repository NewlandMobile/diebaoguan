package com.lin.diebaoguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
public class ArticleDetailsActivity extends BaseRedTitleBarActivity {

    private TextView text_title;
    private TextView text_time;
    private WebView webView;

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
        text_title = (TextView) findViewById(R.id.detail_title);
        text_time = (TextView) findViewById(R.id.detail_time);
        webView = (WebView) findViewById(R.id.detail_webview);


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
                text_title.setText(info.getTitle());
                String date = info.getDate();
                text_time.setText(date);
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

}
