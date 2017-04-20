package com.dianzhi.yxt.ui.activity;

import android.os.Bundle;

import com.dianzhi.yxt.R;
import com.dianzhi.yxt.base.BaseActivity;

public class SearchActivity extends BaseActivity {

    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        needImmersive=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity,true);
        overridePendingTransition(R.anim.open_enter_up,R.anim.open_exit_down);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.open_enter_up,R.anim.open_exit_down);
    }
}
