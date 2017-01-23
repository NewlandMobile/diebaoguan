package com.lin.diebaoguan.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.diebaoguan.network.send.FeedbackDS;
import com.lin.diebaoguan.uibase.BaseRedTitleBarActivity;
import com.lin.lib_volley_https.VolleyListener;

/**
 * 意见反馈界面
 */
public class FeedbackActivity extends BaseRedTitleBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(getString(R.string.feedback), true, false, false, R.layout.activity_feedback);
        Button btn_submit = (Button) findViewById(R.id.feedback_submit);
        final EditText edit_feed = (EditText) findViewById(R.id.feedback_content);
        final EditText edit_contact = (EditText) findViewById(R.id.feedback_contact);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit_feed.getText().toString().trim();
                String contact = edit_contact.getText().toString().trim();
                if (contact.equals("") || content.equals("")) {
                    showToast(getString(R.string.pleaseinputrightinfor));
                } else {
                    FeedbackDS feedbackDS = new FeedbackDS();
                    feedbackDS.setModule("api_libraries_common_feedback");
                    feedbackDS.setUsername(MyAppication.getUserName());
                    feedbackDS.setUid(MyAppication.getUid());
                    feedbackDS.setImei(CommonUtils.getIMEI(FeedbackActivity.this));
                    feedbackDS.setEmail(contact);
                    feedbackDS.setContent(content);
                    feedbackDS.initTimePart();
                    CommonUtils.httpPost(feedbackDS.parseParams(), new VolleyListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            showToast(getResources().getString(R.string.datapostfail));
                        }

                        @Override
                        public void onResponse(String s) {
                            LogUtils.e(s);
                            BaseResponseTemplate response = BaseResponseTemplate.parseObject(s, BaseResponseTemplate.class);
                            String message = response.getMessage();
                            showToast(message);
                            edit_feed.setText("");
                            edit_contact.setText("");
                        }
                    });
                }
            }
        });
    }
}
