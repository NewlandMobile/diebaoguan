package com.lin.diebaoguan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by linx on 2016/12/2915:43.
 * mail :1057705307@QQ.com.
 * describe: 下拉刷新，爱美志类型的适配器
 */
public class AmzAdapter extends BaseAdapter {
    private Context context;
    private List<String> datalist;

    public AmzAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.datalist = dataList;
    }

    @Override
    public int getCount() {
        return 15;
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


        return null;
    }
}
