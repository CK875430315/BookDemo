package com.dianzhi.bookdemo.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by CK on 2016/10/17.
 */
public class RxBus {
    private static  RxBus RxInstance;
    private final Subject<Object, Object> bus;
    public RxBus(){
        bus=new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault(){
        if (RxInstance == null) {
            synchronized (RxBus.class) {
                if (RxInstance == null) {
                    RxInstance=new RxBus();
                }
            }
        }
    return RxInstance;
    }
    // 发送一个新的事件
    public void post (Object o) {
        bus.onNext(o);
    }
    /**
     * 返回特定类型的被观察者
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
