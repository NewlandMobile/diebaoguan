package com.lin.diebaoguan.uibase;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.CommentActivity;
import com.lin.diebaoguan.activity.LoginActivity;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.diebaoguan.network.send.ArticleCollectDS;
import com.lin.lib_volley_https.VolleyListener;

/**
 * It's Created by NewLand-JianFeng on 2017/1/10.
 */

public class BaseCommentAndShareActivity extends BaseRedTitleBarActivity implements View.OnClickListener {
    protected String docid;
    protected boolean isCollected;
    protected int cid;
    protected RelativeLayout rl1;
    protected RelativeLayout rl2;
    protected EditText edit_txt;
    protected ImageView image_collect;
    protected boolean hasLogin;
    private Runnable runnable;

    protected void initPublicUI(String title, boolean showImage, int layoutId) {
        initTitleBar(title, true, true, showImage, layoutId);
        btn_comments.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        initBottomPart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hasLogin = MyAppication.getInstance().hasLogined();
    }

    public void changeCollectState(boolean collected) {
        if (collected) {
            image_collect.setBackgroundResource(R.drawable.a_n);
        } else {
            image_collect.setBackgroundResource(R.drawable.a_l);
        }
    }

    private void initBottomPart() {

        TextView textView = (TextView) findViewById(R.id.detail_textview);
        Button btn_share = (Button) findViewById(R.id.detail_share);
        image_collect = (ImageView) findViewById(R.id.detail_collect);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
        Button btn_send = (Button) findViewById(R.id.detail_send);
        edit_txt = (EditText) findViewById(R.id.detail_edit);

        btn_send.setOnClickListener(this);
        edit_txt.setOnClickListener(this);
        textView.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        image_collect.setOnClickListener(this);
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
        changeCollectState(collected);
    }


    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baseactivity_back:
                finish();
                break;
            case R.id.baseactivity_comments:
                if (hasLogin) {
                    Intent intent = new Intent(this, CommentActivity.class);
                    intent.putExtra("docid", docid);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;

            // 以上是标题栏   以下是底部评论栏
            case R.id.detail_textview:
                rl1.setVisibility(View.GONE);
                rl2.setVisibility(View.VISIBLE);
                break;
            case R.id.detail_collect:
                if (hasLogin) {
                    postCollect();
                } else {
                    startActivity(new Intent(BaseCommentAndShareActivity.this, LoginActivity.class));
                }
                break;
            case R.id.detail_share://分享





                break;
            case R.id.detail_send:
                String trim = edit_txt.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    rl1.setVisibility(View.VISIBLE);
                    rl2.setVisibility(View.GONE);
                } else {
                    if (hasLogin) {
                        CommonUtils.sendComment(trim, docid, coommentListener);
                        rl1.setVisibility(View.VISIBLE);
                        rl2.setVisibility(View.GONE);
                        edit_txt.setText("");
                    } else {
                        startActivity(new Intent(BaseCommentAndShareActivity.this, LoginActivity.class));
                    }
                }
                break;
        }
    }

    private void postCollect() {
        ArticleCollectDS articleCollectDS = new ArticleCollectDS();
        articleCollectDS.setModule("api_libraries_sjdbg_articlecollect");
        articleCollectDS.setDocid(docid);
        articleCollectDS.setCid(cid);
        articleCollectDS.setAuthkey(MyAppication.getKey());
        articleCollectDS.setUid(MyAppication.getUid());
        if (!isCollected) {
            articleCollectDS.setMethod("collection");
        } else {
            articleCollectDS.setMethod("cancel");
        }
        articleCollectDS.initTimePart();
        image_collect.setEnabled(false);
        CommonUtils.httpPost(articleCollectDS.parseParams(), collectListener);
    }

    /**
     * 提交评论监听
     */
    VolleyListener coommentListener = new VolleyListener() {
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
//            final String message = responseTemplate.getMessage();
            if (code.equals(Const.COMMENTSUCCESS)) {
                showToast(getString(R.string.commentsuccess));
            }
        }

    };

    /**
     * 收藏取消监听
     */
    VolleyListener collectListener = new VolleyListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            image_collect.setEnabled(true);
            LogUtils.e(volleyError.toString());
            showToast(getString(R.string.collectedfailed) + volleyError.toString());
        }

        @Override
        public void onResponse(String s) {
            image_collect.setEnabled(true);
            LogUtils.e(s);
            BaseResponseTemplate response = BaseResponseTemplate.parseObject(s, BaseResponseTemplate.class);
            int status = response.getStatus();
            String code = response.getCode();
            String message = response.getMessage();
            if (status == 1) {
                showToast(message);
                setCollected(code.equals("60000015"));
                if (runnable != null) {
                    runnable.run();
                }
            } else {
                showToast(getString(R.string.collectedfailed) + message);
            }
            //
        }
    };

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}
