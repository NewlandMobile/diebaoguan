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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initTitleBar(, true, true, false, R.layout.activity_article_details);
        initPublicUI(getString(R.string.articdetail),false, R.layout.activity_article_details);
//        initBottomPart();
        initView();
    }

//    public void initBottomPart(){
//        super.initBottomPart();
//    }

    private void initView() {
        Intent intent = getIntent();
        dataArray = (Object[]) intent.getSerializableExtra("allItem");
//        List<Result>
        int currentOffset = intent.getIntExtra("currentOffset", 0);
        Result currentItem = (Result) dataArray[currentOffset];
//        docid = intent.getStringExtra("id");
        docid = currentItem.getDocid();
//        LogUtils.e("docid: " + docid);
//        int position = intent.getIntExtra("position", 0);
//        LogUtils.e("==position=" + position);
//        ArrayList<Result> arrayList = (ArrayList<Result>) intent.getSerializableExtra("datalsit");
//        LogUtils.e("==" + arrayList.size());
//        dataList.addAll(arrayList);

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
            ArticalItemFragment articalItemFragment = new ArticalItemFragment();
//            int docid = dataList.get(position).getDocid();
            String docid = ((Result) dataArray[position]).getDocid();
            articalItemFragment.setDocid(docid);
//            ArticleDetailsActivity.this.docid = docid + "";
//            LogUtils.e("==docid==" + docid);
            return articalItemFragment;
        }

        @Override
        public int getCount() {
//            return dataList == null ? 0 : dataList.size();

            return dataArray == null ? 0 : dataArray.length;
        }
    }
}
