package com.lin.diebaoguan.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.BaseActivity;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.response.RegisterResponse;
import com.lin.diebaoguan.network.send.RegisterDS;
import com.lin.lib_volley_https.VolleyListener;

import java.util.HashMap;
import java.util.Map;

/**
 * It's Created by NewLand-JianFeng on 2016/12/26.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_password;
    private Button btn_show_password_plain;
    private EditText et_email;
    private CheckBox checkBox_read;
    private Button btn_goto_register_protocol;
    private Button btn_register;
    private Button button_cancel;

    static final int STATUS_SUCCESS=1;
    static final int STATUS_ERROR=2;

//    static final int SUCCESS_CODE=60000017;
//    static final int ERROR_CODE_SENSITIVE=60000003;
//    static final int ERROR_CODE_SHIELD=60000004;
//    static final int ERROR_CODE_NAME_HAVEUSED=60000005;
//    static final int ERROR_CODE_EMAIL_INVALID=60000006;
//    static final int ERROR_CODE_DOMAIN=60000007;
//    static final int ERROR_CODE_EMAIL_HAVEUSED=60000008;
//    static final int ERROR_CODE_UNDIFINED=60000009;
//
//    static final String ERROR_MESS_SENSITIVE;
//    static final String ERROR_MESS_SHIELD;
//    static final String ERROR_MESS_NAME_HAVEUSED;
//    static final String ERROR_MESS_EMAIL_INVALID;
//    static final String ERROR_MESS_DOMAIN;
//    static final String ERROR_MESS_EMAIL_HAVEUSED;
//    static final String ERROR_MESS_UNDIFINED;

    /*
 code	整型	60000017
 code	60000003	用户名包含敏感字符！
 code	60000004	用户名包含被系统屏蔽的字符!
 code	60000005	该用户名已被注册！
 code	60000006	Email地址无效！
 code	60000007	抱歉，Email包含不可使用的邮箱域名！
 code	60000008	该 Email地址已被注册！
 code	60000009	未定义的操作！*/
    static Map<String,String > erroCodeMap=new HashMap<>();
    static {
        erroCodeMap.put("60000003","用户名包含敏感字符！");
        erroCodeMap.put("60000004","用户名包含被系统屏蔽的字符!");
        erroCodeMap.put("60000005","该用户名已被注册！");
        erroCodeMap.put("60000006","Email地址无效！");
        erroCodeMap.put("60000007","抱歉，Email包含不可使用的邮箱域名！");
        erroCodeMap.put("60000008","该 Email地址已被注册！");
        erroCodeMap.put("60000009","未定义的操作！");
    }

    private String name;
    private String password;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_show_password_plain = (Button) findViewById(R.id.btn_show_password_plain);
        et_email = (EditText) findViewById(R.id.et_email);
        checkBox_read = (CheckBox) findViewById(R.id.checkBox_read);
        btn_goto_register_protocol = (Button) findViewById(R.id.btn_goto_register_protocol);
        btn_register = (Button) findViewById(R.id.btn_register);
        button_cancel = (Button) findViewById(R.id.button_cancel);

        btn_show_password_plain.setOnClickListener(this);
        btn_goto_register_protocol.setOnClickListener(this);
        btn_goto_register_protocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        btn_register.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String content=((TextView)v).getText().toString();
        showToast("按下了 "+ content+" 键");
        switch (v.getId()) {
            case R.id.btn_show_password_plain:

                break;
            case R.id.btn_goto_register_protocol:

                break;
            case R.id.btn_register:
                if (!isContentEmpty()){
                    post();
                }
                break;
            case R.id.button_cancel:

                break;
        }
    }

    private void post() {
        RegisterDS registerDS=new RegisterDS();
        registerDS.setEmail(email);
        registerDS.setUsername(name);
        registerDS.setPassword(password);

        registerDS.setModule("api_libraries_common_register");
        long currentTime=System.currentTimeMillis()/1000;
        registerDS.setTimestamp(String.valueOf(currentTime));
        registerDS.setToken1(CommonUtils.getToken(currentTime));
//        TODO
//        CommonUtils.httpPost();
        CommonUtils.getInstance().JsonPost(RegisterResponse.class, registerDS, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(classNameString,"onErrorResponse:"+volleyError);
                LogUtils.d(volleyError.toString());
            }

            @Override
            public void onResponse(Object o) {
                Log.d(classNameString,"onResponse:"+o.toString());
                LogUtils.d(o.toString());
            }
        });
    }

    private boolean isContentEmpty() {
        // validate
        name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "中文、英文或数字", Toast.LENGTH_SHORT).show();
            return true;
        }

        password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "4-20位", Toast.LENGTH_SHORT).show();
            return true;
        }

        email = et_email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "email地址", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
