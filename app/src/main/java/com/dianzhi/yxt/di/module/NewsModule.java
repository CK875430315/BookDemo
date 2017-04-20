package com.dianzhi.yxt.di.module;

import com.dianzhi.yxt.mvp.contract.NewsContract;
import com.dianzhi.yxt.mvp.presenter.NewsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by CK on 2017/2/17.
 */
@Module
public class NewsModule {

    private NewsContract.View view;

    public NewsModule(NewsContract.View view) {
        this.view = view;
    }

    @Provides
    NewsContract.View provide() {
        return this.view;
    }
//前面提到MainActivity需要一个NewsPresenter的对象，dagger2会先在NewsModule里找有没有一个可以提供NewsPresenter对象的方法，
// 结果是NewsContract.Presenter并不是，这个是他的契约父类(注意参数和返回值不能一样不然会进入死循环，自己找自己引起)，方法返回
//了参数传过来的NewsPresenter对象，直接去找NewsPresenter的构造方法
    @Provides
    NewsContract.Presenter provideNewsPresenter(NewsPresenter newsPresenter) {
        return newsPresenter;
    }
}
