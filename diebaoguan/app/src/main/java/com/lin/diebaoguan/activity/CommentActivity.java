package com.lin.diebaoguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.adapter.CommentAdapter;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.diebaoguan.network.send.CommentListDS;
import com.lin.diebaoguan.uibase.BaseRedTitleBarActivity;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论界面
 */
public class CommentActivity extends BaseRedTitleBarActivity implements View.OnClickListener {
    private List<Result> list = new ArrayList<>();
    private String docid;
    private CommentAdapter adapter;
    private EditText edit_txt;
    private Button btn_send;
    private RelativeLayout rl2;
    private RelativeLayout rl1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("更多评论", true, false, false, R.layout.activity_comment);
        initView();
        getDate();
    }

    private void initView() {
        Intent intent = getIntent();
        docid = intent.getStringExtra("docid");
        PullToRefreshListView pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.comment_list);
        ListView listView = pullToRefreshListView.getRefreshableView();
        adapter = new CommentAdapter(this, list);
        listView.setAdapter(adapter);

        btn_send = (Button) findViewById(R.id.commentlist_send);
        edit_txt = (EditText) findViewById(R.id.commentlist_edit);
        Button btn_back = (Button) findViewById(R.id.baseactivity_back);
        TextView textView = (TextView) findViewById(R.id.commentlist_text);
        rl1 = (RelativeLayout) findViewById(R.id.commentlist_rl1);
        rl2 = (RelativeLayout) findViewById(R.id.commentlist_rl2);
        btn_back.setOnClickListener(this);
        textView.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    /**
     * 获取数据
     */
    public void getDate() {
        CommentListDS commentListDs = new CommentListDS();
        commentListDs.setModule("api_libraries_sjdbg_commentlist");
        commentListDs.setDocid(docid);
        commentListDs.setAvatar("middle");
        commentListDs.setOffset("0");
        commentListDs.setRows("15");
        commentListDs.initTimePart();

        CommonUtils.httpGet(commentListDs.parseParams(), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtils.e(volleyError.toString());
                showToast(getString(R.string.getdatafail) + volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
                LogUtils.e(s);
                NormalResponse normalResponse = NormalResponse.parseObject(s, NormalResponse.class);
                Result[] result = normalResponse.getData().getResult();
                if (result != null) {
                    for (Result rslt : result) {
                        list.add(rslt);
                        adapter.notifyDataSetChanged();
                    }
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
            case R.id.commentlist_send:
                String trim = edit_txt.getText().toString().trim();
                if (trim != null && !trim.equals("")) {
                    CommonUtils.sendComment(trim, docid, volleyListener);
                } else {
                    showToast(getString(R.string.pleasescanfword));
                }
                edit_txt.setText("");
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
                break;
            case R.id.commentlist_text:
                rl1.setVisibility(View.VISIBLE);
                rl2.setVisibility(View.GONE);
                break;
        }
    }

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
            list.clear();
            getDate();
        }
    };
}
