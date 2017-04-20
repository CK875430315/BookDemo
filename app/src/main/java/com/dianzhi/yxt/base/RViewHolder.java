package com.dianzhi.yxt.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by CK on 2016/10/20.
 */
public abstract class RViewHolder<T> extends RecyclerView.ViewHolder {
    public RViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(T t);
}
