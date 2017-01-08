package com.lin.diebaoguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lin.diebaoguan.BaseRedTitleBarActivity;
import com.lin.diebaoguan.MyAppication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.fsb.fragment.ArticalItemFragment;
import com.lin.diebaoguan.network.bean.Result;

import java.util.ArrayList;

/**
 * 文章详情界面
 */
public class ArticleDetailsActivity extends BaseRedTitleBarActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private String docid;

    private String uid = "";
    private ArrayList<Result> datalist = new ArrayList<>();//资源数据集合
    private int position = 0;//用来记录初始position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar(getString(R.string.articdetail), true, true, false, R.layout.activity_article_details);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        docid = intent.getStringExtra("id");
        LogUtils.e("docid: " + docid);
        position = intent.getIntExtra("position", 0);
        LogUtils.e("==position=" + position);
        ArrayList<Result> arrayList = (ArrayList<Result>) intent.getSerializableExtra("datalsit");
        LogUtils.e("==" + arrayList.size());
        datalist.addAll(arrayList);
        btn_comments.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        ViewPager viewPager = (ViewPager) findViewById(R.id.detail_viewpager);
        FragmentManager fm = getSupportFragmentManager();
        MyAdapter adapter = new MyAdapter(fm);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baseactivity_back:
                finish();
                break;
            case R.id.baseactivity_comments:
                MyAppication application = MyAppication.getInstance();
                boolean isLogined = application.hasLogined();
                if (isLogined) {
                    Intent intent = new Intent(this, CommentActivity.class);
                    intent.putExtra("docid", docid);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
        }
    }


    class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ArticalItemFragment articalItemFragment = new ArticalItemFragment();
            int docid = datalist.get(position).getDocid();
            articalItemFragment.setDocid("" + docid);
            ArticleDetailsActivity.this.docid = docid + "";
            LogUtils.e("==docid==" + docid);
            return articalItemFragment;
        }

        @Override
        public int getCount() {
            return datalist == null ? 0 : datalist.size();
        }
    }
}
