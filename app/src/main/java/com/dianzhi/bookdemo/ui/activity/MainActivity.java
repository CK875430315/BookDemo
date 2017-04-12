package com.dianzhi.bookdemo.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianzhi.bookdemo.R;
import com.dianzhi.bookdemo.di.component.DaggerNewsCompoent;
import com.dianzhi.bookdemo.di.module.NewsModule;
import com.dianzhi.bookdemo.mvp.contract.NewsContract;
import com.dianzhi.bookdemo.mvp.presenter.NewsPresenter;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FloatNormalActivity implements NewsContract.View{
    @Inject
    NewsPresenter newsPresenter;//dagger2注解获取newspresenter的对象，以至于怎么获取，看与当前MainActivity想关联的NewsModule
    @BindView(R.id.title)
    TextView tv;
    @BindView(R.id.iv)
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        needImmersive=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setInjectotor();
    }

    @OnClick(R.id.btn)
    public void get(View view) {
        newsPresenter.refreshData();
    }

    @OnClick(R.id.btn_location)
    public void btn_location(View view) {
        Intent intent=new Intent(this,ListViewSection.class);
        startActivity(intent);
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
        Picasso.with(this)
                .load(picUrl)
                .config(Bitmap.Config.RGB_565)
                .into(iv);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newsPresenter.unsubscribe();
    }
}
