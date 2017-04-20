package com.dianzhi.yxt.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by CK on 2017/2/20.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerApplication {
}
