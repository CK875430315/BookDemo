package com.dianzhi.yxt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dianzhi.yxt.R;
import com.dianzhi.yxt.ui.view.HorizontalListView;

import org.xutils.x;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CK on 2017/4/18.
 */

public class HomeAdapter extends RecyclerView.Adapter{
   private ArrayList<String> datas=new ArrayList<>();
   private Context context;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == 0)
//            return new TopHolder(View.inflate(context, R.layout.home_list_header, null));
//         else
            return new ItemHolder(View.inflate(context, R.layout.home_list_item, null));

    }

    public HomeAdapter(ArrayList<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.tv.setText(datas.get(position));
        } else {



        }
    }



    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    static class  TopHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.header_top_tab)
        HorizontalListView header_top_tab;
        public TopHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
//            final TopHolder topHolder= (TopHolder) holder;
            ArrayList<String> dataTop=new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                dataTop.add("测试"+i);
            }
           header_top_tab.setAdapter(new ArrayAdapter<String>(x.app(),android.R.layout.simple_list_item_1,android.R.id.text1,dataTop));
        }
    }
    static class  ItemHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView tv;
        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
