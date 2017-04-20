package com.dianzhi.yxt.ui.view.slider;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.dianzhi.yxt.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by Administrator on 2016/7/21.
 */
public class SlidingCompatActivity extends AppCompatActivity implements SlidingLayout.SlidingListener {
    private View mPreview;

    private float mInitOffset;
    private boolean hideTitle = false;
    private int titleResId = -1;

    private String mBitmapId;

    @SuppressLint("NewApi")
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.slide_layout);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        LayoutInflater inflater = LayoutInflater.from(this);
        mInitOffset = -(1.f / 3) * metrics.widthPixels;

        mPreview = findViewById(R.id.iv_preview);
        FrameLayout contentView = (FrameLayout) findViewById(R.id.content_view);

        if (!hideTitle) {
            int resId = -1 == titleResId ? R.layout.title_layout : titleResId;
            inflater.inflate(resId, contentView);
        }

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(MATCH_PARENT,
                MATCH_PARENT, Gravity.BOTTOM);
        final int marginTop = hideTitle ? 0 : (int) (metrics.density * 48.f + .5f);
        lp.setMargins(0, marginTop, 0, 0);
        contentView.addView(inflater.inflate(layoutResID, null), lp);

        final SlidingLayout slideLayout = (SlidingLayout) findViewById(R.id.slide_layout);
        slideLayout.setShadowResource(R.drawable.sliding_back_shadow);
        slideLayout.setEdgeSize((int) (metrics.density * 20));
        slideLayout.setSlidingListener(this);
//            mBitmapId = getIntent().getExtras().getString("bitmap_id");
//            Bitmap bitmap = IntentUtils.getInstance().getBitmap(mBitmapId);
//            if (null != bitmap) {
//                if (Build.VERSION.SDK_INT >= 16) {
//                    mPreview.setBackground(new BitmapDrawable(bitmap));
//                } else {
//                    mPreview.setBackgroundDrawable(new BitmapDrawable(bitmap));
//                }
//
//                IntentUtils.getInstance().setIsDisplayed(mBitmapId, true);
//            }
            IntentFilter filter = new IntentFilter("finish");
            filter.addAction("finish");
            registerReceiver(receiver, filter);

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("finish")) {

            SlidingCompatActivity.this.finish();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IntentUtils.getInstance().setIsDisplayed(mBitmapId, false);
        if (receiver != null)
            unregisterReceiver(receiver);
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        if (slideOffset <= 0) {
        } else if (slideOffset < 1) {
            mPreview.setTranslationX(mInitOffset * (1 - slideOffset));
        } else {
            mPreview.setTranslationX(0);
            finish();
            overridePendingTransition(0, 0);
        }
    }

    protected void setContentView(int layoutResID, int titleResId) {
        this.titleResId = titleResId;
        setContentView(layoutResID);
    }

    protected void setContentView(View view, boolean hideTitle) {
        this.hideTitle = hideTitle;
        setContentView(view);
    }
    protected void setContentView(int layoutResID, boolean hideTitle) {
        this.hideTitle = hideTitle;
        setContentView(layoutResID);
    }
}
