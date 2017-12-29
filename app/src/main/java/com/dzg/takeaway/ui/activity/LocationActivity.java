package com.dzg.takeaway.ui.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.dzg.takeaway.App;
import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.HeaderRecyclerAndFooterWrapperAdapter;
import com.dzg.takeaway.adapter.LocationAdapter;
import com.dzg.takeaway.adapter.LocationSearchAdapter;
import com.dzg.takeaway.adapter.SelectCityAdapter;
import com.dzg.takeaway.adapter.listener.OnItemClickListener;
import com.dzg.takeaway.adapter.listener.ViewHolder;
import com.dzg.takeaway.injector.component.ApplicationComponent;
import com.dzg.takeaway.injector.component.DaggerLocationActivityComponent;
import com.dzg.takeaway.injector.component.LocationActivityComponent;
import com.dzg.takeaway.injector.module.ActivityModule;
import com.dzg.takeaway.injector.module.LocationActivityModule;
import com.dzg.takeaway.mvp.contract.LocationActivityContract;
import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.CityHeaderBean;
import com.dzg.takeaway.mvp.model.CitySelectBean;
import com.dzg.takeaway.mvp.model.Constants;
import com.dzg.takeaway.mvp.presenter.LocationActivityPresenter;
import com.dzg.takeaway.ui.view.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import com.dzg.takeaway.ui.view.indexlib.IndexBar.widget.IndexBar;
import com.dzg.takeaway.ui.view.indexlib.suspension.SuspensionDecoration;
import com.dzg.takeaway.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by dengzhouguang on 2017/12/1.
 */

public class LocationActivity extends BaseActivity implements LocationActivityContract.View{
    @Inject
    LocationActivityPresenter mPresenter;
    @BindView(R.id.location_nearbyrv)
    RecyclerView mNearbyRv;
    @BindView(R.id.location_toolbar)
    Toolbar mToolBar;
    @BindView(R.id.location_cityll)
    LinearLayout mLocationCityLl;
    @BindView(R.id.location_citytv)
    TextView mCityTv;
    @BindView(R.id.location_searchll)
    LinearLayout mSearchLl;
    @BindView(R.id.location_searchtv)
    TextView mSearchTv;
    @BindView(R.id.location_nowtv)
    TextView mNowAddrTv;
    @BindView(R.id.location_relocationtv)
    TextView mReLocationTv;
    @BindView(R.id.location_morell)
    LinearLayout mMoreLl;
    @BindView(R.id.location_searchet)
    EditText mSearchEt;
    @BindView(R.id.location_nearbytv)
    TextView mNearbyTv;
    @BindView(R.id.location_relocationiv)
    ImageView mReLocationIv;
    @BindView(R.id.locationsearchll)
    LinearLayout mHistorySearchll;
    @BindView(R.id.locationsearchrv)
    RecyclerView mLocationSearchRv;
    @BindView(R.id.location_search_contentll)
    LinearLayout mSearchContentLl;
    @BindView(R.id.location_nearby_contentll)
    LinearLayout mNearbyContentLl;
    @BindView(R.id.location_city_selectfl)
    FrameLayout mCitySelectFl;
    @BindView(R.id.location_city_indexBar)
    IndexBar mIndexBar;
    @BindView(R.id.location_city_SideBarHinttv)
    TextView mSideBarHintTv;
    @BindView(R.id.location_cityrv)
    RecyclerView mCityRv;

    private boolean mIsFirst = true;
    private LocationAdapter mLocationAdapter;
    private LocationSearchAdapter mSearchAdapter;
    private Animation mRotate;
    private SelectCityAdapter mSelectCityAdapterAdapter;
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
    private List<BaseIndexPinyinBean> mSourceDatas;   //设置给InexBar、ItemDecoration的完整数据集
    private List<CityHeaderBean> mHeaderDatas;    //头部数据源
    private List<CitySelectBean> mBodyDatas;     //主体部分数据源（城市数据）
    private SuspensionDecoration mDecoration;
    private ArrayList<Address> mAddresses;
    private Address mNowAddress;
    @Override
    protected int getLayoutId() {
        return  R.layout.activity_location;
    }

    protected void initView() {
        injectDependences();
        mPresenter.attachView(this);
        initToolBar();
        initRv();
        mPresenter.initLocation(new MyLocationListener());
        mReLocationIv.startAnimation(mRotate);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorMain));
    }

    @Override
    protected void initDatas() {

    }

    private void injectDependences() {
        ApplicationComponent applicationComponent = App.getInstance().getApplicationComponent();
        LocationActivityComponent component= DaggerLocationActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .locationActivityModule(new LocationActivityModule())
                .build();
        component.inject(this);
    }

    private void initRv() {
        mRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        mRotate.setInterpolator(new LinearInterpolator());
        mNearbyRv.setLayoutManager(new LinearLayoutManager(this));
        mNearbyRv.setHasFixedSize(false);
        mNearbyRv.setNestedScrollingEnabled(false);
        mNearbyRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mLocationAdapter = new LocationAdapter(new ArrayList<>());
        mLocationAdapter.setOnItemClickListener(new OnItemClickListener<Address>() {
            @Override
            public void onItemClick(Address address) {
                Intent intent = new Intent();
                intent.putExtra("address", address);
                setResult(Constants.RESULT_CODE, intent);
                finish();
            }
        });
        mNearbyRv.setAdapter(mLocationAdapter);

        mLocationSearchRv.setLayoutManager(new LinearLayoutManager(this));
        mLocationSearchRv.setHasFixedSize(false);
        mLocationSearchRv.setNestedScrollingEnabled(false);
        mLocationSearchRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mSearchAdapter = new LocationSearchAdapter(new ArrayList<>());
        mSearchAdapter.setOnItemClickListener(new OnItemClickListener<Address>() {
            @Override
            public void onItemClick(Address address) {
                Intent intent = new Intent();
                intent.putExtra("address", address);
                setResult(Constants.RESULT_CODE, intent);
                finish();   //finish似乎不是立刻执行
                Object object = SPUtils.getObjectFromShare("addresses");
                List<Address> addresses;
                if (object != null) {
                    addresses = (List<Address>) object;
                    for (Address address1 : addresses) {
                        if (address1.getName().equals(address.getName())) {
                            return;
                        }
                    }
                } else
                    addresses = new ArrayList<>();
                addresses.add(0, address);
                SPUtils.putObjectToShare("addresses", addresses);
            }
        });
        mLocationSearchRv.setAdapter(mSearchAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mCityRv.setLayoutManager(linearLayoutManager);
        mCityRv.setHasFixedSize(false);
        mCityRv.setNestedScrollingEnabled(false);
        mCityRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mSourceDatas = new ArrayList<>();
        mHeaderDatas = new ArrayList<>();
        mBodyDatas = new ArrayList<>();
        List<String> locationCity = new ArrayList<>();
        locationCity.add("定位中");
        mHeaderDatas.add(new CityHeaderBean(locationCity, "定位城市", "#"));
        mSourceDatas.addAll(mHeaderDatas);
        mSelectCityAdapterAdapter = new SelectCityAdapter(mBodyDatas);
        mSelectCityAdapterAdapter.setOnItemClickListener(new OnItemClickListener<CitySelectBean>() {
            @Override
            public void onItemClick(CitySelectBean citySelectBean) {
                mCityTv.setText(citySelectBean.getCity());
                mSearchEt.clearFocus();
                mCitySelectFl.setVisibility(View.GONE);
                mHistorySearchll.setVisibility(View.GONE);
                mSearchContentLl.setVisibility(View.GONE);
                mNearbyContentLl.setVisibility(View.VISIBLE);
            }
        });
        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(mSelectCityAdapterAdapter) {
            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {
                final CityHeaderBean cityHeaderBean = (CityHeaderBean) o;
                ArrayList<CitySelectBean> list = new ArrayList<>();
                CitySelectBean bean = new CitySelectBean();
                bean.setCity(cityHeaderBean.getCityList().get(0));
                list.add(bean);
                RecyclerView recyclerView = holder.getView(R.id.rvCity);
                SelectCityAdapter adapter = new SelectCityAdapter(list);
                adapter.setOnItemClickListener(new OnItemClickListener<CitySelectBean>() {
                    @Override
                    public void onItemClick(CitySelectBean citySelectBean) {
                    }
                });
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(LocationActivity.this));
            }
        };
        mHeaderAdapter.setHeaderView(0, R.layout.city_item_header, mHeaderDatas.get(0));
        mCityRv.setAdapter(mHeaderAdapter);
        mCityRv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mSourceDatas)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics()))
                .setColorTitleBg(0xffefefef)
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()))
                .setColorTitleFont(LocationActivity.this.getResources().getColor(android.R.color.black))
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size()));
        mIndexBar.setmPressedShowTextView(mSideBarHintTv)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(linearLayoutManager)//设置RecyclerView的LayoutManager
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size());
    }

    public  void showCitys(final ArrayList<String> data) {
        mBodyDatas = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            CitySelectBean cityBean = new CitySelectBean();
            cityBean.setCity(data.get(i));
            mBodyDatas.add(cityBean);
        }
        //先排序
        mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);
        mSelectCityAdapterAdapter.addDatas(mBodyDatas);
        mHeaderAdapter.notifyDataSetChanged();
        mSourceDatas.addAll(mBodyDatas);
        mIndexBar.setmSourceDatas(mSourceDatas).invalidate();
        mDecoration.setmDatas(mSourceDatas);
        CityHeaderBean header = mHeaderDatas.get(0);
        header.getCityList().clear();
        header.getCityList().add(mCityTv.getText().toString());
        mHeaderAdapter.notifyItemRangeChanged(1, 3);
    }


    private void initToolBar() {
        mToolBar.setTitle("选择收货地址");
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeAsUpIndicator(R.mipmap.back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNearbyContentLl.getVisibility() == View.GONE) {
                    mNearbyContentLl.setVisibility(View.VISIBLE);
                    mSearchContentLl.setVisibility(View.GONE);
                    mCitySelectFl.setVisibility(View.GONE);
                } else
                    finish();
            }
        });
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(LocationActivity.this, "新增地址成功！", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        mToolBar.setTitleTextAppearance(this, R.style.ToolBar_Title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_menu, menu);
        return true;
    }

    private void initData(String addr) {
    }

    @OnClick({R.id.location_cityll, R.id.location_searchtv, R.id.location_searchll, R.id.location_searchet, R.id.location_garbageiv,
            R.id.location_morell, R.id.location_nearbytv, R.id.location_relocationtv, R.id.location_nowtv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_cityll:
                mNearbyContentLl.setVisibility(View.GONE);
                mSearchContentLl.setVisibility(View.GONE);
                mCitySelectFl.setVisibility(View.VISIBLE);
                mPresenter.initCityDatas();
                break;

            case R.id.location_searchll:
                if (!mSearchEt.isFocused()) {
                    mSearchEt.requestFocus();
                }
                break;

            case R.id.location_searchtv:
                String string = mSearchEt.getText().toString().trim();
                if (string.length() > 0) {
                    mSearchAdapter.initDatas(null);
                    mPresenter.search(string);
                } else {
                    mSearchTv.setVisibility(View.GONE);
                    mSearchEt.clearFocus();
                    mSearchEt.setText("");
                    mNearbyContentLl.setVisibility(View.VISIBLE);
                    mSearchContentLl.setVisibility(View.GONE);
                    mCitySelectFl.setVisibility(View.GONE);
                }
                break;

            case R.id.location_nowtv:
                Intent intent = new Intent();
                intent.putExtra("address", mNowAddress);
                setResult(Constants.RESULT_CODE, intent);
                finish();
                break;
            case R.id.location_relocationtv:
                mIsFirst = true;
                mSearchEt.clearFocus();
                mPresenter.restart();
                mReLocationIv.setImageResource(R.mipmap.relocation_loading);
                mReLocationIv.startAnimation(mRotate);
                break;
            case R.id.location_garbageiv:
                SPUtils.remove("addresses");
                mSearchAdapter.initDatas(null);
                mSearchAdapter.notifyDataSetChanged();
                break;
            case R.id.location_morell:
                Intent moreIntent = new Intent();
                moreIntent.setClass(this, MoreAddrssActivity.class);
                moreIntent.putExtra("list",mAddresses);
                startActivityForResult(moreIntent, Constants.REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == Constants.RESULT_CODE) {
            Address address = (Address) data.getSerializableExtra("address");
            Log.e("ActivityResult", address.getName());
            Intent intent = new Intent();
            intent.putExtra("address", address);
            setResult(Constants.RESULT_CODE, intent);
            finish();
        }
    }

    private void showHistorySearch() {
        Object objectFromShare = SPUtils.getObjectFromShare("addresses");
        if (objectFromShare != null) {
            mSearchAdapter.initDatas((List<Address>) objectFromShare);
            mSearchAdapter.notifyDataSetChanged();
        }
    }

    @OnFocusChange(R.id.location_searchet)
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && mSearchTv.getVisibility() == View.GONE) {
            mSearchTv.setText("取消");
            mSearchContentLl.setVisibility(View.VISIBLE);
            mNearbyContentLl.setVisibility(View.GONE);
            mCitySelectFl.setVisibility(View.GONE);
            mSearchTv.setVisibility(View.VISIBLE);
            showHistorySearch();
        }
    }

    @OnTextChanged(value = R.id.location_searchet, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if ("".equals(s.toString())) {
            mSearchTv.setText("取消");
            mHistorySearchll.setVisibility(View.VISIBLE);
            showHistorySearch();
        } else {
            mSearchTv.setText("搜索");
            mSearchAdapter.initDatas(null);
            mPresenter.search(s.toString());
            mSearchAdapter.notifyDataSetChanged();
            mHistorySearchll.setVisibility(View.GONE);
        }
    }



    @Override
    public void showAddress(List<Address> addresses) {
        if (addresses.size() > 0) {
            mNowAddress=addresses.remove(0);
            mNowAddrTv.setText(mNowAddress.getName());
            if (addresses.size() > 0) {
                mAddresses= (ArrayList<Address>) addresses;
                mNearbyTv.setVisibility(View.VISIBLE);
                List<Address> list = addresses.subList(0, 3);
                mLocationAdapter.initDatas(list);
                mLocationAdapter.notifyDataSetChanged();
                mMoreLl.setVisibility(View.VISIBLE);
            } else {
                mNearbyTv.setVisibility(View.GONE);
                mMoreLl.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void searchSuccess(List<Address> addresses) {
        mSearchAdapter.initDatas(addresses);
        mSearchAdapter.notifyDataSetChanged();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            String addr = location.getAddrStr();    //获取详细地址信息
            String city = location.getCity();    //获取城市
            switch (location.getLocType()) {
                case Constants.LOCATION_SUCCESS_GPS:
                case Constants.LOCATION_SUCCESS_NETWORK:
                    if (mIsFirst && city != null) {
                        mPresenter.initDatas(addr);
                        mCityTv.setText(city);
                        mPresenter.onStop();
                        mIsFirst = false;
                        mReLocationIv.clearAnimation();
                        mReLocationIv.setImageResource(R.mipmap.relocation);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
    }
}
