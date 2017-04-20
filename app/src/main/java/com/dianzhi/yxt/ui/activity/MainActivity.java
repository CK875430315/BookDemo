package com.dianzhi.yxt.ui.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dianzhi.yxt.R;
import com.dianzhi.yxt.ui.fragment.Findragment;
import com.dianzhi.yxt.ui.fragment.HomeFragment;
import com.dianzhi.yxt.ui.fragment.SelfFragment;
import com.dianzhi.yxt.ui.fragment.TestPlayFragment;
import com.dianzhi.yxt.utils.ToastUtils;
import com.dianzhi.yxt.utils.UiUtils;
import com.tencent.stat.StatService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends FloatNormalActivity {


    @BindView(R.id.ll_home)
    LinearLayout ll_home;
    @BindView(R.id.tv_home)
    TextView tv_home;
    @BindView(R.id.iv_home)
    ImageView iv_home;

    @BindView(R.id.ll_test_play)
    LinearLayout ll_test_play;
    @BindView(R.id.tv_test_play)
    TextView tv_test_play;
    @BindView(R.id.iv_test_play)
    ImageView iv_test_play;

    @BindView(R.id.ll_find)
    LinearLayout ll_find;
    @BindView(R.id.tv_find)
    TextView tv_find;
    @BindView(R.id.iv_find)
    ImageView iv_find;

    @BindView(R.id.ll_self)
    LinearLayout ll_self;
    @BindView(R.id.tv_self)
    TextView tv_self;
    @BindView(R.id.iv_self)
    ImageView iv_self;

    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    private List<Fragment> fragments = new ArrayList<>();
    private List<TextView> tvs = new ArrayList<>();
    private int currentIndex=0;
    private int preIndex=0;
    public long exitTime=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        needImmersive = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatService.trackCustomEvent(this, "onCreate", "");
        tvs.add(tv_home);
        tvs.add(tv_test_play);
        tvs.add(tv_find);
        tvs.add(tv_self);
        fragments.add(new HomeFragment());
        fragments.add(new TestPlayFragment());
        fragments.add(new Findragment());
        fragments.add(new SelfFragment());
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragments.get(0), "0")
                .commit();
        tv_home.setTextColor(getResources().getColor(R.color.color_main_tab));
    }

    @OnClick(R.id.ll_home)
    public void ll_home(View v) {
        if (currentIndex == 0)
        {
            bottomHide();
        }else{
            preIndex=currentIndex;
            currentIndex=0;
            changeBottom(preIndex,currentIndex);
          }

    }

    @OnClick(R.id.ll_test_play)
    public void ll_test_play(View v) {
        if (currentIndex == 1)
            return;
        preIndex=currentIndex;
        currentIndex=1;
        changeBottom(preIndex,currentIndex);
    }
    @OnClick(R.id.ll_find)
    public void ll_find(View v) {
        if (currentIndex == 2)
            return;
        preIndex=currentIndex;
        currentIndex=2;
        changeBottom(preIndex,currentIndex);
    }
    @OnClick(R.id.ll_self)
    public void ll_self(View v) {
        if (currentIndex == 3)
            return;
        preIndex=currentIndex;
        currentIndex=3;
        changeBottom(preIndex,currentIndex);
    }
    private void changeBottom(int preIndex, int currentIndex) {
       tvs.get(preIndex).setTextColor(getResources().getColor(R.color.color_bfbfbf));
       tvs.get(currentIndex).setTextColor(getResources().getColor(R.color.color_main_tab));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragmentPP = fragments.get(preIndex);
        Fragment fragment=null;
        if (currentIndex == 0) {
            Fragment fragmentA = fragments.get(currentIndex);
            if (fragmentA.isAdded()) {
                transaction.show(fragmentA);
            } else {
                transaction.add(R.id.fl_content,fragmentA,String.valueOf(currentIndex)).show(fragmentA);
            }
            transaction.hide(fragmentPP);
        } else{
            fragments.remove(currentIndex);
            if (currentIndex == 1) {
                fragment=new TestPlayFragment();
            } else if (currentIndex == 2) {
                fragment=new Findragment();
            } else if (currentIndex == 3) {
                fragment=new SelfFragment();
            }
            fragments.add(currentIndex,fragment);
            transaction.hide(fragmentPP);
            transaction.add(R.id.fl_content,fragment,String.valueOf(currentIndex)).show(fragment);
        }

        transaction.commit();
    }

    public void bottomHide() {
        final RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) ll_bottom.getLayoutParams();
        ValueAnimator valueAnimator=ValueAnimator.ofInt(60,UiUtils.dip2px(this,0));
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer height= (Integer) animation.getAnimatedValue();
                layoutParams.height=height;
                ll_bottom.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
    }

    public void bottomShow() {
        final RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) ll_bottom.getLayoutParams();
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,UiUtils.dip2px(this,60));
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer height= (Integer) animation.getAnimatedValue();
                layoutParams.height=height;
                ll_bottom.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == MotionEvent.ACTION_DOWN) {
          {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    ToastUtils.showToast(this, getResources().getString(R.string.exit_app));
                    exitTime = System.currentTimeMillis();
                } else {
                    System.exit(0);
                }
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
