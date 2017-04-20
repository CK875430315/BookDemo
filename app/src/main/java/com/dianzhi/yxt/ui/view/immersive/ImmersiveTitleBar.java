package com.dianzhi.yxt.ui.view.immersive;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.dianzhi.yxt.R;


/**
 * Created by skindhu on 15/7/14.
 */
public class ImmersiveTitleBar extends View {
	private int mStatusBarHeight;
	public int mViewHeight;
	// 是否要开启透明状态栏兼容绘制，默认开启
	public static boolean TRANSLUCENT_STATUS_BAR = true;
	public static boolean mNeedDrawStatus = true;
	public ImmersiveTitleBar(Context context) {
		super(context);
		initUI(context);
	}
	public ImmersiveTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initUI(context);
	}

	public ImmersiveTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initUI(context);
	}
	public void initUI(Context mContext) {
		mNeedDrawStatus =  TRANSLUCENT_STATUS_BAR && (ImmersiveUtil.isSupporImmersive() == 1);
		mStatusBarHeight = ImmersiveUtil.getStatusBarHeight(mContext);
		if (mNeedDrawStatus) {
			setCustomHeight(mStatusBarHeight);
		}else{
			setCustomHeight(0);
		}
		setBackgroundColor(getResources().getColor(R.color.color_main_tab));
	}

	public void resetBkColor(int color){
		setBackgroundColor(color);
	}
	public void setCustomHeight(int height){
		mViewHeight = height;
		requestLayout();
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mViewHeight, MeasureSpec.EXACTLY));
		setMeasuredDimension(getMeasuredWidth(), mViewHeight);

	}
}
