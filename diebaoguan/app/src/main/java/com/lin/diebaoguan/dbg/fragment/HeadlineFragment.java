package com.lin.diebaoguan.dbg.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.activity.ArticleDetailsActivity;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.Const;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.uibase.BasePullToRefrshListViewFragment;
import com.lin.lib_volley_https.VolleyListener;

import java.io.Serializable;
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
    private TextView adtitle;//广告标题
    private List<ImageView> reList = new ArrayList<>();

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
            adtitle = (TextView) headView.findViewById(R.id.text_adtitle);
            postDelayAd();
            viewPager.setOnPageChangeListener(this);
            refreshableView.setOnItemClickListener(this);
        }
        return view;
    }

    /**
     * 加载滚动的图片资源
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
    }

    @Override
    public void onPageSelected(int position) {
        if (isAdded()) {
            int pi = position % 6;
            pointList.get(pi).setBackground(getResources().getDrawable(R.drawable.red_point));
            pointList.get(beforeInt).setBackground(getResources().getDrawable(R.drawable.gray_point));
            adtitle.setText(Const.PUSHAD[pi]);
            beforeInt = pi;
        }
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
        ImageView imageView = null;

        @Override
        public int getCount() {
            return 300000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            LogUtils.e("removeView=====position:" + position);
            container.removeView((View) object);
            reList.add((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LogUtils.e("instantiateItem=====position:" + position);
            int size = reList.size();
            if (size == 0) {
                imageView = new ImageView(getActivity());
            } else {
                imageView = reList.get(0);
                reList.remove(imageView);
            }
            imageView.setBackground(getResources().getDrawable(imageId[position % 6]));
            container.addView(imageView);
            return imageView;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
        intent.putExtra("currentOffset", position - 2);
        intent.putExtra("allItem", (Serializable) dataList);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        ViewGroup parent = (ViewGroup) view.getParent();
        parent.removeView(view);
        super.onDestroyView();
    }
}