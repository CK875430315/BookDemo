package com.dianzhi.yxt.mvp.presenter;

import com.dianzhi.yxt.mvp.view.BaseView;

/**
 * Created by CK on 2017/2/17.
 */

public interface BasePresenter<T extends BaseView>{

//    void attachView(T view);

//    void subscribe();

    void unsubscribe();

}
