package com.dianzhi.yxt.base;

import android.view.View;

/**
 * Created by CK on 2016/10/20.
 */
public abstract class BViewHolder<T> {
    public abstract void initView(View view);
    public abstract void setData(T t,int position);
}

