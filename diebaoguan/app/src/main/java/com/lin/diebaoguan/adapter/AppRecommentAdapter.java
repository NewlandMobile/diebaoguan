package com.lin.diebaoguan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.common.IMAGEUtils;
import com.lin.diebaoguan.network.bean.RecommendAppList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linx on 2017/1/1718:27.
 * mail :1057705307@QQ.com.
 * describe:
 */
public class AppRecommentAdapter extends BaseAdapter {
    private Context context;
    private List<RecommendAppList> list = new ArrayList<>();

    public AppRecommentAdapter(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecommendAppList recommendApp = list.get(position);
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_apprecomment, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.apprecom_image);
        TextView text_name = (TextView) inflate.findViewById(R.id.apprecom_name);
        TextView text_goodname = (TextView) inflate.findViewById(R.id.apprecom_goodname);
        text_name.setText(recommendApp.getGoodName());
        text_goodname.setText(recommendApp.getName());
        IMAGEUtils.displayImage(recommendApp.getHeadPictureSrc(), imageView);
        final String downloadUrl = recommendApp.getDownloadUrl();
        Button btn_download = (Button) inflate.findViewById(R.id.apprecom_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(downloadUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
        return inflate;
    }
}
