package com.dianzhi.yxt.ui.view;

/**
 * Created by Administrator on 2016/8/23.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

/**
 * 流式布局的RadioGroup
 */
public class FlowRadioGroup extends RadioGroup {

    private int jiange=20;

    public FlowRadioGroup(Context context) {
        super(context);
    }

    public FlowRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private final static int VIEW_MARGIN=6;
    private final static int VIEWMARGIN_TOP=10;
    private final static int VIEW_PADDING=10;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        if(childCount==0){
            setMeasuredDimension(maxWidth, 200);
        }else{
            int x = 0;
            int y = 0;
            int row = 0;

            for (int index = 0; index < childCount; index++) {
                final View child = getChildAt(index);
                if (child.getVisibility() != View.GONE) {
                    child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                    // 此处增加onlayout中的换行判断，用于计算所需的高度
                    int width = child.getMeasuredWidth()+VIEW_MARGIN;
                    int height = child.getMeasuredHeight()+VIEWMARGIN_TOP;
                    x += width+VIEW_MARGIN;
                    y = row * height + height+VIEWMARGIN_TOP;
                    if (x > maxWidth) {
                        x = width;
                        row++;
                        y = row * height + height+VIEWMARGIN_TOP*row;
                    }
                }
            }
            // 设置容器所需的宽度和高度
            setMeasuredDimension(maxWidth, y);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        int maxWidth = r - l;
        int x = 0;
        int y = 0;
        int row = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
              /* x += width;
                y = row * height + height;
                if (x > maxWidth) {
                    x = width;
                    row++;
                    y = row * height + height;
                }*/


                x += width+VIEW_MARGIN;
                y = row * (height +VIEW_MARGIN)+ height;
                if (x > maxWidth) {
                    x = width+VIEW_MARGIN;
                    row++;
                    y =row * height + height+VIEW_MARGIN*row;
                }


               /* if(i == 0){
                    x+=width+VIEW_MARGIN;//第一个的时候不需要加
                }else{
                    x+=width+VIEW_MARGIN +jiange;//按钮之间的间隔
                }

                y=row*(height+VIEW_MARGIN)+VIEW_MARGIN+height+t;
                if(x>r){
                    x=width+VIEW_MARGIN+l;
                    row++;
                    y=row*(height+VIEW_MARGIN)+VIEW_MARGIN+height+t;
                }*/

                /*RadioGroup.LayoutParams params=new LayoutParams(400,250);
                params.gravity= Gravity.CENTER;
                params.setMargins(x - width, y - height, x, y);
                child.setLayoutParams(params);*/
                child.layout(x - width, y - height, x, y);
            }
        }
    }
}