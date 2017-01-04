package com.lin.diebaoguan.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.lin.diebaoguan.BaseRedTitleBarActivity;
import com.lin.diebaoguan.R;

/**
 * 评论界面
 */
public class CommentActivity extends BaseRedTitleBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("更多评论", true, false, false, R.layout.activity_comment);
        initView();
    }

    private void initView() {
        ListView listView = (ListView)findViewById(R.id.comment_list);
//        listView.setAdapter();
    }
}
