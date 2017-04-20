package com.dianzhi.yxt.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dianzhi.yxt.utils.ConfigUtils;
import com.dianzhi.yxt.utils.Core;
import com.dianzhi.yxt.utils.Permission;
import com.dianzhi.yxt.utils.SpUtils;

import butterknife.ButterKnife;

/**
 * Created by CK on 2016/10/20.
 */
public abstract class BaseFragment extends Fragment {
    public static final String TAG = "sssss";
    private boolean isVisiable;
    private boolean isViewInit;
    private boolean isDataFinish;
    protected String appid= ConfigUtils.APPID;
    protected String imei="";
    protected String pt = ConfigUtils.PT;
    protected String ver="";
    public Context mContext;
    private boolean isFirsrt=true;
    protected String uid="0";
    public  Permission.Builder permission;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        permission= new Permission.Builder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initInfo();
        return inflater.inflate(getLayoutId(), container, false);
    }
    @Override
    public void onResume() {
        super.onResume();
        uid = SpUtils.getSP(mContext,"uid");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    private void initInfo() {
         uid = SpUtils.getSP(mContext,"uid");
        if(isFirsrt){
            isFirsrt=false;
            Core core = new Core(mContext);
            imei = core.getIMEI();
            try {
                ver = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    protected abstract int getLayoutId();
}
