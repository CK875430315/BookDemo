package com.dianzhi.yxt.http;

import com.dianzhi.yxt.base.MyApplication;
import com.dianzhi.yxt.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CK on 2017/2/17.
 */

public class RequestObservable {
    private static Api api;
    private static Retrofit retrofit=null;
    private static File cacheFile = new File(MyApplication.Instandce.getCacheDir(), "response");
    private static Cache cache = new Cache(cacheFile, 1024 * 1024 * 10); //100Mb
    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
  private static   Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtils.isNetworkConnected(MyApplication.Instandce)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                         .build();
            }

            Response response = chain.proceed(request);
            if (NetUtils.isNetworkConnected(MyApplication.Instandce)) {
                int maxAge = 60 * 60; // read from cache for 1 minute
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    };
    /**
     * 自定义okhttpclient
     */
   private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .build();
//            .addInterceptor(interceptor)
//            .cache(cache).build();
    public static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    public static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    /**
     * 获得接口的容器 注意baseUrl要以“/”结尾
     * @param
     * @return
     */
    public static Api getApi() {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .client(okHttpClient)
                            .baseUrl("http://apis.guokr.com/handpick/")
                            .addConverterFactory(gsonConverterFactory)
                            .addCallAdapterFactory(rxJavaCallAdapterFactory)
                            .build();
                }
            }
        }
        api = retrofit.create(Api.class);
        return api;
    }
}
