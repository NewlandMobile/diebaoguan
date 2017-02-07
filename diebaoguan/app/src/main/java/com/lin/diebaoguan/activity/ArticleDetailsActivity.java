package com.lin.diebaoguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.fragment.ArticalItemFragment;
import com.lin.diebaoguan.network.bean.Detail;
import com.lin.diebaoguan.network.bean.Result;
import com.lin.diebaoguan.uibase.BaseCommentAndShareActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章详情界面
 */
public class ArticleDetailsActivity extends BaseCommentAndShareActivity {

    //    private ArrayList<Result> dataList = new ArrayList<>();//资源数据集合
    private MyAdapter adapter;
    private List<Detail> dataArray = new ArrayList<>();
    private String activityLog;
    private Detail detail;//当前fragment所在的item
    private int currentOffset;//当前viewpager的偏移量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPublicUI(getString(R.string.articdetail), false, R.layout.activity_article_details);
        initView();
    }

    private void initView() {
        //数据处理
        Intent intent = getIntent();
        activityLog = intent.getStringExtra("ActivityLog");
        List<Result> list = (List<Result>) intent.getSerializableExtra("allItem");
        currentOffset = intent.getIntExtra("currentOffset", 0);
        Result currentItem = list.get(currentOffset);
        List<Detail> detailList = new ArrayList<>();
        if ("GYJSYNTH".equals(activityLog)) {  //如果是由光影集综合跳转过来的
            docid = "" + currentItem.getPicid();
            //对本地数据初始化，默认没有收藏，在联网获取完数据之后对收藏值更新
            for (Result item : list) {
                Detail detail = new Detail();
                detail.setPicid(item.getPicid());
                detail.setCollected(false);
                detailList.add(detail);
            }
        } else {
            docid = currentItem.getDocid();
            for (Result item : list) {
                Detail detail = new Detail();
                detail.setDocid(item.getDocid());
                detail.setCollected(false);
                detailList.add(detail);
            }
        }
        dataArray.addAll(detailList);

        ViewPager viewPager = (ViewPager) findViewById(R.id.detail_viewpager);
        FragmentManager fm = getSupportFragmentManager();
        adapter = new MyAdapter(fm);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentOffset);
        viewPager.addOnPageChangeListener(new MyPageChangeListener());
        setRunnable(new Runnable() {
            @Override
            public void run() {
                detail.setCollected(!detail.isCollected());
            }
        });

    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentOffset = position;
            detail = dataArray.get(currentOffset);
            //  实时更新 当前文章的docid
            docid = detail.getDocid();
            //获取当前的item并将其是否收藏的值传入
            boolean collected1 = detail.isCollected();
            LogUtils.e("collected==" + collected1);
            isCollected = collected1;
            changeCollectState(collected1);
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
            Detail result = dataArray.get(position);
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
            return dataArray == null ? 0 : dataArray.size();
        }
    }

    /**
     * 重写实现本地数据的实时更新
     *
     * @param collected
     */
    public void setCollectedAndTitle(boolean collected, String docid) {
        for (Detail item : dataArray) {
            if (docid.equals(item.getDocid())) {
                item.setCollected(collected);
                item.setTitle(item.getTitle());
            }
        }
        //获取当前的item并将其是否收藏的值传入
        detail = dataArray.get(currentOffset);
        boolean collected1 = detail.isCollected();
        isCollected = collected1;
        changeCollectState(collected1);
    }

}
