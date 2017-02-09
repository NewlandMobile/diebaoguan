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
import com.lin.diebaoguan.uibase.BaseCommentAndShareActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by linx on 2017/1/1610:41.
 * mail :1057705307@QQ.com.
 * describe:
 */
public class AmzDetailActivity extends BaseCommentAndShareActivity {
    private Button detail_save;
    private Button detail_share;
    private TextView tv_content;
    private TextView tv_title;
    private TextView tv_pageNum;
    private ImageView showMoreImageView;
    private ViewPager viewPager_gyj;

    private int picCount = 0;//总共的页数
    private ArrayList<String> urls = new ArrayList<>();//图片资源
    private String isCollected;
    private String contentStr = "";//文章内容
    private MyViewPagerAdapter adapter;
    private String title;
    private View mCurrentView;//当前的viewpager's item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPublicUI(getString(R.string.aimeizhi), true, R.layout.activity_gyj_original_details);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        detail_save = (Button) findViewById(R.id.detail_save);
        detail_save.setVisibility(View.VISIBLE);
        detail_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AmzDetailActivity.this, "进入保存", Toast.LENGTH_SHORT).show();
                //获取内部存储状态
                String state = Environment.getExternalStorageState();
                //如果状态不是mounted，无法读写
                if (!state.equals(Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(AmzDetailActivity.this, R.string.isHaveSdcard, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    CommonUtils.saveBitmapToSDCard(AmzDetailActivity.this, mCurrentView);
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
        adapter = new MyViewPagerAdapter();

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(info);
            title = jsonObject.getString("title");
            tv_title.setText(title);
            isCollected = jsonObject.getString("isCollected");
            int cid = jsonObject.getInt("cid");
            setCollectedAndTitle("1".equals(isCollected), title);
            setCid(cid);
            JSONArray content = jsonObject.getJSONArray("content");
            for (int i = 0; i < content.length(); i++) {
                contentStr = (String) content.get(i);
            }
            tv_content.setText(contentStr);
            JSONArray picUrl = jsonObject.getJSONArray("picUrl");
            for (int i = 0; i < picUrl.length(); i++) {
                urls.add((String) picUrl.get(i));
            }
            viewPager_gyj.setAdapter(adapter);
            //获取docid
            docid = jsonObject.getString("docid");
            picCount = urls.size();
            tv_pageNum.setText("1/" + picCount);
            viewPager_gyj.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tv_pageNum.setText((position + 1) + "/" + picCount);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imageView_allpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AmzDetailActivity.this, GyjDetailPicActivity.class);
                intent.putStringArrayListExtra("pic", urls);
                intent.putExtra("title", title);
                intent.putExtra("id", docid);
                startActivityForResult(intent, 2);
            }
        });

    }

    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(AmzDetailActivity.this);
            IMAGEUtils.displayImage(urls.get(position), view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
//           super.setPrimaryItem(container, position, object);//这一步在源码中其实什么都没有实现，可以去掉
            //因为该方法是在selected之后才执行，所以操作应该放在当前方法中，不可放在onPageSelected中
            mCurrentView = (View) object;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data.getBooleanExtra("haveChange", false)) {
                int pageNum = data.getIntExtra("pageNum", 0);
                viewPager_gyj.setCurrentItem(pageNum);
            }
        }
    }
}
