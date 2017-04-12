package com.dianzhi.launchomode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CK on 2017/2/6.
 */

public class SecondActivity extends Activity {
    private ListView lv;
    private List<String> datas=new ArrayList<>();
    private  ArrayAdapter<String> stringArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        lv= (ListView) findViewById(R.id.lv);
        lv.addHeaderView(View.inflate(this,R.layout.list_header_view,null));
        for (int i = 0; i < 20; i++) {
            datas.add("CK"+i);
        }
        stringArrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, datas);
        lv.setAdapter(stringArrayAdapter);
    }

    public void update(View view) {
        for (int i = 20; i < 40; i++) {
            datas.add("CK"+i);
        }
        stringArrayAdapter.notifyDataSetChanged();
    }

}
