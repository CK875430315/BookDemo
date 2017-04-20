package com.dianzhi.yxt.ui.view.immersive;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.view.Window;


/**
 * Created by skindhu on 15/7/14.
 */
public class SystemBarCompact {

    public boolean mDrawStatus = true;
    /**
     * 状态栏背景.可以在onCreate中修改
     */
    public int mStatusBarColor = 0x000000;
    public Drawable mStatusBarDarwable;

    private Window mWindow;

    private SystemBarTintManager mTintManager;

    private Drawable mPendingStatusBarDrawable;
    private int mPendingStatusBarColor;

    public boolean isStatusBarVisible = true;

    /**
     * 在onCreate中调用
     *
     * @param activity
     * @param statusBarColor
     */
    public SystemBarCompact(Activity activity, boolean drawStatusBar,
                            int statusBarColor) {
        this.mWindow = activity.getWindow();
        this.mDrawStatus = drawStatusBar;
        this.mPendingStatusBarColor = statusBarColor;
    }

    public SystemBarCompact(Dialog dialog, boolean drawStatusBar,
                            int statusBarColor) {
        this.mWindow = dialog.getWindow();
        this.mDrawStatus = drawStatusBar;
        this.mPendingStatusBarColor = statusBarColor;
    }

    /*
      * 自定义标题栏的颜色~
      */
    public void setStatusColor(int Color) {
        this.mPendingStatusBarColor = Color;
    }

    /**
     * 必须在setContentView后调用
     */
    @TargetApi(19)
    public void init() {
        // 4.4以后, 5.0以前才需要用tintManager兼容...
        if (ImmersiveUtil.isSupporImmersive() == 1) {
            ensureTintManager();
            mTintManager.setStatusBarTintEnabled(mDrawStatus);
        }
        setStatusBarColor(mPendingStatusBarColor);
        isStatusBarVisible = true;
    }

    @TargetApi(19)
    public void setStatusBarColor(int color) {
        if (mDrawStatus && mStatusBarColor != color) {
            mStatusBarColor = color;
            if (mTintManager != null && ImmersiveUtil.isSupporImmersive() == 1) {
                mTintManager.setStatusBarTintColor(color);
            }
        }
    }

    private void ensureTintManager() {
        if (mTintManager == null) {
            mTintManager = new SystemBarTintManager(mWindow, mDrawStatus);
        }
    }

    public void setgetStatusBarVisible(boolean visible, int delayTime) {
        isStatusBarVisible = visible;
        if (mTintManager != null) {
            mTintManager.setStatusBarVisible(visible, delayTime);
        }
    }
}
