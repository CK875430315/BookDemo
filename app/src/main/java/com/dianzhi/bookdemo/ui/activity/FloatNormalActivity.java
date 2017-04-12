package com.dianzhi.bookdemo.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dianzhi.bookdemo.R;
import com.dianzhi.bookdemo.ui.view.immersive.ImmersiveUtil;
import com.dianzhi.bookdemo.ui.view.immersive.SystemBarCompact;
import com.dianzhi.bookdemo.utils.ConfigUtils;
import com.dianzhi.bookdemo.utils.Core;
import com.dianzhi.bookdemo.utils.SpUtils;

import rx.Subscription;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by CK on 2017/4/12.
 */

public class FloatNormalActivity extends AppCompatActivity {

    // 沉浸式UI
    public boolean needStatusTrans = true; // 设置是否需要状态栏透明
    public boolean needImmersive = true; // 设置是否需要设置状态栏颜色
    public SystemBarCompact systemBarCompact;
    protected String imei = "";
    protected String ver = "";
    protected String pt = ConfigUtils.PT;
    protected String appid = ConfigUtils.APPID;
    protected String uid = "";
    protected Subscription subscription;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 11;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        /*this.requestWindowFeature(getIntent());*/
        setImmersiveStatus();
        permissget();
    }

    private void permissget() {
        if (Build.VERSION.SDK_INT >= M)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                initInfo();
            }
        else {
            initInfo();
        }

    }
    private void initInfo() {
        uid = SpUtils.getSP(this,"uid");
        Core core = new Core(this);
        imei = core.getIMEI();
        try {
            ver = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (systemBarCompact != null) {
            systemBarCompact.init();
        }
    }

    public void setImmersiveStatus() {
        if (needStatusTrans && ImmersiveUtil.isSupporImmersive() == 1) {
            getWindow().addFlags(0x04000000); // SDK19:WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (needImmersive) {
                int color = getResources().getColor(R.color.color_00a5eb);
                systemBarCompact = new SystemBarCompact(this, true, color);
            }
        }
    }


    protected void requestWindowFeature(Intent intent) {

    }

    protected void settitle(int rsd) {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(rsd);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            //用户同意使用write
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                initInfo();
            } else {
                //用户不同意，自行处理即可
                finish();
            }
        }

    }
}
