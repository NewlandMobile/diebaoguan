package com.lin.diebaoguan.uibase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.CommentActivity;
import com.lin.diebaoguan.activity.LoginActivity;
import com.lin.diebaoguan.common.AccessTokenKeeper;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.diebaoguan.network.send.ArticleCollectDS;
import com.lin.lib_volley_https.VolleyListener;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * It's Created by NewLand-JianFeng on 2017/1/10.
 */

public class BaseCommentAndShareActivity extends BaseRedTitleBarActivity implements View.OnClickListener, IWeiboHandler.Response {
    protected String docid;
    protected boolean isCollected;
    protected int cid;
    protected RelativeLayout rl1;
    protected RelativeLayout rl2;
    protected EditText edit_txt;
    protected ImageView image_collect;
    protected boolean hasLogin;
    private Runnable runnable;//在收藏取消时外部的操作
    private String mTitle;//标题
    private IWeiboShareAPI mWeiboShareAPI;//微博分享

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
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Const.KEY_WEIBO);
        mWeiboShareAPI.registerApp();    // 将应用注册到微博客户端
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

    public void setCollectedAndTitle(boolean collected, String title) {
        isCollected = collected;
        this.mTitle = title;
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
                Toast.makeText(this, "进入分享", Toast.LENGTH_SHORT).show();
                sendMultiMessage();
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
                setCollectedAndTitle(code.equals("60000015"), mTitle);
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

    //创建要分享的文本内容
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = mTitle;
        LogUtils.e("===" + mTitle);
        return textObject;
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
     */
    private void sendMultiMessage() {

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject = getTextObj();
        weiboMessage.imageObject = getImageObj();

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
//        if (mShareType == SHARE_CLIENT) {
//            mWeiboShareAPI.sendRequest(this, request);
//        } else if (mShareType == SHARE_ALL_IN_ONE) {
        AuthInfo authInfo = new AuthInfo(this, Const.KEY_WEIBO, Const.REDIRECT_URL, Const.SCOPE);
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        } else {
            LogUtils.e("==============null");
        }
        mWeiboShareAPI.sendRequest(this, request, authInfo, token, new WeiboAuthListener() {

            @Override
            public void onWeiboException(WeiboException arg0) {
            }

            @Override
            public void onComplete(Bundle bundle) {
                // TODO Auto-generated method stub
                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
                AccessTokenKeeper.writeAccessToken(getApplicationContext(), newToken);
                Toast.makeText(getApplicationContext(), "onAuthorizeComplete token = " + newToken.getToken(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
            }
        });
//        }
    }

    /**
     * 设置title用于分享使用
     *
     * @param title
     */
    protected void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        //设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     *
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    /**
     * 接收微客户端博请求的数据。
     * 当微博客户端唤起当前应用并进行分享时，该方法被调用。
     *
     * @param baseResp 微博请求数据对象
     * @see {@link IWeiboShareAPI#handleWeiboRequest}
     */
    @Override
    public void onResponse(BaseResponse baseResp) {
        if (baseResp != null) {
            switch (baseResp.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
                    Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    Toast.makeText(this,
                            getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: " + baseResp.errMsg,
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
