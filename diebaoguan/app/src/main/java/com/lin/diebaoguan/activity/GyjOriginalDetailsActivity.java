package com.lin.diebaoguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.IMAGEUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Data;
import com.lin.diebaoguan.uibase.BaseCommentAndShareActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * It's Created by NewLand-JianFeng on 2017/1/5.
 */

public class GyjOriginalDetailsActivity extends BaseCommentAndShareActivity {
    //    private TextView detail_textview;
//    private ImageView detail_collect;
    private Button detail_save;
    private Button detail_share;
    ////    private LinearLayout rl1;
//    private Button detail_send;
//    private EditText detail_edit;
//    private RelativeLayout rl2;
//    private RelativeLayout relativeLayout;
    private TextView tv_content;
    private View dividerline_bet_title_and_content;
    private TextView tv_title;
    private TextView tv_pageNum;
    private ImageView showMoreImageView;
    //    private LinearLayout ll_title_part;
//    private RelativeLayout activity_article_details;
    private ViewPager viewPager_gyj;
    private List<ImageView> cachViewsList;

    int picCount = 0;
    private String[] urls;
    private Data data;

    private View mCurrentView;//当前的viewpager's item
//    private String picId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPublicUI(getString(R.string.picdetail), true,
                R.layout.activity_gyj_original_details);
        initShowMoreImageBtn();
        initView();
        data = (Data) getIntent().getSerializableExtra("Data");
        if (data != null) {
            docid = data.getId();
            setCid(data.getCid());
            setCollectedAndTitle("1".equals(data.getIsCollected()), data.getTitle());
            initViewPager(data);
            initTextPart(data);
        }
    }

    private void initShowMoreImageBtn() {
        imageView_allpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GyjOriginalDetailsActivity.this, GyjDetailPicActivity.class);
                intent.putExtra("title", data.getTitle());
                String[] picUrl = data.getPicUrl();
                ArrayList<String> list = new ArrayList<>();
                for (String pics : picUrl) {
                    list.add(pics);
                }
                intent.putStringArrayListExtra("pic", list);
                intent.putExtra("id", data.getId());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 1) {
            return;
        }
        if (data == null) {
            return;
        }
        if (data.getBooleanExtra("haveChange", false)) {
            int pageNum = data.getIntExtra("pageNum", 0);
            viewPager_gyj.setCurrentItem(pageNum);
        }

    }

    private void initTextPart(Data data) {
        tv_title.setText(data.getTitle());
        tv_content.setText(data.getDigest());
    }

    private void initViewPager(Data data) {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter();

        urls = data.getPicUrl();
        picCount = urls.length;
        adapter.count = picCount;
        tv_pageNum.setText("1/" + picCount);
        cachViewsList = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(this);
            cachViewsList.add(imageView);
        }
        viewPager_gyj.setAdapter(adapter);
        viewPager_gyj.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                LogUtils.d("position:"+position+";positionOffset:"+positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.d("position:" + position);
                tv_pageNum.setText((position + 1) + "/" + picCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                LogUtils.d("state:"+state);

            }
        });
    }

    private class MyViewPagerAdapter extends PagerAdapter {

        int count;

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView view = null;
            if (cachViewsList.size() != 0) {
                view = cachViewsList.get(0);
            }
            if (view == null) {
                view = new ImageView(GyjOriginalDetailsActivity.this);
            } else {
                cachViewsList.remove(view);
            }
            IMAGEUtils.displayImage(urls[position], view);
            viewPager_gyj.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            cachViewsList.add((ImageView) object);
            viewPager_gyj.removeView((View) object);

//            LogUtils.d("after removeView at"+position);
//            container.removeView();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
//           super.setPrimaryItem(container, position, object);//这一步在源码中其实什么都没有实现，可以去掉
            //因为该方法是在selected之后才执行，所以操作应该放在当前方法中，不可放在onPageSelected中
            mCurrentView = (View) object;
        }
    }

    private void initView() {
        detail_save = (Button) findViewById(R.id.detail_save);
        detail_save.setVisibility(View.VISIBLE);
        detail_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GyjOriginalDetailsActivity.this, "进入保存", Toast.LENGTH_SHORT).show();
                //获取内部存储状态
                String state = Environment.getExternalStorageState();
                //如果状态不是mounted，无法读写
                if (!state.equals(Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(GyjOriginalDetailsActivity.this, R.string.isHaveSdcard, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    CommonUtils.saveBitmapToSDCard(GyjOriginalDetailsActivity.this, mCurrentView);
                }
            }
        });
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_pageNum = (TextView) findViewById(R.id.tv_pageNum);
        showMoreImageView = (ImageView) findViewById(R.id.im_show_more);
        showMoreImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeShowMoreState();
            }
        });
        viewPager_gyj = (ViewPager) findViewById(R.id.viewPager_gyj);
    }

    private void changeShowMoreState() {
        boolean isSelect = showMoreImageView.isSelected();
        if (!isSelect) {
            tv_content.setMaxLines(15);
        } else {
            tv_content.setMaxLines(1);
        }
        showMoreImageView.setSelected(!isSelect);
    }
}
