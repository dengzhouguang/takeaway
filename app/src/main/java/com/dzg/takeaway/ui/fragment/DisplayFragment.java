package com.dzg.takeaway.ui.fragment;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.bumptech.glide.Glide;
import com.dzg.takeaway.App;
import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.CategoryPagerAdapter;
import com.dzg.takeaway.adapter.RestaurantAdapter;
import com.dzg.takeaway.injector.component.ApplicationComponent;
import com.dzg.takeaway.injector.component.DaggerDisplayFragmentComponent;
import com.dzg.takeaway.injector.component.DisplayFragmentComponent;
import com.dzg.takeaway.injector.module.DisplayFragmentModule;
import com.dzg.takeaway.injector.module.FragmentModule;
import com.dzg.takeaway.mvp.contract.DisplayFragmentContract;
import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.Category;
import com.dzg.takeaway.mvp.model.Constants;
import com.dzg.takeaway.mvp.model.Restaurant;
import com.dzg.takeaway.mvp.model.Weather;
import com.dzg.takeaway.mvp.presenter.DisplayFragmentPresenter;
import com.dzg.takeaway.ui.activity.LocationActivity;
import com.dzg.takeaway.ui.activity.SearchActivity;
import com.dzg.takeaway.ui.view.PullToRefreshView;
import com.dzg.takeaway.ui.view.ScaleRectNavigator;
import com.dzg.takeaway.ui.view.refreshlibrary.PullToRefreshBase;
import com.dzg.takeaway.util.TextUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/29.
 */

public class DisplayFragment extends BaseFragment implements DisplayFragmentContract.View{
    @Inject
    DisplayFragmentPresenter mPresenter;
    @BindView(R.id.locationtv)
    TextView mLocationTv;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.reloadtv)
    TextView mReloadTv;
    @BindView(R.id.xrecyclerView)
    XRecyclerView mRecyclerView;
    @BindView(R.id.content_loading)
    LinearLayout mLoadingLl;
    @BindView(R.id.content_error)
    LinearLayout mErrorLl;
    @BindView(R.id.error_no_network)
    ImageView mNoNetworkIv;
    @BindView(R.id.loadingIv)
    ImageView mLoadingIv;
    @BindView(R.id.temperaturetv)
    TextView mTemperatureTv;
    @BindView(R.id.weathertv)
    TextView mWeatherTv;
    @BindView(R.id.weatheriv)
    ImageView mWeatherIv;
    @BindView(R.id.searchll)
    LinearLayout mSearchLl;
    @BindView(R.id.pull_to_refresh_view)
    PullToRefreshView mPullToRefreshView;
    private LocationClient mLocationClient = null;
    private int mOffset = 0;
    private Address mAddress;
    private String mAddr;
    private RestaurantAdapter mAdapter;
    private boolean mIsFirst = true;
    private boolean mIsReStart = false;
    private Animation mRotate;

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initView() {
        injectDependences();
        mPresenter.attachView(this);
        mPresenter.initLocation(new MyLocationListener());
        mPresenter.getCategory();
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                /**
                 * verticalOffset=0 为 appBarLayout 完全展开的时候
                 * 完全展开 即可拦截下拉事件 执行 刷新操作
                 */
                mPullToRefreshView.setCanRefresh(verticalOffset == 0);
            }
        });

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<FrameLayout>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<FrameLayout> refreshView) {
                if (mAddress != null)
                    mPresenter.updates(mAddress);
                else {
                    mRecyclerView.setVisibility(View.GONE);
                    mLoadingLl.setVisibility(View.GONE);
                    mErrorLl.setVisibility(View.VISIBLE);
                    mPullToRefreshView.onPullDownRefreshComplete();
                }
            }
        });
        mRotate= AnimationUtils.loadAnimation(getActivity(),R.anim.rotate);
        mRotate.setInterpolator(new LinearInterpolator());
        mLoadingIv.startAnimation(mRotate);
    }

    private void injectDependences() {
        ApplicationComponent applicationComponent = App.getInstance().getApplicationComponent();
        DisplayFragmentComponent component= DaggerDisplayFragmentComponent.builder()
                .applicationComponent(applicationComponent)
                .fragmentModule(new FragmentModule(this))
                .displayFragmentModule(new DisplayFragmentModule())
                .build();
        component.inject(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_display;
    }


    @OnClick({R.id.reloadtv, R.id.locationll, R.id.searchll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reloadtv:
                mLoadingIv.startAnimation(mRotate);
                mErrorLl.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                mLoadingLl.setVisibility(View.VISIBLE);
                if (mAddress != null)
                    mPresenter.updates(mAddress);
                else {
                    mPresenter.initDates(mAddr);
                }
                break;
            case R.id.locationll:
                Intent intent = new Intent();
                intent.setClass(getActivity(), LocationActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE);
                break;
            case R.id.searchll:
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                mSearchLl, getActivity().getString(R.string.transition));
                Intent searchintent = new Intent(getActivity(), SearchActivity.class);
                searchintent.putExtra("address",mAddress);
                ActivityCompat.startActivity(getActivity(), searchintent, options.toBundle());
                break;
            default:
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == Constants.RESULT_CODE) {
            mAddress = (Address) data.getSerializableExtra("address");
            mLocationTv.setText(mAddress.getName());
            mIsReStart = true;
        }
    }

    @Override
    public void onDestroy() {
        if (mLocationClient != null && mLocationClient.isStarted())
            mLocationClient.stop();
        mLocationClient = null;
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mIsReStart) {
            mPresenter.updates(mAddress);
            mIsReStart = false;
        }
    }



    @Override
    public void showCategory(List<Category> list) {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.header_main, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        CategoryPagerAdapter categoryPagerAdapter = new CategoryPagerAdapter(list);
        ViewPager categoryVp = inflate.findViewById(R.id.category_viewpager);
        MagicIndicator magicIndicator = inflate.findViewById(R.id.category_indicator);
        categoryVp.setAdapter(categoryPagerAdapter);
        ScaleRectNavigator scaleRectNavigator = new ScaleRectNavigator(getActivity());
        scaleRectNavigator.setRectCount(2);
        scaleRectNavigator.setRectClickListener(new ScaleRectNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                categoryVp.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(scaleRectNavigator);
        ViewPagerHelper.bind(magicIndicator, categoryVp);
        mAdapter = new RestaurantAdapter(new ArrayList<>(), getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setHorizontalFadingEdgeEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addHeaderView(inflate);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                Log.e("onLoadMore", "onLoadMore :" + mOffset);
                if (mAddress != null)
                    mPresenter.getRestaurants(mAddress, mOffset);
            }
        });

    }

    @Override
    public void showUpdatesSuccess(List<Restaurant> restaurants) {
        mAdapter.initDatas(restaurants);
        mAdapter.notifyDataSetChanged();
        if (mOffset == 0)
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        if (restaurants.size() == 0) {
            mRecyclerView.setNoMore(true);
            mRecyclerView.setLoadingMoreEnabled(false);
        }
        if (restaurants.size() != 0)
            Log.e("getRestaurants", restaurants.get(0).toString());
        mOffset = mOffset + restaurants.size();
        mRecyclerView.loadMoreComplete();
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setNoMore(false);
        mPullToRefreshView.setLastUpdatedLabel(TextUtils.formatDateTime(System.currentTimeMillis()));
        mPullToRefreshView.onPullDownRefreshComplete();
    }

    @Override
    public void showUpdatesError(Throwable e) {
        mLoadingIv.clearAnimation();
        mRecyclerView.setVisibility(View.GONE);
        mLoadingLl.setVisibility(View.GONE);
        mErrorLl.setVisibility(View.VISIBLE);
        mPullToRefreshView.onPullDownRefreshComplete();
    }

    @Override
    public void showUpdatesCompleter() {
        mErrorLl.setVisibility(View.GONE);
        mLoadingLl.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mLoadingIv.clearAnimation();
    }

    @Override
    public void showInitSuccess(List<Address> addresses) {
        if (addresses != null && addresses.size() > 0) {
            mAddress = addresses.get(0);
            mPresenter.updates(mAddress);
            mLocationTv.setText(addresses.get(0).getName());

        }
    }

    @Override
    public void showInitError(Throwable e) {
        mLoadingIv.clearAnimation();
        mRecyclerView.setVisibility(View.GONE);
        mLoadingLl.setVisibility(View.GONE);
        mErrorLl.setVisibility(View.VISIBLE);
        mRecyclerView.loadMoreComplete();
    }

    @Override
    public void showRestaurantsSuccess(List<Restaurant> restaurants) {
        mAdapter.addDatas(restaurants);
        mAdapter.notifyDataSetChanged();
        if (mOffset == 0)
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        if (restaurants.size() == 0) {
            mRecyclerView.setNoMore(true);
            mRecyclerView.setLoadingMoreEnabled(false);
        }
        if (restaurants.size() != 0)
            Log.e("getRestaurants", restaurants.get(0).toString());
        mOffset = mOffset + restaurants.size();
        mRecyclerView.loadMoreComplete();
        mPullToRefreshView.setLastUpdatedLabel(TextUtils.formatDateTime(System.currentTimeMillis()));
        mPullToRefreshView.onPullDownRefreshComplete();
    }

    @Override
    public void showRestaurantsError(Throwable throwable) {
        mRecyclerView.loadMoreComplete();
        Toast.makeText(getActivity(), "网络异常请稍后再试！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showWeather(Weather weather) {
        if (weather != null) {
            Weather.WeatherBean.NowBean now = weather.getWeather().get(0).getNow();
            mTemperatureTv.setVisibility(View.VISIBLE);
            mWeatherTv.setVisibility(View.VISIBLE);
            mTemperatureTv.setText(now.getTemperature() + "º");
            mWeatherTv.setText(now.getText());
            Glide.with(getActivity()).load("http://weixin.jirengu.com/images/weather/code/" + now.getCode() + ".png").into(mWeatherIv);
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            String addr = location.getAddrStr();
            switch (location.getLocType()) {
                case Constants.LOCATION_SUCCESS_GPS:
                case Constants.LOCATION_SUCCESS_NETWORK:
                    if (mIsFirst) {
                        mPresenter.getWeather(location.getLatitude(), location.getLongitude());
                        mPresenter.initDates(addr);
                        mIsFirst = false;
                        mAddr = addr;
                        mPresenter.onStop();
                    }
                    break;
                case Constants.LOCATION_ERROR_INVALID:
                    Log.e("LocationError", "无法获取有效定位依据，定位失败，请检查运营商网络或者WiFi网络是否正常开启，尝试重新请求定位");
                    break;
                case Constants.LOCATION_ERROR_NETWORK:
                    Log.e("LocationError", "网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位");
                    break;
                case Constants.LOCATION_ERROR_PERMISSION:
                    Log.e("LocationError", "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位");
                    break;
                default:
                    break;

            }
        }
    }



}
