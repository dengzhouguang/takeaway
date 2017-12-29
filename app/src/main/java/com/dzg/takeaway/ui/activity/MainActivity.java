package com.dzg.takeaway.ui.activity;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.VpAdapter;
import com.dzg.takeaway.ui.fragment.DisplayFragment;
import com.dzg.takeaway.ui.fragment.FindFragment;
import com.dzg.takeaway.util.DensityUtil;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.viewpage)
    ViewPager mViewPage;
    @BindView(R.id.navigation)
    BottomNavigationViewEx mNavigation;
    private VpAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void initView() {
        mNavigation.setOnNavigationItemSelectedListener(this);
        mNavigation.enableAnimation(false);
        mNavigation.enableShiftingMode(false);
        mNavigation.enableItemShiftingMode(false);
        mNavigation.setIconsMarginTop(DensityUtil.dp2px(12));
        mNavigation.setIconSize(20, 20);
        mNavigation.setTextSize(12);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new DisplayFragment());
        fragments.add(new FindFragment());
        fragments.add(new FindFragment());
        fragments.add(new FindFragment());
        mAdapter = new VpAdapter(getSupportFragmentManager(), fragments);
        mViewPage.setAdapter(mAdapter);
        mViewPage.setOffscreenPageLimit(3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mViewPage.setNestedScrollingEnabled(false);
        }
        mNavigation.setupWithViewPager(mViewPage);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorMain));
    }

    @Override
    protected void initDatas() {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_takeaway:
                return true;
            case R.id.navigation_find:
                return true;
            case R.id.navigation_order:
                return true;
            case R.id.navigation_user:
                return true;
        }
        return false;
    }



}
