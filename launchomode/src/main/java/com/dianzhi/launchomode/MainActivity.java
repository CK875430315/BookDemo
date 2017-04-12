package com.dianzhi.launchomode;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity implements View.OnScrollChangeListener {
  private MyGrideView listview;
  public ViewPager vp;
  private MyScrollView activity_main;
  private LinearLayout ll_sticky;
    private ArrayList<Fragment> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity_main= (MyScrollView) findViewById(R.id.activity_main);
//        ll_sticky= (LinearLayout) findViewById(R.id.ll_sticky);
        vp= (ViewPager) findViewById(R.id.vp);

        list = new ArrayList<Fragment>();

        for (int i = 0; i < 2; i++) {
            list.add(new Fragment1());
        }
        MyPagerAdpater myPagerAdpater = new MyPagerAdpater(getSupportFragmentManager());

        vp.setAdapter(myPagerAdpater);

//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                activity_main.smoothScrollTo(0,20);
//            }
//        });
        activity_main.setOnScrollChangeListener(this);
//        vp.setAdapter(new VPAdapter());


    }

    class MyPagerAdpater extends FragmentPagerAdapter {

        public MyPagerAdpater(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
private class VPAdapter extends PagerAdapter{

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view= View.inflate(MainActivity.this,R.layout.vp_item,null);
       ListView listView= (ListView) view.findViewById(R.id.lv);
        List<String> datas=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add("ddd"+i);
        }
        listView.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,datas));
        int listViewHeight = ViewUtil.setListViewHeightBasedOnChildren1(listView);

        ViewGroup.LayoutParams params =listView.getLayoutParams();
        params.height = listViewHeight;
        listView.setLayoutParams(params);
        container.addView(listView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Log.e("sssss", "onScrollChange: "+"scrollY:"+scrollY+",oldScrollY:"+oldScrollY);
//        if (scrollY < dip2px(this, 120)) {
//         ll_sticky.setVisibility(View.INVISIBLE);
//        } else {
//         ll_sticky.setVisibility(View.VISIBLE);
//
//        }
    }

    /**
     * 动态的算出ListView实际的LayoutParams
     * 最关键的是算出LayoutParams.height
     */

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
