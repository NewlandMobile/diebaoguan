package com.lin.diebaoguan.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.lin.diebaoguan.BaseActivity;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.response.RegisterResponse;
import com.lin.diebaoguan.network.send.RegisterDS;
import com.lin.lib_volley_https.VolleyListener;

/**
 * Created by Alan on 2017/1/2 0002.
 * 登录页
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_name;
    private EditText et_password;
    private Button btn_login;
    private Button btn_register;
    private String name=null;
    private String password=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        final ProgressDialog progressDialog = CommonUtils.showProgressDialog(this);
//        progressDialog.show();
        initView();
        setDefaultUser();
    }

    private void setDefaultUser() {
        et_name.setText(R.string.testUserName2);
        et_password.setText(R.string.testPassword);
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_register= (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                checkInputAndPost();
                break;
            case R.id.btn_register:
                gotoRegister();
                break;
        }
    }

    private void gotoRegister() {
        Intent registerIntent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(registerIntent);
        finish();
    }

    private void checkInputAndPost() {
        if (isInputEmpty()){
            return;
        }
        post();
    }

    private void post() {
        RegisterDS loginDS = new RegisterDS();
//        registerDS.setEmail(email);
        loginDS.setUsername(name);
        loginDS.setPassword(password);
        loginDS.setModule("api_libraries_common_login");
        loginDS.initTimePart();
        CommonUtils.httpPost(loginDS.parseParams(),
                new VolleyListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogUtils.d(volleyError.toString());
                        if (volleyError instanceof TimeoutError){
                            showToast("网络连接超时");
                        }else {
                            showToast("网络连接错误");
                        }
                    }

                    @Override
                    public void onResponse(String response) {
                        RegisterResponse loginResponse=RegisterResponse.parseObject(response,RegisterResponse.class);
                        LogUtils.d(loginResponse.toString());
                        if (1!=loginResponse.getStatus()){
                            showToast(loginResponse.getMessage());
                            return;
                        }
                        LogUtils.d("用户ID："+loginResponse.getData().getUid());
                        MyAppication.setKey(loginResponse.getData().getKey());
                        MyAppication.setUid(loginResponse.getData().getUid());
                        gotoMainActivity();
                    }

                });
    }

    private void gotoMainActivity() {
        finish();
//        Intent mainActivityTntent=new Intent(LoginActivity)
    }

    private boolean isInputEmpty() {
        // validate
        name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
//            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            showToast("请填写用户名");
            return true;
        }

        password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
//            Toast.makeText(this, "请输入4-20位密码", Toast.LENGTH_SHORT).show();
            showToast("请输入密码");
            return true;

        }
        return false;
    }
}
