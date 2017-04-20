package com.dianzhi.yxt.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.dianzhi.yxt.R;
import com.dianzhi.yxt.ui.view.slider.IntentUtils;
import com.dianzhi.yxt.utils.Core;
import com.dianzhi.yxt.utils.Permission;
import com.dianzhi.yxt.utils.SpUtils;
import com.dianzhi.yxt.utils.ToastUtils;

import butterknife.ButterKnife;

/**
 * Created by CK on 2017/4/12.
 */

public class WelcomeActivity extends FloatNormalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        needImmersive=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        permissget();

    }
    private void permissget() {
        builder.activity(this).requestPermissionFeature(new String[]{Manifest.permission.READ_PHONE_STATE}, new Permission.PermissionListener() {
            @Override
            public void granted() {
                initInfo();
            }
            @Override
            public void deniedNeverAsk() {
                ToastUtils.showToast(WelcomeActivity.this,getString(R.string.deniedNeverAsk));
            }
            @Override
            public void denied() {
                finish();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        builder.RequestPermissionsResult(requestCode,permissions,grantResults);
    }

    public void initInfo() {
        uid = SpUtils.getSP(this,"uid");
        Core core = new Core(this);
        imei = core.getIMEI();
        try {
            ver = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        SpUtils.putSP(this,"imei",imei);
        SpUtils.putSP(this,"ver",ver);
        toNext();
    }
    private void toNext() {
        if (!TextUtils.isEmpty(imei)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                    IntentUtils.getInstance().startActivity(WelcomeActivity.this,intent);
                    finish();
                }
            },2500);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

}
