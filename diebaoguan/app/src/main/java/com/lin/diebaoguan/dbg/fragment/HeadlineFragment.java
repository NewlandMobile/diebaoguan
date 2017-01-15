package com.lin.diebaoguan.dbg.fragment;


import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.uibase.BasePullToRefrshListViewFragment;
import com.lin.lib_volley_https.VolleyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 头条界面
 */
public class HeadlineFragment extends BasePullToRefrshListViewFragment implements ViewPager.OnPageChangeListener {


    private View view;
    private ViewPager viewPager;
    private int[] imageId = {R.drawable.home_banner_1, R.drawable.home_banner_2,
            R.drawable.home_banner_3, R.drawable.home_banner_4, R.drawable.home_banner_5, R.drawable.home_banner_6};
    private List<ImageView> list = new ArrayList<>();
    private int[] points = {R.id.headline_point1, R.id.headline_point2,
            R.id.headline_point3, R.id.headline_point4, R.id.headline_point5, R.id.headline_point6};
    private List<ImageView> pointList = new ArrayList<>();
    public boolean isDrag;//判断是否有滑动的操作
    private int beforeInt = 0;
    private View headView;

    public HeadlineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            initImageId();
            view = super.onCreateView(inflater, container, savedInstanceState);
            headView = getActivity().getLayoutInflater().inflate(R.layout.view_ad, null);
            initPoint();
            viewPager = (ViewPager) headView.findViewById(R.id.headline_viewpager);
            viewPager.setAdapter(new MyAdapter());
            refreshableView.addHeaderView(headView);
            adapter.notifyDataSetChanged();
            postDelayAd();
            viewPager.setOnPageChangeListener(this);
        }
        return view;
    }

    /**
     * 加载徐涛滚动的图片资源
     */
    private void initImageId() {
        for (int i = 0; i < imageId.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackground(getResources().getDrawable(imageId[i]));
            list.add(imageView);
        }
    }

    /**
     * 初始化点点
     */
    private void initPoint() {
        for (int i = 0; i < points.length; i++) {
            ImageView imageView = (ImageView) headView.findViewById(points[i]);
            pointList.add(imageView);
        }
    }

    /**
     * 广告自动跳动
     */
    private void postDelayAd() {
        viewPager.postDelayed(new Runnable() {

            @Override
            public void run() {
                int curItem = viewPager.getCurrentItem();
                if (!isDrag) {
                    curItem++;
                    viewPager.setCurrentItem(curItem);
                }
                viewPager.postDelayed(this, 3000);
            }
        }, 3000);
    }

    @Override
    protected void getData(int pageOffset, VolleyListener volleyListener) {
        CommonUtils.fetchDataAtFsbOrDbg(pageOffset, this, true, 4, volleyListener);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        pointList.get(position % 6).setImageDrawable(getResources().getDrawable(R.drawable.red_point));
        pointList.get(beforeInt).setImageDrawable(getResources().getDrawable(R.drawable.gray_point));
        beforeInt = position % 6;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE://默认 没有操作
                isDrag = false;
                break;
            case ViewPager.SCROLL_STATE_DRAGGING://被滑动
                isDrag = true;
                break;
            case ViewPager.SCROLL_STATE_SETTLING://滑动结束
                isDrag = false;
                break;

        }
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3000000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = list.get(position % 6);
            container.addView(imageView);
            return imageView;
        }
    }
}