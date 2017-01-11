package com.lin.diebaoguan.uibase;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lin.diebaoguan.R;

/**
 * 带红色标题栏的公共父类
 */
public class BaseRedTitleBarActivity extends BaseActivity {

    protected Button btn_back;//返回
    protected Button btn_comments;//查看跟帖
    protected ImageView imageView_allpic;//查看所有图片
    protected TextView text_title;//设置标题
    private RelativeLayout rl_content;//内容

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
        rl_content = (RelativeLayout) findViewById(R.id.baseactivity_content);

    }


    /**
     * 红色标题栏内容初始化,在执行完oncreat之后，必须执行该方法来保证内容
     * 的正确显示加载
     *
     * @param title          标题
     * @param showBackBtn    是否显示左边的返回键
     * @param showCommentBtn 是否显示右边的评论按钮
     */
    protected void initTitleBar(String title, boolean showBackBtn, boolean showCommentBtn, boolean showImage, int layoutId) {
        btn_back.setVisibility(showBackBtn ? View.VISIBLE : View.GONE);
        btn_comments.setVisibility(showCommentBtn ? View.VISIBLE : View.GONE);
        imageView_allpic.setVisibility(showImage ? View.VISIBLE : View.GONE);
        text_title.setText(title);
        View view = getLayoutInflater().inflate(layoutId, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rl_content.addView(view, layoutParams);
    }
}
