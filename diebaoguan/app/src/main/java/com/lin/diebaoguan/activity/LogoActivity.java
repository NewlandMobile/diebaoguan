package com.lin.diebaoguan.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.uibase.BaseActivity;
import com.lin.diebaoguan.MainActivity;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.response.NormalResponse;
import com.lin.diebaoguan.network.send.BaseSendTemplate;
import com.lin.lib_volley_https.VolleyListener;

/**
 * logo（欢迎）界面
 */
public class LogoActivity extends BaseActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        imageView = (ImageView) findViewById(R.id.welcome_image);
        getData();
    }

    public void getData() {
        BaseSendTemplate sendTemplate = new BaseSendTemplate();
        sendTemplate.setModule("api_libraries_sjdbg_startuplogo");
        sendTemplate.initTimePart();
        CommonUtils.httpGet(sendTemplate.parseParams(), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                gotoNextActivity(false, volleyError.toString());
            }

            @Override
            public void onResponse(String s) {
                LogUtils.e(s);
                gotoNextActivity(true, s);
            }
        });
    }

    private void gotoNextActivity(boolean isSuccess, String message) {
        if (isSuccess) {
            NormalResponse normalResponse = NormalResponse.parseObject(message, NormalResponse.class);
            if (normalResponse.getStatus() == 1) {
                String logoSrc = normalResponse.getData().getLogoSrc();
// logo的图片基本不变，为了更加迅速的进入，可以暂时不做图片的加载
//                IMAGEUtils.displayImageWithRounder(logoSrc, imageView, 0, R.drawable.welcome);
            }
        } else {
            showToast(getResources().getString(R.string.getdatafail) + message);
        }

        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Const.SP_ISFIRSTNAME, Context.MODE_PRIVATE);
                boolean isFirst = sharedPreferences.getBoolean(Const.SP_ISFIRSTKEY, true);
                if (isFirst) {
                    startActivity(new Intent(LogoActivity.this, GuideActivity.class));
                } else {
                    startActivity(new Intent(LogoActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 2000);
    }
}
