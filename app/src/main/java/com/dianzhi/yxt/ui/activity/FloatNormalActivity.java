package com.dianzhi.yxt.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.dianzhi.yxt.R;
import com.dianzhi.yxt.ui.view.immersive.ImmersiveUtil;
import com.dianzhi.yxt.ui.view.immersive.SystemBarCompact;
import com.dianzhi.yxt.utils.ConfigUtils;
import com.dianzhi.yxt.utils.Permission;
import com.dianzhi.yxt.utils.SpUtils;

import rx.Subscription;

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
    public Permission.Builder builder;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        /*this.requestWindowFeature(getIntent());*/
        setImmersiveStatus();
        uid = SpUtils.getSP(this, "uid");
        imei = SpUtils.getSP(this, "imei");
        ver = SpUtils.getSP(this, "ver");
        builder = new Permission.Builder();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (uid.equals("0"))
            uid = SpUtils.getSP(this, "uid");
        if (systemBarCompact != null) {
            systemBarCompact.init();
        }
    }

    public void setImmersiveStatus() {
        if (needStatusTrans && ImmersiveUtil.isSupporImmersive() == 1) {
            getWindow().addFlags(0x04000000); // SDK19:WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (needImmersive) {
                int color = getResources().getColor(R.color.color_main_tab);
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


}
