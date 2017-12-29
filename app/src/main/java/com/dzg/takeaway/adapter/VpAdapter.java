package com.dzg.takeaway.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dengzhouguang on 2017/11/26.
 */

public class VpAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> data;

    public VpAdapter(FragmentManager fm, ArrayList<Fragment> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Fragment getItem(int position) {
        return data.get(position);
    }
}