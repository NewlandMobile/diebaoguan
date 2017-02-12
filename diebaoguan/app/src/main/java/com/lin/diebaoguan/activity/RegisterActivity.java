package com.lin.diebaoguan.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.lin.diebaoguan.uibase.BaseActivity;
import com.lin.diebaoguan.MyApplication;
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

    static final int STATUS_SUCCESS = 1;
    static final int STATUS_ERROR = 2;

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
    static Map<String, String> erroCodeMap = new HashMap<>();

    static {
        erroCodeMap.put("60000003", "用户名包含敏感字符！");
        erroCodeMap.put("60000004", "用户名包含被系统屏蔽的字符!");
        erroCodeMap.put("60000005", "该用户名已被注册！");
        erroCodeMap.put("60000006", "Email地址无效！");
        erroCodeMap.put("60000007", "抱歉，Email包含不可使用的邮箱域名！");
        erroCodeMap.put("60000008", "该 Email地址已被注册！");
        erroCodeMap.put("60000009", "未定义的操作！");
    }

    private String name;
    private String password;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        postTestPrepare();
    }

    private void postTestPrepare() {
        et_name.setText(R.string.testUserName3);
        et_email.setText(R.string.testEmail3);
        et_password.setText(R.string.testPassword);
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
        String content = ((TextView) v).getText().toString();
        showToast("按下了 " + content + " 键");
        switch (v.getId()) {
            case R.id.btn_show_password_plain:
                changePasswordShowState();
                break;
            case R.id.btn_goto_register_protocol:
                //TODO 注册页
                showToast("注册协议尚未完成");
                break;
            case R.id.btn_register:
                valifyInputAndPost();
                break;
            case R.id.button_cancel:

                break;
        }
    }

    private void changePasswordShowState() {
        String text=btn_show_password_plain.getText().toString();
        if (text.contains("显示")){
//            方式二选一  第二种似乎对字体有影响
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//            et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            btn_show_password_plain.setText("隐藏");
        }else {
            et_password.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
//            et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            btn_show_password_plain.setText("显示");
        }
//        if (et_password)


    }

    private void valifyInputAndPost() {
        if (!checkBox_read.isChecked()){
            showToast("请先阅读 注册协议");
            return;
        }
        if (isContentEmpty()) {
            return;
        }
        int passwordLength=password.length();
        if (passwordLength<4||passwordLength>20)
        {
            showToast("密码长度应该在4-20之间");
            return;
        }
        post();
    }

    private void post() {
        RegisterDS registerDS = new RegisterDS();
        registerDS.setEmail(email);
        registerDS.setUsername(name);
        registerDS.setPassword(password);
        registerDS.setModule("api_libraries_common_register");
        registerDS.initTimePart();
        CommonUtils.httpPost(registerDS.parseParams(),
                new VolleyListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtils.d(volleyError.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        RegisterResponse registerResponse=RegisterResponse.parseObject(response,RegisterResponse.class);
                        LogUtils.d(registerResponse.toString());
                        if (1!=registerResponse.getStatus()){
                            showToast(registerResponse.getMessage());
                            return;
                        }
                        MyApplication.setKey(registerResponse.getData().getKey());
                        MyApplication.setUid(registerResponse.getData().getUid());
                    }
                });
    }

    private boolean isContentEmpty() {
        // validate
        name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("请填写用户名");
//            Toast.makeText(this, "中文、英文或数字", Toast.LENGTH_SHORT).show();
            return true;
        }

        password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return true;
        }

        email = et_email.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "请输入email", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
