package com.lin.diebaoguan.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.lin.diebaoguan.BaseRedTitleBarActivity;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.adapter.CommentAdapter;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论界面
 */
public class CommentActivity extends BaseRedTitleBarActivity {
    private List<Comment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("更多评论", true, false, false, R.layout.activity_comment);
        initView();
        getDate();
    }

    private void initView() {
        ListView listView = (ListView) findViewById(R.id.comment_list);
        listView.setAdapter(new CommentAdapter(this, list));
    }

    /**
     * 获取数据
     */
    public void getDate() {


    }
}
