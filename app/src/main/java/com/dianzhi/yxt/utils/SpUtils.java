package com.dianzhi.yxt.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/7/21.
 */
public class SpUtils {

    /**
     * 存入uid信息
     *
     * @param ctx
     * @param ctx
     * @param
     */
    public static void putSP(Context ctx, String key,String value) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("Info", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    /**
     * 获取uid
     *
     * @param ctx
     * @return
     */
    public static String getSP(Context ctx,String key) {
        return ctx.getSharedPreferences("Info", Context.MODE_PRIVATE).getString(key, "0");
    }
}
