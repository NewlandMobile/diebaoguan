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
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.diebaoguan.network.send.ArticleDetailDS;
import com.lin.lib_volley_https.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 文章详情界面
 */
public class ArticleDetailsActivity extends BaseRedTitleBarActivity implements View.OnClickListener {

    private WebView webView;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private EditText edit_txt;
    private String docid;
    private TextView text_title;
    private TextView text_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(getString(R.string.articdetail), true, true, false, R.layout.activity_article_details);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        docid = intent.getStringExtra("id");
        LogUtils.e("docid: " + docid);
        webView = (WebView) findViewById(R.id.detail_webview);
        TextView textView = (TextView) findViewById(R.id.detail_textview);
        Button btn_share = (Button) findViewById(R.id.detail_share);
        ImageView image_collect = (ImageView) findViewById(R.id.detail_collect);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
        Button btn_send = (Button) findViewById(R.id.detail_send);
        edit_txt = (EditText) findViewById(R.id.detail_edit);
        text_title = (TextView) findViewById(R.id.detail_title);
        text_time = (TextView) findViewById(R.id.detail_time);

        btn_comments.setOnClickListener(this);
        btn_back.setOnClickListener(this);
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
                showToast(getString(R.string.getdatafail) + volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
                LogUtils.e(s);
                //启用支持javascript
                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String data = jsonObject.getString("data");
                    JSONObject jsonObject1 = new JSONObject(data);
                    String info = jsonObject1.getString("info");
                    JSONObject jsonObject2 = new JSONObject(info);
                    String title = jsonObject2.getString("title");
                    String date = jsonObject2.getString("date");
                    String docUrl = jsonObject2.getString("docUrl");
                    String type = jsonObject2.getString("type");
                    String content = jsonObject2.getString("content");
                    text_time.setText(date);
                    text_title.setText(title);
                    if (type.equals("pic")) {
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
                    } else {
                        //WebView加载web资源
                        webView.loadDataWithBaseURL(docUrl, content, "text/html", "UTF-8", null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            case R.id.detail_send:
                String trim = edit_txt.getText().toString().trim();
                if (trim.equals("") || trim == null) {
                    rl1.setVisibility(View.VISIBLE);
                    rl2.setVisibility(View.GONE);
                } else {
                    MyAppication application = MyAppication.getInstance();
                    boolean isLogined = application.hasLogined();
                    if (isLogined) {
                        CommonUtils.sendComment(trim, docid, volleyListener);
                        rl1.setVisibility(View.VISIBLE);
                        rl2.setVisibility(View.GONE);
                        edit_txt.setText("");
                    } else {
                        startActivity(new Intent(this, LoginActivity.class));
                    }
                }
                break;
            case R.id.baseactivity_comments:
                MyAppication application = MyAppication.getInstance();
                boolean isLogined = application.hasLogined();
                if (isLogined) {
                    Intent intent = new Intent(this, CommentActivity.class);
                    intent.putExtra("docid", docid);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.baseactivity_back:
                finish();
                break;
        }
    }

    /**
     * 提交评论监听
     */
    VolleyListener volleyListener = new VolleyListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogUtils.e(volleyError.toString());
            showToast(getString(R.string.commentfail) + volleyError.toString());
        }

        @Override
        public void onResponse(String s) {
            LogUtils.e(s);
            BaseResponseTemplate responseTemplate = BaseResponseTemplate.parseObject(s, BaseResponseTemplate.class);
            String code = responseTemplate.getCode();
            final String message = responseTemplate.getMessage();
            if (code.equals(Const.COMMENTSUCCESS)) {
                showToast(getString(R.string.commentsuccess));
            }
        }

    };
}
