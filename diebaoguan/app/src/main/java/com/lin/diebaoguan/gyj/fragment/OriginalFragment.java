package com.lin.diebaoguan.gyj.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.lin.diebaoguan.R;
import com.lin.diebaoguan.fragment.PullToRefreshBaseFragment;

import java.util.List;

/**
 * 精品原创
 * A simple {@link Fragment} subclass.
 */
public class OriginalFragment extends PullToRefreshBaseFragment {

//    private List<>

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

        @Override
        public int getCount() {
            return 0;
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
}
