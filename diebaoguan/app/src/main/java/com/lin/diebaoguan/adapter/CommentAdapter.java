package com.lin.diebaoguan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.IMAGEUtils;
import com.lin.diebaoguan.network.bean.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linx on 2017/1/415:14.
 * mail :1057705307@QQ.com.
 * describe:评论适配器
 */
public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<Result> dataList = new ArrayList<>();

    public CommentAdapter(Context context, List<Result> list) {
        this.context = context;
        this.dataList = list;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList == null ? null : dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataList == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
        Result result = dataList.get(position);

        TextView name = (TextView) view.findViewById(R.id.comment_name);
        TextView content = (TextView) view.findViewById(R.id.comment_content);
        TextView time = (TextView) view.findViewById(R.id.comment_time);
        ImageView imageView = (ImageView) view.findViewById(R.id.comment_pic);
        name.setText(result.getUsername());
        content.setText(result.getContent());
        time.setText(result.getDate());
        IMAGEUtils.displayImage(result.getAvatar(), imageView);
        return view;
    }
}
