package com.dianzhi.yxt.http;

import com.dianzhi.yxt.entity.NewsEntity;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by CK on 2017/2/17.
 */

public interface Api {
    //http://apis.guokr.com/handpick/article.json?retrieve_type=by_since&category=all&limit=3&ad=1
    @GET("article.json")
    Observable<NewsEntity> getNewsData(@QueryMap Map<String, String> map);
}
