package com.dianzhi.yxt.ui.view.immersive;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;


/**
 * Created by skindhu on 15/7/13.
 */
public class SystemBarTintManager implements Handler.Callback{

	/**
	 * The default system bar tint color value
	 */
	public static final int DEFAULT_TINT_COLOR = 0x0000FF;

	private final SystemBarConfig mConfig; // 配置
	private boolean mStatusBarAvailable; // statusbar是否可用
	public boolean mStatusBarTintEnabled; // 自定义statusbar是否显示
	public View mStatusBarTintView;
	public static final int MSG_VISIBLE = 0;
	public static final int MSG_INVISIBLE = 1;
	public Handler mHandler;

	/**
	 * Constructor. Call this in the host activity onCreate method after its
	 * content view has been set. You should always create new instances when
	 * the host activity is recreated.
	 *
	 */
	@TargetApi(19)
	public SystemBarTintManager(Window window, boolean drawStatusBar) {
		ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();

		if (ImmersiveUtil.isSupporImmersive() == 1) {
			mStatusBarAvailable = drawStatusBar;
		}
		mConfig = new SystemBarConfig(window, mStatusBarAvailable);
		if (mStatusBarAvailable) {
			setupStatusBarView(window, decorViewGroup);
		}
		mHandler = new Handler(Looper.getMainLooper(), this);
	}

	private void setupStatusBarView(Window window, ViewGroup decorViewGroup) {
		mStatusBarTintView = new View(window.getContext());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mConfig.getStatusBarHeight());
		params.gravity = Gravity.TOP;
		mStatusBarTintView.setLayoutParams(params);
		mStatusBarTintView.setVisibility(View.GONE);
		decorViewGroup.addView(mStatusBarTintView);
	}

	public void setStatusBarVisible(boolean visible,int delayTime){
		mStatusBarTintEnabled = visible;
		if (visible) {
			mHandler.sendEmptyMessageDelayed(MSG_VISIBLE, delayTime);

		}else{
			mHandler.sendEmptyMessageDelayed(MSG_INVISIBLE, delayTime);
		}
	}

	/**
	 * Enable tinting of the system status bar.
	 *
	 * If the platform is running Jelly Bean or earlier, or translucent system
	 * UI modes have not been enabled in either the theme or via window flags,
	 * then this method does nothing.
	 *
	 * @param enabled True to enable tinting, false to disable it (default).
	 */
	public void setStatusBarTintEnabled(boolean enabled) {
		mStatusBarTintEnabled = enabled;
		if (mStatusBarAvailable) {
			mStatusBarTintView.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
		}
	}

	/**
	 * Apply the specified color tint to all system UI bars.
	 *
	 * @param color The color of the background tint.
	 */
	public void setTintColor(int color) {
		setStatusBarTintColor(color);
	}

	/**
	 * Apply the specified drawable or color resource to all system UI bars.
	 *
	 * @param res The identifier of the resource.
	 */
	public void setTintResource(int res) {
		setStatusBarTintResource(res);
	}

	/**
	 * Apply the specified drawable to all system UI bars.
	 *
	 * @param drawable The drawable to use as the background, or null to remove it.
	 */
	public void setTintDrawable(Drawable drawable) {
		setStatusBarTintDrawable(drawable);
	}

	/**
	 * Apply the specified alpha to all system UI bars.
	 *
	 * @param alpha The alpha to use
	 */
	public void setTintAlpha(float alpha) {
		setStatusBarAlpha(alpha);
	}

	/**
	 * Apply the specified color tint to the system status bar.
	 *
	 * @param color The color of the background tint.
	 */
	public void setStatusBarTintColor(int color) {
		if (mStatusBarAvailable) {
			mStatusBarTintView.setBackgroundColor(color);
		}
	}

	/**
	 * Apply the specified drawable or color resource to the system status bar.
	 *
	 * @param res The identifier of the resource.
	 */
	public void setStatusBarTintResource(int res) {
		if (mStatusBarAvailable) {
			mStatusBarTintView.setBackgroundResource(res);
		}
	}

	/**
	 * Apply the specified drawable to the system status bar.
	 *
	 * @param drawable The drawable to use as the background, or null to remove it.
	 */
	@SuppressWarnings("deprecation")
	public void setStatusBarTintDrawable(Drawable drawable) {
		if (mStatusBarAvailable) {
			mStatusBarTintView.setBackgroundDrawable(drawable);
		}
	}

	/**
	 * Apply the specified alpha to the system status bar.
	 *
	 * @param alpha The alpha to use
	 */
	@TargetApi(11)
	public void setStatusBarAlpha(float alpha) {
		if (mStatusBarAvailable && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mStatusBarTintView.setAlpha(alpha);
		}else{
			AlphaAnimation alphaAni = new AlphaAnimation(alpha, alpha);
			alphaAni.setDuration(0);
			alphaAni.setFillAfter(true);
			mStatusBarTintView.startAnimation(alphaAni);
		}
	}
	/**
	 * Get the system bar configuration.
	 *
	 * @return The system bar configuration for the current device configuration.
	 */
	public SystemBarConfig getConfig() {
		return mConfig;
	}

	/**
	 * Is tinting enabled for the system status bar?
	 *
	 * @return True if enabled, False otherwise.
	 */
	public boolean isStatusBarTintEnabled() {
		return mStatusBarTintEnabled;
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what){
			case MSG_VISIBLE:
				mStatusBarTintView.setVisibility(View.VISIBLE);
				break;
			case MSG_INVISIBLE:
				mStatusBarTintView.setVisibility(View.INVISIBLE);
				break;
		}
		return false;
	}

	/**
	 * Class which describes system bar sizing and other characteristics for the current
	 * device configuration.
	 */
	public static class SystemBarConfig {

		private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";

		private final boolean mTranslucentStatusBar;
		private final int mStatusBarHeight;
		private final boolean mInPortrait;
		private final float mSmallestWidthDp;

		private SystemBarConfig(Window window, boolean translucentStatusBar) {
			Resources res = window.getContext().getResources();
			mInPortrait = (res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
			mSmallestWidthDp = getSmallestWidthDp(window);
			mStatusBarHeight = getInternalDimensionSize(res, STATUS_BAR_HEIGHT_RES_NAME);
			mTranslucentStatusBar = translucentStatusBar;
		}

		private int getInternalDimensionSize(Resources res, String key) {
			int result = 0;
			int resourceId = res.getIdentifier(key, "dimen", "android");
			if (resourceId > 0) {
				result = res.getDimensionPixelSize(resourceId);
			}
			return result;
		}

		@SuppressLint("NewApi")
		private float getSmallestWidthDp(Window window) {
			DisplayMetrics metrics = new DisplayMetrics();
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				window.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
			} else {
				// TODO this is not correct, but we don't really care pre-kitkat
				window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			}
			float widthDp = metrics.widthPixels / metrics.density;
			float heightDp = metrics.heightPixels / metrics.density;
			return Math.min(widthDp, heightDp);
		}

		/**
		 * Get the height of the system status bar.
		 *
		 * @return The height of the status bar (in pixels).
		 */
		public int getStatusBarHeight() {
			return mStatusBarHeight;
		}
	}
}
