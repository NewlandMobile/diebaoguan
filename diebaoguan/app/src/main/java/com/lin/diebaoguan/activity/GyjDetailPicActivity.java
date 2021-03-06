package com.lin.diebaoguan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.IMAGEUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.lin.diebaoguan.uibase.BaseRedTitleBarActivity;

import java.util.ArrayList;

/**
 * It's Created by NewLand-JianFeng on 2017/1/9.
 */

public class GyjDetailPicActivity extends BaseRedTitleBarActivity {
    private GridView gridView_Image_browsing;
    private ArrayList<String> urls = new ArrayList<>();
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        LogUtils.e("==title==" + title);
        urls = intent.getStringArrayListExtra("pic");
        for (int i = 0; i < urls.size(); i++) {
            LogUtils.e("==urls==" + urls.get(i));
        }
        id = intent.getStringExtra("id");
        if (title == null | urls == null) {
            return;
        }
        initTitleBar(title, true, true, false, R.layout.activity_detail_pic_gyj);
        initView();
        initBackBtn();
        initCommentBtn();
    }

    private void initCommentBtn() {
        btn_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GyjDetailPicActivity.this, CommentActivity.class);
                intent.putExtra("docid", id);
                startActivity(intent);
//                setResult(0,intent);
//                finish();
            }
        });
    }

    private void initBackBtn() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("haveChange", false);
                setResult(0, intent);
                finish();
            }
        });
    }

    private void initView() {
        gridView_Image_browsing = (GridView) findViewById(R.id.gridView_Image_browsing);
        BaseAdapter adapter = new BaseAdapter() {
            LayoutInflater layoutInflater = getLayoutInflater();

            @Override
            public int getCount() {
                return urls.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView;
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.item_gyj_image_brows_gridview, null);
                    imageView = (ImageView) convertView.findViewById(R.id.item_grid_pic_browsing);
                    convertView.setTag(imageView);
                } else {
                    imageView = (ImageView) convertView.getTag();
                }
                IMAGEUtils.displayImage(urls.get(position), imageView);
                return convertView;
            }
        };
        gridView_Image_browsing.setAdapter(adapter);
        gridView_Image_browsing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("haveChange", true);
                intent.putExtra("pageNum", position);
                setResult(0, intent);
                finish();
            }
        });
    }
}
