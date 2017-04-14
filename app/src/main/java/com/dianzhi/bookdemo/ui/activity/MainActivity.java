package com.dianzhi.bookdemo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dianzhi.bookdemo.R;
import com.dianzhi.bookdemo.ui.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


public class MainActivity extends FloatNormalActivity{

    private List<Fragment> fragments=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        needImmersive=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragments.add(new HomeFragment());
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,fragments.get(0),"0")
                .commit();
    }


}
