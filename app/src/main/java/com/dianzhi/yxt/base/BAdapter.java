package com.dianzhi.yxt.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by CK on 2016/10/20.
 */
public abstract class BAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mList;

    private int resId;

    public BAdapter(Context context, List<T> list, int resId) {
        this.mContext = context;
        this.mList = list;
        this.resId = resId;
    }
    public void refresh(List<T> list){
        mList=list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(resId, null);
            viewHolder = getHolder();
            viewHolder.initView(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BViewHolder) convertView.getTag();
        }
        viewHolder.setData(mList.get(position),position);
        return convertView;
    }

    public abstract BViewHolder getHolder();
}