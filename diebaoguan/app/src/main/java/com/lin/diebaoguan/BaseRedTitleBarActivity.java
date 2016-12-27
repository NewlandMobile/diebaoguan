package com.lin.diebaoguan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 带红色标题栏的公共父类
 */
public class BaseRedTitleBarActivity extends BaseActivity {

    protected Button btn_back;//返回
    protected Button btn_comments;//查看跟帖
    protected ImageView imageView_allpic;//查看所有图片
    protected TextView text_title;//设置标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initTitleViews();
    }

    /**
     * 初始化  通用TitleBar
     */
    private void initTitleViews() {
        btn_back = (Button) findViewById(R.id.baseactivity_back);
        btn_comments = (Button) findViewById(R.id.baseactivity_comments);
        imageView_allpic = (ImageView) findViewById(R.id.baseactivity_all);
        text_title = (TextView) findViewById(R.id.baseactivity_titil);
    }

    /**
     * 设置标题 显示返回键 不显示 评论键
     * @param title 标题
     */
    protected void initTitleBarShowBackBtn(String title){
        initTitleBar(title,true,false);
    }

    /**
     * 设置标题 只显示标题
     * @param title 标题
     */
    protected void initTitleBar(String title){
        initTitleBar(title,false,false);
    }

    /**
     * 红色标题栏内容初始化
     *
     * @param title 标题
     * @param showBackBtn 是否显示左边的返回键
     * @param showCommentBtn 是否显示右边d的评论按钮
     */
    protected void initTitleBar(String title,boolean showBackBtn,boolean showCommentBtn){
        btn_back.setVisibility(showBackBtn?View.VISIBLE:View.GONE);
        btn_comments.setVisibility(showCommentBtn?View.VISIBLE:View.GONE);
        text_title.setText(title);
//        TODO 还需要补足  ImageView的操作
//        imageView_allpic = (ImageView) findViewById(R.id.baseactivity_all);
    }
}
