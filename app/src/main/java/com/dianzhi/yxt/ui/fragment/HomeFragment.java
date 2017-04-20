package com.dianzhi.yxt.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dianzhi.yxt.R;
import com.dianzhi.yxt.adapter.HomeAdapter;
import com.dianzhi.yxt.base.BaseFragment;
import com.dianzhi.yxt.di.component.DaggerNewsCompoent;
import com.dianzhi.yxt.di.module.NewsModule;
import com.dianzhi.yxt.mvp.contract.NewsContract;
import com.dianzhi.yxt.mvp.presenter.NewsPresenter;
import com.dianzhi.yxt.ui.activity.MainActivity;
import com.dianzhi.yxt.ui.activity.SearchActivity;
import com.dianzhi.yxt.ui.view.HorizontalListView;
import com.dianzhi.yxt.ui.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.dianzhi.yxt.ui.view.recyclerview.LRecyclerView;
import com.dianzhi.yxt.ui.view.recyclerview.interfaces.OnItemClickLitener;
import com.dianzhi.yxt.ui.view.recyclerview.util.RecyclerViewUtils;
import com.dianzhi.yxt.ui.view.slider.IntentUtils;
import com.dianzhi.yxt.utils.ToastUtils;
import com.dianzhi.yxt.utils.UiUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by CK on 2017/4/13.
 */

public class HomeFragment extends BaseFragment implements NewsContract.View, OnItemClickLitener, LRecyclerView.LScrollListener {
    @Inject
    NewsPresenter newsPresenter;//dagger2注解获取newspresenter的对象，以至于怎么获取，看与当前MainActivity想关联的NewsModule
    @BindView(R.id.head_r_iv)
    ImageView head_r_iv;
    @BindView(R.id.mlrv)
    LRecyclerView mlrv;
    @BindView(R.id.rl_top)
    RelativeLayout rl_top;

    RelativeLayout rl_header;
    private HorizontalListView listView;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private HomeAdapter adapter;
    private View footView,headerView;
    private  View header;
    private ArrayList<String> datas=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setInjectotor();
        initView();
    }

    private void initView() {
        head_r_iv.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = head_r_iv.getLayoutParams();
        layoutParams.height=UiUtils.dip2px(mContext,20);
        layoutParams.width=UiUtils.dip2px(mContext,20);
        head_r_iv.setLayoutParams(layoutParams);
        head_r_iv.setImageResource(R.mipmap.home_search);
        for (int i = 1; i < 10; i++) {
            datas.add(i+"测试从踩踩踩从");
        }
        header=View.inflate(mContext, R.layout.home_list_header, null);
        rl_header = (RelativeLayout) header.findViewById(R.id.ll_header);

        adapter= new HomeAdapter(datas,mContext);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mContext, adapter);
        mHeaderAndFooterRecyclerViewAdapter.setOnItemClickLitener(this);
        footView = LayoutInflater.from(mContext).inflate(R.layout.gridview_footer, null);
        headerView=LayoutInflater.from(mContext).inflate(R.layout.home_list_header,null);
        footView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, 200));
        mlrv.setLayoutManager(new LinearLayoutManager(mContext));
        mlrv.setLScrollListener(this);
        RecyclerViewUtils.setFooterView(mlrv, footView);
        mlrv.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setHeaderView(mlrv,header);
        ArrayList<String> dataTop=new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            dataTop.add("测试"+i);
        }

        listView=(HorizontalListView) header.findViewById(R.id.header_top_tab);
        listView.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,android.R.id.text1,dataTop));
    }

    @OnClick(R.id.head_r_iv)
    public void head_r_iv(View view) {
      IntentUtils.getInstance().startActivity(mContext,new Intent(mContext, SearchActivity.class));
    }

    private void setInjectotor() {
        //固定写法表示要注入的是当前的MainActivity,与NewsModule相关联
        DaggerNewsCompoent.builder().newsModule(new NewsModule(this)).build().inject(this);
    }

    @Override
    public void showTitle(String title) {

    }

    @Override
    public void showImage(String picUrl) {

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

    @Override
    public void onItemClick(View view, int position) {
        ToastUtils.showToast(mContext,datas.get(position));

//        Intent intent = new MQIntentBuilder(mContext).build();
//        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onRefresh() {

    }
   private boolean isUpAnmi,isDownAnmi;
    @Override
    public void onScrollUp() {
//hide
        if (!isUpAnmi) {
            isUpAnmi=true;
            isDownAnmi=false;
            MainActivity mainActivity= (MainActivity) getActivity();
            mainActivity.bottomHide();
        }

    }

    @Override
    public void onScrollDown() {
//show
        if (!isDownAnmi) {
            isDownAnmi=true;
            isUpAnmi=false;
            MainActivity mainActivity= (MainActivity) getActivity();
            mainActivity.bottomShow();
        }

    }

    @Override
    public void onBottom() {
        datas.clear();
        for (int i = 1; i <20 ; i++) {
            datas.add(i+"测试从踩踩踩从");
        }
        adapter.notifyDataSetChanged();
        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onScrolled(int distanceX, int distanceY) {
        Log.e(TAG, "onScrolled: "+distanceY);
        if (distanceY >= UiUtils.dip2px(mContext, 252)) {
            if (rl_top.getChildCount() == 0) {
                rl_header.removeView(listView);
                rl_top.addView(listView);
            }
        } else {
            if (rl_top.getChildCount() != 0) {

                rl_top.removeView(listView);
                rl_header.addView(listView);
            }
        }
    }
}
