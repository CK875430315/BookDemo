package com.dianzhi.yxt.ui.view.immersive;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by skindhu on 15/7/14.
 */
public class ImmersiveUtil {

	private static float density = -1;
	private static int screenWidth = -1;
	private static int screenHeight = -1;

	public static int i_support_immersive = -1;
	public static int FLAG_TRANSLUCENT_STATUS = 0x04000000;

	public static void init(Context context) {
		if (density == -1) {
			DisplayMetrics metrics = context.getResources().getDisplayMetrics();
			density = metrics.density;
			if (metrics.widthPixels < metrics.heightPixels) {
				screenWidth = metrics.widthPixels;
				screenHeight = metrics.heightPixels;
			} else {
				// 部分机器使用的是context的宽高反转
				screenHeight = metrics.widthPixels;
				screenWidth = metrics.heightPixels;
			}
		}
	}

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = resources.getDimensionPixelSize(resourceId);
		}

		return result;
	}

	public static float getDensity() {
		return density;
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static int dpToPx(float dp) {
		return Math.round(dp * density);
	}

	/**
	 * 在xml里设置android:alpha对api11以前的系统不起作用，所以在代码里获取并设置透明度
	 */
	public static void setAlpha(View view, float alphaValue) {
		if (view == null) {
			return;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			view.setAlpha(alphaValue);
		} else {
			AlphaAnimation alpha = new AlphaAnimation(alphaValue, alphaValue);
			alpha.setDuration(0);
			alpha.setFillAfter(true);
			view.startAnimation(alpha);
		}
	}

	public static int  isSupporImmersive(){
		if(i_support_immersive != -1){
			return i_support_immersive;
		}
		if(Build.VERSION.SDK_INT < 19){
			i_support_immersive = 0;
			return i_support_immersive;
		}
		String manufacturer = Build.MANUFACTURER.toUpperCase();
		if(manufacturer.endsWith("BBK") || manufacturer.endsWith("VIVO")){
			i_support_immersive = 0;
		} else{
			i_support_immersive = 1;
		}
		return i_support_immersive;
	}

	public void setFitsSystemWindows(View view, boolean fitsystem){
		view.setFitsSystemWindows(fitsystem);
		view.setPadding(0, getStatusBarHeight(view.getContext()), 0, 0);
	}
}
