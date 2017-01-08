package com.lin.diebaoguan.fsb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.LoginActivity;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.diebaoguan.network.send.ArticleCollectDS;
import com.lin.diebaoguan.network.send.ArticleDetailDS;
import com.lin.lib_volley_https.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ArticalItemFragment extends Fragment implements View.OnClickListener {
    private String docid;
    private View inflate;
    private String cid;

    private WebView webView;
    private TextView text_title;
    private TextView text_time;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private EditText edit_txt;
    private ImageView image_collect;

    private String uid;
    private String isCollected;

    public ArticalItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (inflate == null) {
            if (MyAppication.getInstance().hasLogined()) {
                uid = MyAppication.getUid();
            }
            inflate = inflater.inflate(R.layout.fragment_artical_item, container, false);
            initView();
            String docid = getDocid();
            getData(docid);
        }
        return inflate;
    }

    private void initView() {
        webView = (WebView) inflate.findViewById(R.id.detail_webview);
        text_title = (TextView) inflate.findViewById(R.id.detail_title);
        text_time = (TextView) inflate.findViewById(R.id.detail_time);

        TextView textView = (TextView) inflate.findViewById(R.id.detail_textview);
        Button btn_share = (Button) inflate.findViewById(R.id.detail_share);
        image_collect = (ImageView) inflate.findViewById(R.id.detail_collect);
        rl1 = (RelativeLayout) inflate.findViewById(R.id.rl1);
        rl2 = (RelativeLayout) inflate.findViewById(R.id.rl2);
        Button btn_send = (Button) inflate.findViewById(R.id.detail_send);
        edit_txt = (EditText) inflate.findViewById(R.id.detail_edit);

        btn_send.setOnClickListener(this);
        edit_txt.setOnClickListener(this);
        textView.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        image_collect.setOnClickListener(this);
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDocid() {
        return docid;
    }

    @Override
    public void onDestroyView() {
        ViewGroup parent = (ViewGroup) inflate.getParent();
        parent.removeAllViews();
        super.onDestroyView();
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
        if (MyAppication.getInstance().hasLogined()) {
            sendParam.setUid(uid);
        }
        CommonUtils.httpGet(sendParam.parseParams(), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.e(volleyError.toString());
                Toast.makeText(getActivity(), getString(R.string.getdatafail) + volleyError.toString(), Toast.LENGTH_SHORT).show();
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
                    cid = jsonObject2.getString("cid");
                    isCollected = jsonObject2.getString("isCollected");
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
                    if (isCollected.equals("1")) {
                        image_collect.setImageDrawable(getResources().getDrawable(R.drawable.collection_icon));
                    } else {
                        image_collect.setImageDrawable(getResources().getDrawable(R.drawable.uncollection));
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
                if (MyAppication.getInstance().hasLogined()) {
                    ArticleCollectDS articleCollectDS = new ArticleCollectDS();
                    articleCollectDS.setModule("api_libraries_sjdbg_articlecollect");
                    articleCollectDS.setDocid(docid);
                    articleCollectDS.setCid(cid);
                    articleCollectDS.setAuthkey(MyAppication.getKey());
                    articleCollectDS.setUid(uid);
                    if (isCollected.equals("0")) {
                        articleCollectDS.setMethod("collection");
                    } else {
                        articleCollectDS.setMethod("cancel");
                    }
                    articleCollectDS.initTimePart();
                    CommonUtils.httpPost(articleCollectDS.parseParams(), collectList);
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

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
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                }
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

    private void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 收藏取消监听
     */
    VolleyListener collectList = new VolleyListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            LogUtils.e(volleyError.toString());
            showToast(getString(R.string.collectedfailed) + volleyError.toString());
        }

        @Override
        public void onResponse(String s) {
            LogUtils.e(s);
            BaseResponseTemplate response = BaseResponseTemplate.parseObject(s, BaseResponseTemplate.class);
            int status = response.getStatus();
            String code = response.getCode();
            String message = response.getMessage();
            if (status == 1) {
                showToast(message);
                if (code.equals("60000015")) {
                    image_collect.setImageDrawable(getResources().getDrawable(R.drawable.collection_icon));
                    isCollected = "1";
                } else {
                    image_collect.setImageDrawable(getResources().getDrawable(R.drawable.uncollection));
                    isCollected = "0";
                }
            } else {
                showToast(getString(R.string.collectedfailed) + message);
            }
        }
    };

}
