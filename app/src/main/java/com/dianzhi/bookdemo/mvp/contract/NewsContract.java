package com.dianzhi.bookdemo.mvp.contract;

import com.dianzhi.bookdemo.mvp.presenter.BasePresenter;
import com.dianzhi.bookdemo.mvp.view.BaseView;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by CK on 2017/2/17.
 */

public class NewsContract {
    public static CompositeSubscription mCompositeSubscription;

    public interface View extends BaseView {
        //获取新闻需要展示的数据
        void showTitle(String title);

        void showImage(String picUrl);
    }

    public interface Presenter extends BasePresenter<View> {
        void refreshData();
    }

}