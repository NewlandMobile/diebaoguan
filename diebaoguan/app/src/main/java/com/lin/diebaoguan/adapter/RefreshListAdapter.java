package com.lin.diebaoguan.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.network.bean.Result;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by linx on 2016/12/2809:49.
 * mail :1057705307@QQ.com.
 * describe:作为下拉刷新listview类型的适配器
 */
public class RefreshListAdapter extends BaseAdapter {

    private Context context;
    private List<Result> datalist;
    class ViewHolder{
        ImageView imageView;
        TextView title;
        TextView content;
        TextView time;
    }

    public RefreshListAdapter(Context context, List<String> dataList) {
        this.context = context;
//        this.datalist = dataList;
    }

//    TODO  这个方法这是为了  不改动太多临时加的。后期确认后要删掉
public RefreshListAdapter(Context context, List<Result> dataList,boolean test) {
    this.context = context;
    this.datalist = dataList;
}

    @Override
    public int getCount() {
        return datalist.size();
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
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.item_list, null);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.item_iamge);
            viewHolder.content= (TextView) convertView.findViewById(R.id.itme_abstract);
            viewHolder.title= (TextView) convertView.findViewById(R.id.item_title);
            viewHolder.time= (TextView) convertView.findViewById(R.id.item_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if (datalist.isEmpty()){
            return convertView;
        }
        Result result=datalist.get(position);

        URL picUrl = null;
        try {
            picUrl = new URL(result.getPicUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap pngBM = null;
        try {
            pngBM = BitmapFactory.decodeStream(picUrl.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.imageView.setImageBitmap(pngBM);
//        viewHolder.imageView.setImageURI(Uri.parse(result.getPicUrl()));
        viewHolder.content.setText(result.getContent());
        viewHolder.title.setText(result.getTitle());
        viewHolder.time.setText(result.getDate());
//        View view = LayoutInflater.from(context).inflate(R.layout.item_list, null);
        return convertView;
    }
}
