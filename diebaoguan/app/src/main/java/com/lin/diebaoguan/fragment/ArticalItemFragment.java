package com.lin.diebaoguan.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.ArticleDetailsActivity;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.network.send.ArticleDetailDS;
import com.lin.lib_volley_https.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 文章详情fragment
 */
public class ArticalItemFragment extends Fragment {
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
        parentActivity = (ArticleDetailsActivity) getActivity();
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

    /**
     * 获取数据
     *
     * @param docid docid
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
//                LogUtils.e(volleyError.toString());
                Toast.makeText(getActivity(), getString(R.string.getdatafail) + volleyError.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String s) {
//                LogUtils.e(s);
                updateUI(s);
            }
        });
    }

    private void updateUI(String s) {
        JSONObject info;
        String type = null;
        String content = null;
        String docUrl = null;
        try {
            JSONObject response = new JSONObject(s);
            JSONObject data = response.getJSONObject("data");
            info = data.getJSONObject("info");
            String title = info.getString("title");
            String date = info.getString("date");
            docUrl = info.getString("docUrl");
            type = info.getString("type");
            content = info.getString("content");
            String docid = info.getString("docid");

            parentActivity.setCid(info.getInt("cid"));
            String isCollected = info.getString("isCollected");
            parentActivity.setCollectedAndTitle(("1").equals(isCollected),docid);
            text_time.setText(date);
            text_title.setText(title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
//        拦截图片下载 测试
        webSettings.setBlockNetworkImage(MyAppication.isBlockImage());
        // 字体大小 测试
        webSettings.setTextZoom(MyAppication.getTextSizeZoom());
//        webSettings.setTextSize(WebSettings.TextSize.);

        Document doc = Jsoup.parse(content);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }

        if (("pic").equals(type)) {
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(docUrl);
        } else {
            //WebView加载web资源
            webView.loadDataWithBaseURL("", doc.toString(), "text/html", "UTF-8", null);
        }
    }
}
