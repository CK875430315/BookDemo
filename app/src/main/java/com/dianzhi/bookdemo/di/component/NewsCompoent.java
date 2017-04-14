package com.dianzhi.bookdemo.di.component;

import com.dianzhi.bookdemo.di.module.NewsModule;
import com.dianzhi.bookdemo.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * Created by CK on 2017/2/17.
 */
@Component(modules = NewsModule.class)
public interface NewsCompoent {
    //定义注入的目标"地点"
    void inject(HomeFragment activity);

}
