package com.lin.diebaoguan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lin.diebaoguan.R;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linx on 2017/1/415:14.
 * mail :1057705307@QQ.com.
 * describe:评论适配器
 */
public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> dataList = new ArrayList<>();

    public CommentAdapter(Context context, List<Comment> list) {
        this.context = context;
        this.dataList = list;
    }

    @Override
    public int getCount() {
        return 20;
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

        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, null);

        return view;
    }
}
