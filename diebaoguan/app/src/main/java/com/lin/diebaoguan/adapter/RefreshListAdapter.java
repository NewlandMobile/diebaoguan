package com.lin.diebaoguan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lin.diebaoguan.R;

import java.util.List;

/**
 * Created by linx on 2016/12/2809:49.
 * mail :1057705307@QQ.com.
 * describe:作为下拉刷新listview类型的适配器
 */
public class RefreshListAdapter extends BaseAdapter {

    private Context context;
    private List<String> datalist;

    public RefreshListAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.datalist = dataList;
    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, null);
        return view;
    }
}
