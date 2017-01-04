package com.lin.diebaoguan.gyj.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;
import com.lin.diebaoguan.network.bean.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * 精品原创
 * A simple {@link Fragment} subclass.
 */
public class OriginalFragment extends PullToRefreshBaseFragment {

//    private List<Result>  dataList=null;

    private GridView gridView;
    private BaseAdapter myAdapter=null;

    public OriginalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.fragment_original, container, false);

        initView(baseView);
        return baseView;
    }

    private void initView(View baseView) {
        gridView = (GridView) baseView.findViewById(R.id.gridView);
        myAdapter=new MyAdapter();
        gridView.setAdapter(myAdapter);
    }

    class MyAdapter extends BaseAdapter{

        class ViewHolder {
            TextView textView;
            ImageView imageView;
        }
        private List<Result>  dataList=new ArrayList<>();
        private LayoutInflater layoutInflater=LayoutInflater.from(getActivity());

        @Override
        public int getCount() {
            return dataList.size();
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
            ViewHolder viewHolder;
            if (convertView==null){
                convertView=layoutInflater.inflate(R.layout.item_gridview_gyj,null);
                viewHolder=new ViewHolder();
                convertView.setTag(viewHolder);
                viewHolder.textView= (TextView) convertView.findViewById(R.id.textView2);
                viewHolder.imageView= (ImageView) convertView.findViewById(R.id.imageView3);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
//            viewHolder.imageView.se
            
            return convertView;
        }
    }
}
