package com.dianzhi.bookdemo.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.dianzhi.bookdemo.R;
import com.dianzhi.bookdemo.entity.CityEntity;
import com.dianzhi.bookdemo.ui.view.PinnedSectionListView;
import com.dianzhi.bookdemo.ui.view.SlideBar;
import com.dianzhi.bookdemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CK on 2017/2/28.
 */

public class ListViewSection extends Activity {
    @BindView(R.id.slide_bar)
    SlideBar slide_bar;
    @BindView(R.id.listview)
    PinnedSectionListView pinnedSectionListView;

    public static void startActivity(Context context) {
        Intent intent=new Intent(context,ListViewSection.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ButterKnife.bind(this);
        initializeAdapter();
        slide_bar.setOnTouchLetterChangeListenner(new SlideBar.OnTouchLetterChangeListenner() {
            @Override
            public void onTouchLetterChange(boolean isTouched, String s) {
                ToastUtils.showToast(ListViewSection.this, s);
                if (sections != null) {
                    for (int i = 0; i < sections.length; i++) {
                        if (sections[i].text.equals(s)) {
                            int listPosition = sections[i].listPosition;
                            pinnedSectionListView.setSelection(listPosition);
                        }
                    }
                }
            }
        });
        pinnedSectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = (Item) pinnedSectionListView.getAdapter().getItem(position);
                if (item.type == Item.ITEM) {
                    ToastUtils.showToast(ListViewSection.this,item.text);
                }
            }
        });

    }

    static class Item {

        public static final int ITEM = 0;
        public static final int SECTION = 1;

        public final int type;
        public final String text;

        public int sectionPosition;
        public int listPosition;

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }

    }

    private static ArrayList<CityEntity> datas = new ArrayList<>();

    static class SimpleAdapter extends ArrayAdapter<Item> implements PinnedSectionListView.PinnedSectionListAdapter {

//        private static final int[] COLORS = new int[] {
//                R.color.green_light, R.color.orange_light,
//                R.color.blue_light, R.color.red_light };

        public SimpleAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
            generateDataset('A', 'Z', false);
        }

        public void generateDataset(char from, char to, boolean clear) {

//            if (clear) clear();

            CityEntity cityEntityA = new CityEntity();
            cityEntityA.setLetter("A");
            ArrayList<String> a = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                a.add("安徽" + i);
            }
            cityEntityA.setCitynames(a);
            datas.add(cityEntityA);
            CityEntity cityEntityB = new CityEntity();
            cityEntityB.setLetter("B");
            ArrayList<String> b = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                b.add("北京" + i);
            }
            cityEntityB.setCitynames(b);
            datas.add(cityEntityB);

            CityEntity cityEntityC = new CityEntity();
            cityEntityC.setLetter("C");
            ArrayList<String> c = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                c.add("长按" + i);
            }
            cityEntityC.setCitynames(c);
            datas.add(cityEntityC);

            CityEntity cityEntityZ = new CityEntity();
            cityEntityZ.setLetter("Z");
            ArrayList<String> z = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                z.add("浙江" + i);
            }
            cityEntityZ.setCitynames(z);
            datas.add(cityEntityZ);

            final int sectionsNumber = to - from + 1;
            prepareSections(datas.size());

            int sectionPosition = 0, listPosition = 0;
            for (int i = 0; i < datas.size(); i++) {
                Item section = new Item(Item.SECTION, datas.get(i).getLetter());
                section.sectionPosition = sectionPosition;
                section.listPosition = listPosition++;
                onSectionAdded(section, sectionPosition);
                add(section);

//                final int itemsNumber = (int) Math.abs((Math.cos(2f * Math.PI / 3f * sectionsNumber / (i + 1f)) * 25f));
                List<String> itemsNumber = datas.get(i).getCitynames();
                for (int j = 0; j < itemsNumber.size(); j++) {
                    Item item = new Item(Item.ITEM,  itemsNumber.get(j));
                    item.sectionPosition = sectionPosition;
                    item.listPosition = listPosition++;
                    add(item);
                }

                sectionPosition++;
            }


//            for (char i=0; i<sectionsNumber; i++) {
//               Item section = new Item(Item.SECTION, String.valueOf((char)('A' + i)));
//                section.sectionPosition = sectionPosition;
//                section.listPosition = listPosition++;
//                onSectionAdded(section, sectionPosition);
//                add(section);
//
//                final int itemsNumber = (int) Math.abs((Math.cos(2f*Math.PI/3f * sectionsNumber / (i+1f)) * 25f));
//                for (int j=0;j<itemsNumber;j++) {
//                    Item item = new Item(Item.ITEM, section.text.toUpperCase(Locale.ENGLISH) + " - " + j);
//                    item.sectionPosition = sectionPosition;
//                    item.listPosition = listPosition++;
//                    add(item);
//                }
//
//                sectionPosition++;
//            }
        }

        protected void prepareSections(int sectionsNumber) {
        }

        protected void onSectionAdded(Item section, int sectionPosition) {
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTextColor(Color.DKGRAY);
            view.setTag("" + position);
            Item item = getItem(position);
            if (item.type == Item.SECTION) {
                //view.setOnClickListener(PinnedSectionListActivity.this);
                view.setBackgroundColor(parent.getResources().getColor(R.color.color_8e8e8e));
            }
            return view;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position).type;
        }

        @Override
        public boolean isItemViewTypePinned(int viewType) {
            return viewType == Item.SECTION;
        }

    }

        private Item[] sections;
        class FastScrollAdapter extends SimpleAdapter implements SectionIndexer {


        public FastScrollAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
        }

        @Override
        protected void prepareSections(int sectionsNumber) {
            sections = new Item[sectionsNumber];
        }

        @Override
        protected void onSectionAdded(Item section, int sectionPosition) {
            sections[sectionPosition] = section;
        }

        @Override
        public Item[] getSections() {
            return sections;
        }

        @Override
        public int getPositionForSection(int section) {
            if (section >= sections.length) {
                section = sections.length - 1;
            }
            return sections[section].listPosition;
        }

        @Override
        public int getSectionForPosition(int position) {
            if (position >= getCount()) {
                position = getCount() - 1;
            }
            return getItem(position).sectionPosition;
        }

    }

        FastScrollAdapter fastScrollAdapter ;
    @SuppressLint("NewApi")
    private void initializeAdapter() {
        pinnedSectionListView.setFastScrollEnabled(true);
        fastScrollAdapter = new FastScrollAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        pinnedSectionListView.setAdapter(fastScrollAdapter);
//        getListView().setFastScrollEnabled(isFastScroll);
//        if (isFastScroll) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                getListView().setFastScrollAlwaysVisible(true);
//            }
//            setListAdapter(new FastScrollAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1));
//        } else {
//            setListAdapter(new SimpleAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1));
//        }
    }
}
