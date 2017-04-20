package com.dianzhi.yxt.ui.view;


import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.dianzhi.yxt.R;
import com.dianzhi.yxt.utils.DisplayUtil;


public class MyRelativeLayout extends RelativeLayout {
    private ViewDragHelper mDragHelper;
    private View float_button;
    private boolean first = true;
    private float x, y;
   private Context context;
    public MyRelativeLayout(Context context) {
        this(context, null);
        this.context=context;
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context=context;
    }

    public MyRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, mCallback);
    }

    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //确定哪个控件能被拖动
            return float_button == child;
        }


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //横向
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //竖向
            return top;
        }

        //getViewHorizontalDragRange和getViewVerticalDragRange这两个方法主要是解决控件设置onClick事件，不能拖动的问题
        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {

            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            x = float_button.getX();
            y = float_button.getY();
            if (x < 0) {
                x = 0;
            }
            if (y < DisplayUtil.dip2px(context,200)) {
                y = DisplayUtil.dip2px(context,200);
            }
            if (x > getMeasuredWidth() - releasedChild.getMeasuredWidth()) {
                x = getMeasuredWidth() - releasedChild.getMeasuredWidth();
            }
            if (y > getMeasuredHeight() - releasedChild.getMeasuredHeight()-DisplayUtil.dip2px(context,90)) {
                y = getMeasuredHeight() - releasedChild.getMeasuredHeight()-DisplayUtil.dip2px(context,90);
            }
            float_button.layout((int) x, (int) y, (int) (x + float_button.getMeasuredWidth()), (int) (y + float_button.getMeasuredHeight()));
            super.onViewReleased(releasedChild, xvel, yvel);
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (first) {
            x = getResources().getDisplayMetrics().widthPixels-float_button.getMeasuredWidth()- DisplayUtil.dip2px(context,15);
//            y = getResources().getDisplayMetrics().heightPixels-float_button.getMeasuredHeight()- ConfigUtils.navigationBarHeight-DisplayUtil.dip2px(context,40);
            first = false;
        }
        float_button.layout((int) x, (int) y, (int) (x + float_button.getMeasuredWidth()), (int) (y + float_button.getMeasuredHeight()));
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        float_button = findViewById(R.id.float_button);
    }
}
