package com.lin.diebaoguan.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lin.diebaoguan.BaseRedTitleBarActivity;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.IMAGEUtils;
import com.lin.diebaoguan.network.bean.Data;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * It's Created by NewLand-JianFeng on 2017/1/5.
 */

public class GyjOriginalDetailsActivity extends BaseRedTitleBarActivity implements View.OnClickListener {
    private TextView detail_textview;
    private ImageView detail_collect;
    private Button detail_save;
    private Button detail_share;
    private LinearLayout rl1;
    private Button detail_send;
    private EditText detail_edit;
    private RelativeLayout rl2;
    private RelativeLayout relativeLayout;
    private TextView tv_content;
//    private View dividerline_bet_title_and_content;
    private TextView tv_title;
    private TextView tv_pageNum;
    private ImageView imageView4;
    private LinearLayout ll_title_part;
//    private RelativeLayout activity_article_details;
    private ViewPager viewPager_gyj;

    int picCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(getString(R.string.JingPinOrigin), true, true, false, R.layout.activity_gyj_original_details);
        initView();
        Data data= (Data) getIntent().getSerializableExtra("Data");
        if (data!=null){
            initViewPager(data);
            initTextPart(data);
        }

    }

    private void initTextPart(Data data) {
        tv_title.setText(data.getTitle());
//        TODO  内容部分要加上
//        tv_content.setText(data.get);
    }

    private void initViewPager(Data data) {
        MyViewPagerAdapter adapter=new MyViewPagerAdapter();
        viewPager_gyj.setAdapter(adapter);
        String[] urls=data.getPicUrl();

        tv_pageNum.setText("1/"+picCount);
        for (String url:urls){
            ImageView imageView=new ImageView(this);
            IMAGEUtils.displayImage(url,imageView);
            viewPager_gyj.addView(imageView);
        }
        picCount=urls.length;
    }

    private class MyViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return picCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return false;
        }
    }

    private void initView() {

        detail_textview = (TextView) findViewById(R.id.detail_textview);
        detail_textview.setOnClickListener(this);
        detail_collect = (ImageView) findViewById(R.id.detail_collect);
        detail_collect.setOnClickListener(this);
        detail_save = (Button) findViewById(R.id.detail_save);
        detail_save.setOnClickListener(this);
        detail_share = (Button) findViewById(R.id.detail_share);
        detail_share.setOnClickListener(this);
        rl1 = (LinearLayout) findViewById(R.id.rl1);
        detail_send = (Button) findViewById(R.id.detail_send);
        detail_send.setOnClickListener(this);
        detail_edit = (EditText) findViewById(R.id.detail_edit);
        detail_edit.setOnClickListener(this);
        rl2 = (RelativeLayout) findViewById(R.id.rl2);
        rl2.setOnClickListener(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_pageNum = (TextView) findViewById(R.id.tv_pageNum);
        imageView4 = (ImageView) findViewById(R.id.im_show_more);
        imageView4.setOnClickListener(this);
        ll_title_part = (LinearLayout) findViewById(R.id.ll_title_part);
        viewPager_gyj = (ViewPager) findViewById(R.id.viewPager_gyj);
//        viewPager_gyj.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_save:

                break;
            case R.id.detail_share:

                break;
            case R.id.detail_send:

                break;
        }
    }

    private void submit() {
        // validate
        String edit = detail_edit.getText().toString().trim();
        if (TextUtils.isEmpty(edit)) {
            Toast.makeText(this, "edit不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
