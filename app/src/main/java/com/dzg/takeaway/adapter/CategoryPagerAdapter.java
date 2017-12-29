package com.dzg.takeaway.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.dzg.takeaway.R;
import com.dzg.takeaway.mvp.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CategoryPagerAdapter extends PagerAdapter {
    private List<Category> mDataList;
    public CategoryPagerAdapter(List<Category> dataList) {
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        if (mDataList == null || mDataList.size() == 0)
            return 0;
        else if (mDataList.size() / 8 <= 1)
            return 1;
        else if (mDataList.size() / 8 <= 2)
            return 2;
        else
            return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /*TextView textView = new TextView(container.getContext());
        textView.setText(mDataList.get(position));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(24);
        container.addView(textView);*/
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.category_gridview, container, false);
        GridView gridView = view.findViewById(R.id.gview);
        List<Map<String, Object>> list = getData(position);
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        SimpleAdapter simpleadapter = new SimpleAdapter(container.getContext(), list, R.layout.item_category, from, to);
        gridView.setAdapter(simpleadapter);
        container.addView(view);
        return view;
    }

    public List<Map<String, Object>> getData(int position) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (mDataList.size() <= 8) {
            for (int i = 0; i < mDataList.size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("image", mDataList.get(i).getResId());
                map.put("text", mDataList.get(i).getName());
                list.add(map);
            }
        }else
        {
            for (int i = 0; i < 8; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("image", mDataList.get(i+position*8).getResId());
                map.put("text", mDataList.get(i+position*8).getName());
                list.add(map);
            }
        }

        return list;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {

        LinearLayout linearLayout = (LinearLayout) object;
        Category category= (Category) ((GridView)linearLayout.findViewById(R.id.gview)).getAdapter().getItem(0);
        int index = mDataList.indexOf(category);
        if (index<0)
            return POSITION_NONE;
        else if (index<8)
            index=0;
        else if (index<16)
            index=1;
        else
            index=2;
        return index;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataList.get(position).getName();
    }
}
