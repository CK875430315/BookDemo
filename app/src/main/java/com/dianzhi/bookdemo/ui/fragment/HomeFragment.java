package com.dianzhi.bookdemo.ui.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianzhi.bookdemo.R;
import com.dianzhi.bookdemo.base.BaseFragment;
import com.dianzhi.bookdemo.di.component.DaggerNewsCompoent;
import com.dianzhi.bookdemo.di.module.NewsModule;
import com.dianzhi.bookdemo.mvp.contract.NewsContract;
import com.dianzhi.bookdemo.mvp.presenter.NewsPresenter;
import com.dianzhi.bookdemo.utils.Permission;
import com.dianzhi.bookdemo.utils.ToastUtils;
import com.dianzhi.bookdemo.utils.UiUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by CK on 2017/4/13.
 */

public class HomeFragment extends BaseFragment implements NewsContract.View {
    @Inject
    NewsPresenter newsPresenter;//dagger2注解获取newspresenter的对象，以至于怎么获取，看与当前MainActivity想关联的NewsModule
    @BindView(R.id.title)
    TextView tv;
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setInjectotor();
    }
    @OnClick(R.id.btn)
    public void get(View view) {
//        newsPresenter.refreshData();
        UiUtils.loadDetailImage(mContext,iv,"http://ooc8y8v7r.bkt.clouddn.com/1.jpg");
    }


    @OnClick(R.id.btn_permission)
    public void btn_permission(View view) {
        permission.fragment(this).requestPermissionForFragment(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new Permission.PermissionListener() {
            @Override
            public void granted() {
                ToastUtils.showToast(mContext, "照相通过");
            }

            @Override
            public void denied() {
                ToastUtils.showToast(mContext, "照相拒绝");
            }

            @Override
            public void deniedNeverAsk() {
                ToastUtils.showToast(mContext, getString(R.string.deniedNeverAsk));
            }
        });
    }

    @OnClick(R.id.btn_permission_location)
    public void btn_permission_location(View view) {
        permission.fragment(this).requestPermissionForFragment(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, new Permission.PermissionListener() {
            @Override
            public void granted() {
                ToastUtils.showToast(mContext, "定位通过");
            }

            @Override
            public void denied() {
                ToastUtils.showToast(mContext, "定位拒绝");
            }

            @Override
            public void deniedNeverAsk() {
                ToastUtils.showToast(mContext, getString(R.string.deniedNeverAsk));
            }
        });
    }

    private void setInjectotor() {
        //固定写法表示要注入的是当前的MainActivity,与NewsModule相关联
        DaggerNewsCompoent.builder().newsModule(new NewsModule(this)).build().inject(this);
    }

    @Override
    public void showTitle(String title) {
        tv.setText(title);
    }

    @Override
    public void showImage(String picUrl) {
        UiUtils.loadDetailImage(mContext, iv, picUrl);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newsPresenter.unsubscribe();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permission.RequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
