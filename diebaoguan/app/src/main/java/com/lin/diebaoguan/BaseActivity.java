package com.lin.diebaoguan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseActivity extends Activity {

    public Button btn_back;//返回
    public Button btn_comments;//查看跟帖
    public ImageView imageView_allpic;//查看所有图片
    public TextView text_title;//设置标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        btn_back = (Button) findViewById(R.id.baseactivity_back);
        btn_comments = (Button) findViewById(R.id.baseactivity_comments);
        imageView_allpic = (ImageView) findViewById(R.id.baseactivity_all);
        text_title = (TextView) findViewById(R.id.baseactivity_titil);
    }
}
