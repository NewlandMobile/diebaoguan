package com.lin.diebaoguan.fsb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.lin.diebaoguan.activity.ArticleDetailsActivity;
import com.lin.diebaoguan.activity.BaseCommentAndShareActivity;
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

public class ArticalItemFragment extends Fragment  {
    private String docid;
    private View inflate;


    private WebView webView;
    private TextView text_title;
    private TextView text_time;


    private String uid;
    private ArticleDetailsActivity parentActivity;

    public ArticalItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity= (ArticleDetailsActivity) getActivity();
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
//            String docid = getDocid();
            getData(docid);
        }
        return inflate;
    }

    private void initView() {
        webView = (WebView) inflate.findViewById(R.id.detail_webview);
        text_title = (TextView) inflate.findViewById(R.id.detail_title);
        text_time = (TextView) inflate.findViewById(R.id.detail_time);
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDocid() {
        return docid;
    }

//    @Override
//    public void onDestroyView() {
//        ViewGroup parent = (ViewGroup) inflate.getParent();
//        parent.removeAllViews();
//        super.onDestroyView();
//    }

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
                    parentActivity.setCid( jsonObject2.getString("cid"));
                    String isCollected = jsonObject2.getString("isCollected");
                    parentActivity.setCollected(("1").equals(isCollected));
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
//                    parentActivity.changeCollectState(isCollected.equals("1"));
//                    if () {
//                        image_collect.setImageResource(R.drawable.collection_icon);
////                        image_collect.setImageDrawable(getResources().getDrawable(R.drawable.collection_icon));
//                    } else {
//                        image_collect.setImageResource(R.drawable.uncollection);
////                        image_collect.setImageDrawable(getResources().getDrawable(R.drawable.uncollection));
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }



}
