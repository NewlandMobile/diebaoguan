package com.lin.diebaoguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.network.bean.Data;

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

    int picCount=0;
    private String[] urls;
    private Data data;
//    private String picId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPublicUI(getString(R.string.JingPinOrigin),true,
                R.layout.activity_gyj_original_details);
        initShowMoreImageBtn();
//        initTitleBar(getString(R.string.JingPinOrigin), true, true, true, R.layout.activity_gyj_original_details);
//        iniTitleButton();
        initView();
        data= (Data) getIntent().getSerializableExtra("Data");
        if (data!=null){
            docid=data.getId();
            setCid(data.getCid());
            setCollected("1".equals(data.getIsCollected()));
            initViewPager(data);
            initTextPart(data);
        }
    }

    private void initShowMoreImageBtn() {
        imageView_allpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GyjOriginalDetailsActivity.this,GyjDetailPicActivity.class);
                intent.putExtra("Data",data);
                startActivityForResult(intent,1);
            }
        });
    }

//    private void iniTitleButton() {
//        btn_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        imageView_allpic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(GyjOriginalDetailsActivity.this,GyjDetailPicActivity.class);
//                intent.putExtra("Data",data);
////                intent.putExtra("Title",tv_title.getText());
////                intent.putExtra("Pics",urls);
//                startActivityForResult(intent,1);
////                startActivity(intent);
////                viewPager_gyj.setCurrentItem();
//            }
//        });
//        btn_comments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(GyjOriginalDetailsActivity.this,CommentActivity.class);
//                intent.putExtra("docid",picId);
//                startActivity(intent);
//            }
//        });
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode!=1){
            return;
        }
        if (data==null){
            return;
        }
        if (data.getBooleanExtra("haveChange",false)){
            int pageNum=data.getIntExtra("pageNum",0);
            viewPager_gyj.setCurrentItem(pageNum);
        }

    }

    private void initTextPart(Data data) {
        tv_title.setText(data.getTitle());
        tv_content.setText(data.getDigest());
    }

    private void initViewPager(Data data) {
        MyViewPagerAdapter adapter=new MyViewPagerAdapter();

        urls=data.getPicUrl();
        picCount=urls.length;
        adapter.count=picCount;
        tv_pageNum.setText("1/"+picCount);
        cachViewsList =new ArrayList<>(4);
        for (int i=0;i<4;i++){
            ImageView imageView=new ImageView(this);
//            IMAGEUtils.displayImage(url,imageView);
//            viewPager_gyj.addView(imageView);
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
                LogUtils.d("position:"+position);
                tv_pageNum.setText((position+1)+"/"+picCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                LogUtils.d("state:"+state);

            }
        });
    }

    private class MyViewPagerAdapter extends PagerAdapter{

        int count;

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
//            LogUtils.d("View:"+view+";Object:"+object);
            return view.equals(object);
//            return true;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            String url=urls[position];
//            ImageView imageView=new ImageView(GyjOriginalDetailsActivity.this);
//            IMAGEUtils.displayImage(url,imageView);

            ImageView view=null;
            if (cachViewsList.size()!=0){
                view   =  cachViewsList.get(0);
            }
            if (view==null){
                view=new ImageView(GyjOriginalDetailsActivity.this);
            }else {
                cachViewsList.remove(view);
            }
            IMAGEUtils.displayImage(urls[position],view);
            viewPager_gyj.addView(view);
//            LogUtils.d("after addView at"+position);
//            return super.instantiateItem(container, position);
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
    }

    private void initView() {

//        detail_textview = (TextView) findViewById(R.id.detail_textview);
//        detail_textview.setOnClickListener(this);
//        detail_collect = (ImageView) findViewById(R.id.detail_collect);
//        detail_collect.setOnClickListener(this);
        detail_save = (Button) findViewById(R.id.detail_save);
        detail_save.setVisibility(View.VISIBLE);
        detail_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  保存
            }
        });
//        detail_share = (Button) findViewById(R.id.detail_share);
//        detail_share.setOnClickListener(this);
//        rl1 = (LinearLayout) findViewById(R.id.rl1);
//        detail_send = (Button) findViewById(R.id.detail_send);
//        detail_send.setOnClickListener(this);
//        detail_edit = (EditText) findViewById(R.id.detail_edit);
//        detail_edit.setOnClickListener(this);
//        rl2 = (RelativeLayout) findViewById(R.id.rl2);
//        rl2.setOnClickListener(this);
//        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
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
//        ll_title_part = (LinearLayout) findViewById(R.id.ll_title_part);
        viewPager_gyj = (ViewPager) findViewById(R.id.viewPager_gyj);
//        viewPager_gyj.setCurrentItem();
//        viewPager_gyj.setOnClickListener(this);
    }

    private void changeShowMoreState() {
        boolean isSelect=showMoreImageView.isSelected();
        if (!isSelect){
            tv_content.setMaxLines(15);
        }else {
            tv_content.setMaxLines(1);
        }
        showMoreImageView.setSelected(!isSelect);
    }

//    private void submit() {
//        // validate
//        String edit = detail_edit.getText().toString().trim();
//        if (TextUtils.isEmpty(edit)) {
//            Toast.makeText(this, "edit不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//    }
}
