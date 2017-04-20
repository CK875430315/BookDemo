package com.dianzhi.yxt.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by CK on 2016/10/21.
 */
public class ObservableScrollView extends ScrollView {
    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
    private boolean isScrolledToBottom = false;
    private ScrollViewListener scrollViewListener = null;
    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0) {
            isScrolledToTop = clampedY;
            isScrolledToBottom = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
        }
        notifyScrollChangedListeners();
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }

        if (android.os.Build.VERSION.SDK_INT < 9) {  // API 9及之后走onOverScrolled方法监听
            if (getScrollY() == 0) {    // 小心踩坑1: 这里不能是getScrollY() <= 0
                isScrolledToTop = true;
                isScrolledToBottom = false;
            } else if (getScrollY() + getHeight() == getChildAt(0).getHeight()) {
                // 小心踩坑2: 这里不能是getScrollY() + getHeight() >= getChildAt(0).getHeight()
                isScrolledToBottom = true;
                isScrolledToTop = false;
            } else {
                isScrolledToTop = false;
                isScrolledToBottom = false;
            }
            notifyScrollChangedListeners();
        }
    }

    private void notifyScrollChangedListeners() {
        if (isScrolledToTop) {
            if (scrollViewListener != null) {
                scrollViewListener.onScrolledToTop();
            }
        } else if (isScrolledToBottom) {
            if (scrollViewListener != null) {
                scrollViewListener.onScrolledToBottom();
            }
        }
    }
    public interface ScrollViewListener {
        void onScrolledToBottom();
        void onScrolledToTop();
        void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);

    }


}
