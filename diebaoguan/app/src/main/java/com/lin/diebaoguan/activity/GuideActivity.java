package com.lin.diebaoguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.lin.diebaoguan.uibase.BaseActivity;
import com.lin.diebaoguan.MainActivity;
import com.lin.diebaoguan.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private int[] images = {R.drawable.guide_page1, R.drawable.guide_page2,
            R.drawable.guide_page3, R.drawable.guide_page4, R.drawable.guide_page5};
    private List<ImageView> imageList = new ArrayList<>();
    private List<ImageView> pointList = new ArrayList<>();
    private int[] points = {R.id.guide_point1, R.id.guide_point2,
            R.id.guide_point3, R.id.guide_point4, R.id.guide_point5};
    private int beforeInt = 0;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initImages();
        initPoint();
        initView();
    }

    private void initPoint() {
        for (int i = 0; i < points.length; i++) {
            ImageView imageView = (ImageView) findViewById(points[i]);
            pointList.add(imageView);
        }
    }

    private void initImages() {
        for (int i = 0; i < images.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(images[i]);
            imageList.add(imageView);
        }
    }


    private void initView() {
        btn_start = (Button) findViewById(R.id.guide_start);
        btn_start.setOnClickListener(this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.guide_viewpager);
        MyAdapter adapter = new MyAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pointList.get(position).setImageDrawable(getResources().getDrawable(R.drawable.screenview_seekpoint_selected));
        pointList.get(beforeInt).setImageDrawable(getResources().getDrawable(R.drawable.screenview_seekpoint_normal));
        beforeInt = position;
        if (position == 4) {
            btn_start.setVisibility(View.VISIBLE);
        } else {
            btn_start.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        ;
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageList.get(position);
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            imageView.setLayoutParams(layoutParams);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageList.get(position));
        }
    }
}
