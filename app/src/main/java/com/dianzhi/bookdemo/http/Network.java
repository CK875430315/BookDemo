package com.dianzhi.bookdemo.http;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CK on 2017/2/17.
 */

public class Network {
    /**
     * get请求
     *
     * @param observable
     * @param backListener
     */
    public static Subscription send(Observable observable, CallBackListener backListener) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserve(backListener));
    }
    /**
     * 构造observer
     *
     * @param backListener
     * @return
     */
    private static Observer getObserve(final CallBackListener backListener) {
        Observer observer = new Observer() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (backListener != null) {
                    backListener.onError(e.getMessage());
                    backListener.onFinish();
                }
            }

            @Override
            public void onNext(Object o) {
                if (backListener != null) {
                    backListener.onSuccess(o);
                    backListener.onFinish();
                }
            }
        };
        return observer;
    }
}
