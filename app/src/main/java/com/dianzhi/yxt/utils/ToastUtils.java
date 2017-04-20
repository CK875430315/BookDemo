package com.dianzhi.yxt.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 解决Toast重复弹出
 */
public class ToastUtils {
    /**
     * @param context
     * @param s
     */
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }


    public static void showToast(Context context, int resId) {
        showToast(context, context.getResources().getString(resId));
    }
}
