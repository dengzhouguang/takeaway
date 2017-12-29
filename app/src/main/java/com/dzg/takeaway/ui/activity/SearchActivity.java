package com.dzg.takeaway.ui.activity;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.dzg.takeaway.App;
import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.PopupAdapter;
import com.dzg.takeaway.adapter.SearchRestaurantAdapter;
import com.dzg.takeaway.adapter.SearchResultAdapter;
import com.dzg.takeaway.adapter.listener.OnItemClickListener;
import com.dzg.takeaway.injector.component.ApplicationComponent;
import com.dzg.takeaway.injector.component.DaggerSearchActivityComponent;
import com.dzg.takeaway.injector.component.SearchActivityComponent;
import com.dzg.takeaway.injector.module.ActivityModule;
import com.dzg.takeaway.injector.module.SearchActivityModule;
import com.dzg.takeaway.mvp.contract.SearchContract;
import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.Constants;
import com.dzg.takeaway.mvp.model.HistoryBean;
import com.dzg.takeaway.mvp.model.HotBean;
import com.dzg.takeaway.mvp.model.SearchRestaurant;
import com.dzg.takeaway.mvp.model.SortPopupBean;
import com.dzg.takeaway.mvp.presenter.SearchPresenter;
import com.dzg.takeaway.ui.view.dragflowlayout.ClickToDeleteItemListenerImpl;
import com.dzg.takeaway.ui.view.dragflowlayout.DragAdapter;
import com.dzg.takeaway.ui.view.dragflowlayout.DragFlowLayout;
import com.dzg.takeaway.util.DensityUtil;
import com.dzg.takeaway.util.SPUtils;
import com.dzg.takeaway.util.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by dengzhouguang on 2017/12/4.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View{
    @Inject
    SearchPresenter mPresenter;
    @BindView(R.id.search_restaurantll)
    LinearLayout mSearchRestaurantLl;
    @BindView(R.id.search_history_select)
    ScrollView mHistoryAndHot;
    @BindView(R.id.search_hot_drag_flowLayout)
    DragFlowLayout mHotFlowLayout;
    @BindView(R.id.search_et)
    EditText mSearchEt;
    @BindView(R.id.search_history_drag_flowLayout)
    DragFlowLayout mHistoryFlowLayout;
    @BindView(R.id.search_restaurantrv)
    RecyclerView mSearchRestaurantRv;
    @BindView(R.id.search_resultll)
    LinearLayout mSearchResultLl;
    @BindView(R.id.search_result_sorttypetv)
    TextView mSortTypeTv;
    @BindView(R.id.search_result_sortiv)
    ImageView mSortTypeIv;
    @BindView(R.id.search_result_sort_sales_maxtv)
    TextView mSalesMaxTv;
    @BindView(R.id.search_result_sort_distance_mintv)
    TextView mDistanceMinTv;
    @BindView(R.id.search_result_sort_filtertv)
    TextView mSortFilterTv;
    @BindView(R.id.search_result_sort_filteriv)
    ImageView mSortFilterIv;
    @BindView(R.id.search_resultrv)
    RecyclerView mSearchResultRv;
    @BindView(R.id.search_sortll)
    LinearLayout mSearchSortLl;

    private List<HistoryBean> mList;
    private Address mAddress;
    private SearchRestaurantAdapter mSearchRestaurantAdapter;
    private boolean mIsFirst = true;
    private LocationClient mLocationClient;
    private PopupWindow mPopupWindow;
    private boolean mIsShowing = false;
    private PopupAdapter mPopupAdapter;
    private SearchResultAdapter mSearchResultAdapter;
    private List<SearchRestaurant.RestaurantWithFoodsBean> mRestaurantWithFoodsBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        injectDependences();
        mPresenter.attachView(this);
        mPresenter.initDatas(new MyLocationListener());

    }
    private void injectDependences() {
        ApplicationComponent applicationComponent = App.getInstance().getApplicationComponent();
        SearchActivityComponent component= DaggerSearchActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(new ActivityModule(this))
                .searchActivityModule(new SearchActivityModule())
                .build();
        component.inject(this);
    }
    private void initSearchResult() {
        initPopupWindow();
        mSearchResultAdapter = new SearchResultAdapter(new ArrayList<>(), this);
        mSearchResultRv.setHasFixedSize(false);
        mSearchResultRv.setNestedScrollingEnabled(false);
        mSearchResultRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mSearchResultRv.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultRv.setAdapter(mSearchResultAdapter);
    }

    private void initPopupWindow() {
        View content = LayoutInflater.from(this).inflate(R.layout.pop_up_window, null);
        RecyclerView recyclerView = content.findViewById(R.id.rv_pw);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<SortPopupBean> list = new ArrayList<>();
        list.add(new SortPopupBean("综合排序", true));
        list.add(new SortPopupBean("好评优先", false));
        list.add(new SortPopupBean("起送价最低", false));
        list.add(new SortPopupBean("配送最快", false));
        mPopupAdapter = new PopupAdapter(list, this);
        mPopupAdapter.setOnItemClickListener(new OnItemClickListener<Integer>() {
            @Override
            public void onItemClick(Integer integer) {
                switch (integer) {
                    case 0:
                        mSortTypeTv.setText("综合排序");
                        mSearchResultAdapter.setDatas(mRestaurantWithFoodsBean);
                        mSearchResultAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        mSortTypeTv.setText("好评优先");
                        mSearchResultAdapter.sort(SearchResultAdapter.SORT_RATING);
                        mSearchResultAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        mSortTypeTv.setText("起送价最低");
                        mSearchResultAdapter.sort(SearchResultAdapter.SORT_DELIVERY_FEE_MIN);
                        mSearchResultAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        mSortTypeTv.setText("配送最快");
                        mSearchResultAdapter.sort(SearchResultAdapter.SORT_DELIVERY_MOSTFAST);
                        mSearchResultAdapter.notifyDataSetChanged();
                        break;
                }
                mPopupAdapter.setSelected(integer);
                mPopupWindow.dismiss();
                mPopupAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(mPopupAdapter);
        mPopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(180), true);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_white));
        mPopupWindow.setFocusable(false);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mSortTypeTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                mSortTypeIv.setImageResource(R.mipmap.search_sort_default);
                mSearchResultLl.getBackground().setAlpha(255);
                mIsShowing = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsShowing = false;
                    }
                }, 100);
            }
        });
    }

    private void initSearchRestaurant() {
        mSearchRestaurantRv.setLayoutManager(new LinearLayoutManager(this));
        mSearchRestaurantRv.setHasFixedSize(false);
        mSearchRestaurantRv.setNestedScrollingEnabled(false);
        mSearchRestaurantRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mSearchRestaurantAdapter = new SearchRestaurantAdapter(new SearchRestaurant(), this);
        mSearchRestaurantAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(String s) {
                mSearchEt.setText(s);
                mSearchEt.setSelection(s.length());
                //TODO 搜索
            }
        });
        mSearchRestaurantRv.setAdapter(mSearchRestaurantAdapter);
    }

    @Override
    protected void initDatas() {
        ;
        mRestaurantWithFoodsBean = new ArrayList<>();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.statusbar_grey));
    }

    private void initHistotyContent() {
        mHistoryFlowLayout.setOnItemClickListener(new ClickToDeleteItemListenerImpl(R.id.iv_close) {
            @Override
            protected void onDeleteSuccess(DragFlowLayout dfl, View child, Object data) {
                HistoryBean bean = (HistoryBean) data;
                for (HistoryBean historyBean : mList) {
                    if (historyBean.equals(bean.getText())) {
                        mList.remove(historyBean);
                    }
                }
                SPUtils.putObjectToShare("searchlist", mList);
            }
        });
        mHistoryFlowLayout.setDragAdapter(new DragAdapter<HistoryBean>() {
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
                        mSearchEt.setText(((TextView) v).getText());
                        mSearchEt.setSelection(((TextView) v).getText().length());
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
        mHistoryFlowLayout.prepareItemsByCount(10);
        mHistoryFlowLayout.setOnDragStateChangeListener(new DragFlowLayout.OnDragStateChangeListener() {
            @Override
            public void onDragStateChange(DragFlowLayout dfl, int dragState) {
            }
        });
        mHistoryFlowLayout.getDragItemManager().addItems(getHistoryBeans());


        //热门
        mHotFlowLayout.setDragAdapter(new DragAdapter<HotBean>() {
            @Override
            public int getItemLayoutId() {
                return R.layout.item_drag_flow;
            }

            @Override
            public void onBindData(View itemView, int dragState, HotBean data) {
                itemView.setTag(data);

                TextView tv = itemView.findViewById(R.id.tv_text);
                tv.setText(data.getText());
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String str = ((TextView) v).getText().toString();
                        mSearchEt.setText(str);
                        mSearchEt.setSelection(str.length());
                        for (HistoryBean historyBean : mList) {
                            if (historyBean.equals(str)) {
                                return;
                            }
                        }
                        mList.add(new HistoryBean(str));
                        SPUtils.putObjectToShare("searchlist", mList);
                    }
                });
                itemView.findViewById(R.id.iv_close).setVisibility(View.GONE);
            }

            @Override
            public HotBean getData(View itemView) {
                return (HotBean) itemView.getTag();
            }
        });
        mHotFlowLayout.prepareItemsByCount(10);
        mHotFlowLayout.getDragItemManager().addItems(mPresenter.initRecommend());
    }

    @NonNull
    private List<HistoryBean> getHistoryBeans() {
        Object object = SPUtils.getObjectFromShare("searchhistory");
        List<HistoryBean> historys = (ArrayList<HistoryBean>) object;
        if (object != null)
            historys = (List<HistoryBean>) object;
        else
            historys = new ArrayList<>();
        mList = historys;
        return historys;
    }

    @OnClick({R.id.search_back, R.id.search_historytv, R.id.search_history_garbageiv, R.id.search_result_sorttypetv,
            R.id.search_result_sort_sales_maxtv, R.id.search_result_sort_distance_mintv, R.id.search_result_sort_filtertv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back:
                if (mHistoryAndHot.getVisibility() == View.VISIBLE)
                    finish();
                else {
                    mHistoryAndHot.setVisibility(View.VISIBLE);
                    mSearchResultLl.setVisibility(View.GONE);
                    mSearchSortLl.setVisibility(View.GONE);
                    mSearchRestaurantLl.setVisibility(View.GONE);
                }
                break;
            case R.id.search_historytv:
                String editText = mSearchEt.getText().toString().trim();
                if (editText.equals(""))
                    break;
                boolean exists = false;
                for (HistoryBean historyBean : mList) {
                    if (editText.equals(historyBean.getText())) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    HistoryBean bean = new HistoryBean(editText);
                    mList.add(0,bean);
                    mHistoryFlowLayout.getDragItemManager().addItem(bean);
                    SPUtils.putObjectToShare("searchhistory", mList);
                }
                mHistoryAndHot.setVisibility(View.GONE);
                mSearchRestaurantLl.setVisibility(View.GONE);
                mSearchResultLl.setVisibility(View.VISIBLE);
                mSearchSortLl.setVisibility(View.VISIBLE);
                mSearchResultAdapter.setDatas(mRestaurantWithFoodsBean);
                mSearchResultAdapter.notifyDataSetChanged();
                break;
            case R.id.search_history_garbageiv:
                mHistoryFlowLayout.getDragItemManager().clearItems();
                SPUtils.remove("searchhistory");
                break;
            case R.id.search_result_sorttypetv:
                if (!mIsShowing) {
                    mSortTypeTv.setTextColor(getResources().getColor(R.color.colorMain));
                    mSalesMaxTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                    mDistanceMinTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                    mSortFilterTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                    mSortTypeIv.setImageResource(R.mipmap.search_sort_selecting);
                    popupWindowShow(view);
                }
                break;
            case R.id.search_result_sort_sales_maxtv:
                mSalesMaxTv.setTextColor(getResources().getColor(R.color.colorMain));
                mSortTypeTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                mDistanceMinTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                mSortFilterTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                mSortFilterIv.setImageResource(R.mipmap.search_result_sort_filter_default);
                mSearchResultAdapter.sort(SearchResultAdapter.SORT_MOST_SALES);
                mSearchResultAdapter.notifyDataSetChanged();
                break;
            case R.id.search_result_sort_distance_mintv:
                mSalesMaxTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                mSortTypeTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                mDistanceMinTv.setTextColor(getResources().getColor(R.color.colorMain));
                mSortFilterTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                mSortFilterIv.setImageResource(R.mipmap.search_result_sort_filter_default);
                mSearchResultAdapter.sort(SearchResultAdapter.SORT_DISTANCE_MIN);
                mSearchResultAdapter.notifyDataSetChanged();
                break;
            case R.id.search_result_sort_filtertv:
                mSalesMaxTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                mSortTypeTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                mDistanceMinTv.setTextColor(getResources().getColor(R.color.colorLightBlack));
                mSortFilterTv.setTextColor(getResources().getColor(R.color.colorMain));
                mSortFilterIv.setImageResource(R.mipmap.search_result_sort_filter_selected);
                //TODO  赛选功能
                break;
            default:
                break;
        }
    }

    @OnFocusChange(R.id.search_et)
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
        } else {
        }
    }

    @OnTextChanged(value = R.id.search_et, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if ("".equals(s.toString())) {
            mHistoryAndHot.setVisibility(View.VISIBLE);
            mSearchRestaurantLl.setVisibility(View.GONE);
        } else {
            mSearchRestaurantLl.setVisibility(View.VISIBLE);
            mHistoryAndHot.setVisibility(View.GONE);
            mPresenter.search(s.toString(), mAddress.getLatitude(), mAddress.getLongitude());
        }
    }

    @Override
    public void showDatas(Address address) {
        Log.e("看不懂",address.toString());
        mAddress=address;
        initHistotyContent();
        initSearchRestaurant();
        initSearchResult();
    }

    @Override
    public void showSearch(SearchRestaurant searchRestaurant) {
        mRestaurantWithFoodsBean = searchRestaurant.getRestaurant_with_foods();
        mSearchRestaurantAdapter.setData(searchRestaurant);
        mSearchRestaurantAdapter.notifyDataSetChanged();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            switch (location.getLocType()) {
                case Constants.LOCATION_SUCCESS_GPS:
                case Constants.LOCATION_SUCCESS_NETWORK:
                    if (mIsFirst) {
                        mAddress = new Address();
                        mAddress.setLatitude(location.getLatitude());
                        mAddress.setLongitude(location.getLongitude());
                        mIsFirst = false;
                        mLocationClient.stop();
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

    private void popupWindowShow(View view) {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int h = DensityUtil.dp2px(96) + StatusBarUtils.getStatusBarHeight(this) - 20;
        ;
        Point p = new Point();
        wm.getDefaultDisplay().getSize(p);
        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, h);
        mSearchResultLl.getBackground().setAlpha(180);
    }

}
