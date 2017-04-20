package com.dianzhi.yxt.mvp.presenter;

import com.dianzhi.yxt.entity.NewsEntity;
import com.dianzhi.yxt.http.CallBackListener;
import com.dianzhi.yxt.http.Network;
import com.dianzhi.yxt.http.RequestObservable;
import com.dianzhi.yxt.mvp.contract.NewsContract;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.dianzhi.yxt.mvp.contract.NewsContract.mCompositeSubscription;

/**
* Created by CK on 2017/02/17
*/
public class NewsPresenter implements NewsContract.Presenter{
    private NewsContract.View view;
    //@Inject表示构造方法会提供NewsPresenter对象，但是需要一个view，NewsModule容器里已经提供，这时p层和v层就关联在一起
    @Inject
    public NewsPresenter(NewsContract.View view) {
        this.view=view;
        mCompositeSubscription = new CompositeSubscription();
    }
    @Override
    public void unsubscribe() {
        mCompositeSubscription.clear();
    }
    //设缓存有效期为两个星期
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 14;
    //查询缓存的Cache-Control设置
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";
    @Override
    public void refreshData() {
        //    //http://apis.guokr.com/handpick/article.json?retrieve_type=by_since&category=all&limit=3&ad=1
        Map<String,String> map=new HashMap<>();
        map.put("retrieve_type","by_since");
        map.put("category","all");
        map.put("limit","3");
        map.put("ad","1");
        Subscription subscription = Network.send(RequestObservable.getApi().getNewsData(map), new CallBackListener<NewsEntity>() {
            @Override
            public void onSuccess(NewsEntity newsEntity) {
                view.showImage(newsEntity.result.get(0).getImages().get(0));
                view.showTitle(newsEntity.result.get(0).getTitle());
            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void onFinish() {

            }
        });

        mCompositeSubscription.add(subscription);
    }
}