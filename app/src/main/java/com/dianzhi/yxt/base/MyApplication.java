package com.dianzhi.yxt.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;

import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.tencent.stat.StatConfig;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




/**
 * Created by CK on 2016/10/21.
 */
public class MyApplication extends Application {
    public static int Fragment_Index = 0;
    public static Map<String, Long> map;
    public static List<AppCompatActivity> ACTIVITY_ARRAY = new ArrayList<>();
    public static Context Instandce;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //一定要放在这里，或者http://www.jianshu.com/p/5dd2a7a4e6aa
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Instandce=this;
        x.Ext.init(this);
        String channel = AnalyticsConfig.getChannel(this);
        StatConfig.setInstallChannel(channel);
        // 友盟初始化
        MobclickAgent.startWithConfigure(
                new MobclickAgent.UMAnalyticsConfig(MyApplication.this, "58f6db1a04e2050e2300021b", channel, MobclickAgent.EScenarioType.E_UM_NORMAL));
        //美恰
        MQConfig.init(this, "f70a92c995d140bc8082b51e1812c7e9", new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {

            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }

}
