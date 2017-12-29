package com.dzg.takeaway.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dzg.takeaway.App;
import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.HeaderRecyclerAndFooterWrapperAdapter;
import com.dzg.takeaway.adapter.LocationSearchAdapter;
import com.dzg.takeaway.adapter.SelectCityAdapter;
import com.dzg.takeaway.adapter.VpAdapter;
import com.dzg.takeaway.adapter.listener.OnItemClickListener;
import com.dzg.takeaway.adapter.listener.ViewHolder;
import com.dzg.takeaway.injector.component.ApplicationComponent;
import com.dzg.takeaway.injector.component.DaggerMoreAddressComponent;
import com.dzg.takeaway.injector.component.MoreAddressComponent;
import com.dzg.takeaway.injector.module.ActivityModule;
import com.dzg.takeaway.injector.module.MoreAddressModule;
import com.dzg.takeaway.mvp.contract.MoreAddressContract;
import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.CityHeaderBean;
import com.dzg.takeaway.mvp.model.CitySelectBean;
import com.dzg.takeaway.mvp.model.Constants;
import com.dzg.takeaway.mvp.model.HistoryBean;
import com.dzg.takeaway.mvp.presenter.MoreAddressPresenter;
import com.dzg.takeaway.ui.fragment.SearchListFragment;
import com.dzg.takeaway.ui.view.dragflowlayout.ClickToDeleteItemListenerImpl;
import com.dzg.takeaway.ui.view.dragflowlayout.DragAdapter;
import com.dzg.takeaway.ui.view.dragflowlayout.DragFlowLayout;
import com.dzg.takeaway.ui.view.dragflowlayout.IViewObserver;
import com.dzg.takeaway.ui.view.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import com.dzg.takeaway.ui.view.indexlib.IndexBar.widget.IndexBar;
import com.dzg.takeaway.ui.view.indexlib.suspension.SuspensionDecoration;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

/**
 * Created by dengzhouguang on 2017/12/3.
 */

public class MoreAddrssActivity extends BaseActivity implements MoreAddressContract.View{
    @Inject
    MoreAddressPresenter mPresenter;
    @BindView(R.id.moreaddress_city_selectfl)
    FrameLayout mCitySelectFl;
    @BindView(R.id.moreaddress_city_indexBar)
    IndexBar mIndexBar;
    @BindView(R.id.moreaddress_city_SideBarHinttv)
    TextView mSideBarHintTv;
    @BindView(R.id.moreaddress_cityrv)
    RecyclerView mCityRv;
    @BindView(R.id.moreaddress_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.moreaddress_magicindicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.moreaddress_backiv)
    ImageView mBackIv;
    @BindView(R.id.moreaddress_citytv)
    TextView mCityTv;
    @BindView(R.id.moreaddress_categoryll)
    LinearLayout mCategoryLl;
    @BindView(R.id.moreaddress_canceliv)
    ImageView mCancelIv;
    @BindView(R.id.moreaddress_historyll)
    LinearLayout mHistoryLl;
    @BindView(R.id.moreaddress_garbageiv)
    ImageView mGarbageIv;
    @BindView(R.id.moreaddress_et)
    EditText mSearchEt;
    @BindView(R.id.drag_flowLayout)
    DragFlowLayout mDragFlowLayout;
    @BindView(R.id.moreaddress_searchrv)
    RecyclerView mSearchRv;
    @BindView(R.id.moreaddress_searchll)
    LinearLayout mSearchLl;

    private static final String[] TITLES = new String[]{"全部", "写字楼", "小区", "学校"};
    private List<String> mTitles = Arrays.asList(TITLES);
    private ArrayList<Address> mDatas;
    private VpAdapter mVpAdapter;
    private SelectCityAdapter mSelectCityAdapterAdapter;
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
    private List<BaseIndexPinyinBean> mSourceDatas;
    private List<CityHeaderBean> mHeaderDatas;
    private List<CitySelectBean> mBodyDatas;
    private SuspensionDecoration mDecoration;
    List<Address> mAddresses;
    LocationSearchAdapter mSearchAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_address;
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initView() {
        injectDependences();
        mPresenter.attachView(this);
        initTab();
        initCitySelectRv();
        initHistotyContent();
        initSearchRv();
    }

    private void injectDependences() {
        ApplicationComponent applicationComponent = App.getInstance().getApplicationComponent();
        MoreAddressComponent component= DaggerMoreAddressComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .moreAddressModule(new MoreAddressModule())
                .build();
        component.inject(this);
    }


    private void initTab(){
        mDatas = (ArrayList<Address>) getIntent().getExtras().getSerializable("list");
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setBackgroundColor(Color.WHITE);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mTitles.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#787878"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#45A6FF"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#2395ff"));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setDividerPadding(UIUtil.dip2px(this, 15));
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(SearchListFragment.newInstance(mDatas));
        list.add(SearchListFragment.newInstance(mDatas));
        list.add(SearchListFragment.newInstance(mDatas));
        list.add(SearchListFragment.newInstance(mDatas));
        mVpAdapter = new VpAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(mVpAdapter);
        mViewPager.setOffscreenPageLimit(3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mViewPager.setNestedScrollingEnabled(false);
        }
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }
    private void initSearchRv() {
        mSearchRv.setLayoutManager(new LinearLayoutManager(this));
        mSearchRv.setHasFixedSize(false);
        mSearchRv.setNestedScrollingEnabled(false);
        mSearchRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mSearchAdapter = new LocationSearchAdapter(new ArrayList<>());
        mSearchAdapter.setOnItemClickListener(new OnItemClickListener<Address>() {
            @Override
            public void onItemClick(Address address) {
                Intent intent = new Intent();
                intent.putExtra("address", address);
                setResult(Constants.RESULT_CODE, intent);
                finish();
                mPresenter.saveAddress(address);
            }
        });
        mSearchRv.setAdapter(mSearchAdapter);
    }

    private void initHistotyContent() {
        mDragFlowLayout.setOnItemClickListener(new ClickToDeleteItemListenerImpl(R.id.iv_close) {
            @Override
            protected void onDeleteSuccess(DragFlowLayout dfl, View child, Object data) {
                HistoryBean bean = (HistoryBean) data;
                mPresenter.removeAddress(mAddresses,bean.getText());
            }
        });
        mDragFlowLayout.setDragAdapter(new DragAdapter<HistoryBean>() {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_drag_flow;
            }

            @Override
            public void onBindData(View itemView, int dragState, HistoryBean data) {
                itemView.setTag(data);
                TextView tv = itemView.findViewById(R.id.tv_text);
                tv.setText(data.getText());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (Address address : mAddresses) {
                            if (address.getName().equals(((TextView) v).getText())) {
                                setResult(address);
                            }
                        }
                    }
                });
                itemView.findViewById(R.id.iv_close).setVisibility(
                        dragState != DragFlowLayout.DRAG_STATE_IDLE
                                && data.isDraggable() ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public HistoryBean getData(View itemView) {
                return (HistoryBean) itemView.getTag();
            }
        });
        mDragFlowLayout.prepareItemsByCount(10);
        mDragFlowLayout.setOnDragStateChangeListener(new DragFlowLayout.OnDragStateChangeListener() {
            @Override
            public void onDragStateChange(DragFlowLayout dfl, int dragState) {
            }
        });
        //添加view观察者
        mDragFlowLayout.addViewObserver(new IViewObserver() {
            @Override
            public void onAddView(View child, int index) {
            }

            @Override
            public void onRemoveView(View child, int index) {
            }
        });
        Object object=mPresenter.getAddressObject();
        List<HistoryBean> historys = new ArrayList<>();
        List<Address> addresses;
        if (object != null) {
            addresses = (List<Address>) object;
            for (Address address : addresses) {
                HistoryBean bean = new HistoryBean(address.getName());
                historys.add(bean);
            }
        } else
            addresses = new ArrayList<>();
        mAddresses = addresses;
        mDragFlowLayout.getDragItemManager().addItems(historys);
    }

    public void setResult(Address address) {
        Intent intent = new Intent();
        intent.putExtra("address", address);
        setResult(Constants.RESULT_CODE, intent);
        finish();
    }

    private void initCitySelectRv() {
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
                mCategoryLl.setVisibility(View.VISIBLE);
                mCitySelectFl.setVisibility(View.GONE);
                mHistoryLl.setVisibility(View.GONE);
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
                recyclerView.setLayoutManager(new LinearLayoutManager(MoreAddrssActivity.this));
            }
        };
        mHeaderAdapter.setHeaderView(0, R.layout.city_item_header, mHeaderDatas.get(0));
        mCityRv.setAdapter(mHeaderAdapter);
        mCityRv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mSourceDatas)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics()))
                .setColorTitleBg(0xffefefef)
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()))
                .setColorTitleFont(MoreAddrssActivity.this.getResources().getColor(android.R.color.black))
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size()));
        mIndexBar.setmPressedShowTextView(mSideBarHintTv)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(linearLayoutManager)//设置RecyclerView的LayoutManager
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size());
    }
    public void showCitys(final List<String> data) {
        mBodyDatas = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            CitySelectBean cityBean = new CitySelectBean();
            cityBean.setCity(data.get(i));//设置城市名称
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

    @Override
    public void showSearch(List<Address> list) {
        mSearchAdapter.initDatas(list);
        mSearchAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.moreaddress_citytv, R.id.moreaddress_backiv, R.id.moreaddress_canceliv, R.id.moreaddress_garbageiv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.moreaddress_citytv:
                mCategoryLl.setVisibility(View.GONE);
                mCitySelectFl.setVisibility(View.VISIBLE);
                mPresenter.initCitys();
                break;

            case R.id.moreaddress_backiv:
                if (mCategoryLl.getVisibility() == View.GONE) {
                    mCategoryLl.setVisibility(View.VISIBLE);
                    mHistoryLl.setVisibility(View.GONE);
                    mCitySelectFl.setVisibility(View.GONE);
                } else
                    finish();
                break;
            case R.id.moreaddress_canceliv:
                mCancelIv.setVisibility(View.GONE);
                mSearchEt.setText("");
                break;
            case R.id.moreaddress_garbageiv:
                mPresenter.removeAll();
                mDragFlowLayout.getDragItemManager().clearItems();
                break;
            default:
                break;

        }
    }

    @OnFocusChange(R.id.moreaddress_et)
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            mHistoryLl.setVisibility(View.VISIBLE);
            mCategoryLl.setVisibility(View.GONE);
            mCitySelectFl.setVisibility(View.GONE);
            mSearchLl.setVisibility(View.GONE);
        } else {
            mCategoryLl.setVisibility(View.VISIBLE);
            mCitySelectFl.setVisibility(View.GONE);
            mHistoryLl.setVisibility(View.GONE);
            mSearchLl.setVisibility(View.GONE);
        }
    }

    @OnTextChanged(value = R.id.moreaddress_et, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if ("".equals(s.toString())) {
            mCancelIv.setVisibility(View.GONE);
            mHistoryLl.setVisibility(View.VISIBLE);
            mCitySelectFl.setVisibility(View.GONE);
            mCategoryLl.setVisibility(View.GONE);
            mSearchLl.setVisibility(View.GONE);
            mSearchAdapter.initDatas(null);
            mSearchAdapter.notifyDataSetChanged();

        } else {
            mCancelIv.setVisibility(View.VISIBLE);
            mSearchLl.setVisibility(View.VISIBLE);
            mHistoryLl.setVisibility(View.GONE);
            mCitySelectFl.setVisibility(View.GONE);
            mCategoryLl.setVisibility(View.GONE);
            mPresenter.search(s.toString());
        }
    }

}
