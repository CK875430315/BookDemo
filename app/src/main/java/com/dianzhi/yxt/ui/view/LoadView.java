package com.dianzhi.yxt.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dianzhi.yxt.R;
import com.dianzhi.yxt.utils.DisplayUtil;

public class LoadView extends View {
    private Context context;
    private Paint mPaint = new Paint();

    private int widthSpecSize;
    private int heightSpecSize;
    private int radius = DisplayUtil.dip2px(context,4);//小圆球半径
    private int HLength = DisplayUtil.dip2px(context,23);//六边形半径
    private int maxMoveH = DisplayUtil.dip2px(context,20);//向外移送最长距离
    private double moveH = 0;//向外移动增量
    private int theCircle = -1;//那个圆球在作用

    private ValueAnimator animator;

    public LoadView(Context context) {
        super(context);
        this.context=context;
    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;

    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(getResources().getColor(R.color.color_main_tab));
        mPaint.setAntiAlias(true);
        for (int i = 1; i <= 6; i++) {
            if (theCircle % 6 + 1 == i)
                canvas.drawCircle(widthSpecSize / 2 + (float) (Math.cos(Math.PI * i / 3) * (HLength + moveH)), heightSpecSize / 2 - (float) (Math.sin(Math.PI * i / 3) * (HLength + moveH)), radius, mPaint);
            else
                canvas.drawCircle(widthSpecSize / 2 + (float) Math.cos(Math.PI * i / 3) * HLength, heightSpecSize / 2 - (float) Math.sin(Math.PI * i / 3) * HLength, radius, mPaint);
        }
    }

    public void start() {
        if (animator != null)
            animator.cancel();
        animator = ValueAnimator.ofFloat(0f, (float) Math.PI * 6);
        animator.setDuration(3000);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveH = Math.abs(Math.sin((Float) animation.getAnimatedValue())) * maxMoveH;
                theCircle = (int) ((Float) animation.getAnimatedValue() / Math.PI) + 1;
                invalidate();
            }
        });
        animator.start();
    }

    public void stopAnima() {
        if (animator != null) {
            animator.cancel();
            setVisibility(INVISIBLE);
        }
    }
}
