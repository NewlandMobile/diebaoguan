package com.lin.diebaoguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.fragment.ArticalItemFragment;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.uibase.BaseCommentAndShareActivity;

/**
 * 文章详情界面
 */
public class ArticleDetailsActivity extends BaseCommentAndShareActivity {

    //    private ArrayList<Result> dataList = new ArrayList<>();//资源数据集合
    private MyAdapter adapter;
    private Object[] dataArray = null;
    private String activityLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPublicUI(getString(R.string.articdetail), false, R.layout.activity_article_details);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        activityLog = intent.getStringExtra("ActivityLog");
        dataArray = (Object[]) intent.getSerializableExtra("allItem");
        int currentOffset = intent.getIntExtra("currentOffset", 0);
        Result currentItem = (Result) dataArray[currentOffset];
        if ("GYJSYNTH".equals(activityLog)) {  //如果是由光影集综合跳转过来的
            docid = "" + currentItem.getPicid();
        } else {
            docid = currentItem.getDocid();
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.detail_viewpager);
        FragmentManager fm = getSupportFragmentManager();
        adapter = new MyAdapter(fm);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentOffset);
        viewPager.addOnPageChangeListener(new MyPageChangeListener());
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //  实时更新 当前文章的docid
            docid = ((ArticalItemFragment) adapter.getItem(position)).getDocid();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    private class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String docid;
            ArticalItemFragment articalItemFragment = new ArticalItemFragment();
            Result result = (Result) dataArray[position];
            if ("GYJSYNTH".equals(activityLog)) {  //如果是由光影集综合跳转过来的
                docid = "" + result.getPicid();
            } else {
                docid = result.getDocid();
            }
            articalItemFragment.setDocid(docid);
            return articalItemFragment;
        }

        @Override
        public int getCount() {
            return dataArray == null ? 0 : dataArray.length;
        }
    }
}
