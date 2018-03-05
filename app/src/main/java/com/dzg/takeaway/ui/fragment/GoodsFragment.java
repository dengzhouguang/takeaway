package com.dzg.takeaway.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dzg.takeaway.App;
import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.BigramHeaderAdapter;
import com.dzg.takeaway.adapter.GoodsAdapter;
import com.dzg.takeaway.adapter.GoodsCategoryListAdapter;
import com.dzg.takeaway.adapter.listener.OnItemClickViewListener;
import com.dzg.takeaway.event.CartMessage;
import com.dzg.takeaway.event.GoodsListEvent;
import com.dzg.takeaway.injector.component.ApplicationComponent;
import com.dzg.takeaway.injector.component.DaggerGoodsComponent;
import com.dzg.takeaway.injector.component.GoodsComponent;
import com.dzg.takeaway.injector.module.FragmentModule;
import com.dzg.takeaway.injector.module.GoodsModule;
import com.dzg.takeaway.mvp.contract.GoodsContract;
import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.mvp.model.RestaurantMenu;
import com.dzg.takeaway.mvp.presenter.GoodsPresenter;
import com.dzg.takeaway.util.AlphaUtil;
import com.dzg.takeaway.util.TextUtils;
import com.eowise.recyclerview.stickyheaders.OnHeaderClickListener;
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * 商品
 */
public class GoodsFragment extends BaseFragment implements GoodsAdapter.OnShopCartGoodsChangeListener, OnHeaderClickListener ,GoodsContract.View{
    @Inject
    GoodsPresenter mPresenter;


    @BindView(R.id.goods_category_list)
    RecyclerView mGoodsCateGoryListRv;
    @BindView(R.id.goods_recycleView)
    RecyclerView mGoodsRv;


    private GoodsCategoryListAdapter mGoodsCategoryListAdapter;
    private GoodsAdapter mGoodsAdapter;
    private List<RestaurantMenu> mGoodscatrgoryEntities = new ArrayList<>();
    private List<RestaurantMenu.FoodsBean> mGoodsitemEntities = new ArrayList<>();
    private List<Integer> mTitlePois = new ArrayList<>();
    private StickyHeadersItemDecoration mTopStickyHeaders;
    private BigramHeaderAdapter mHeaderAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private PopupWindow mFoodDetailPopupWindow;
    private RestaurantDetail mRestaurantDetail;


    @Override
    protected void initDatas() {
        Bundle arguments = getArguments();
        mRestaurantDetail = (RestaurantDetail) arguments.getSerializable("restaurantDetail");
        if (mRestaurantDetail!=null)
        mPresenter.initRestaurantMenus(mRestaurantDetail.getId());
    }
    private void injectDependences() {
        ApplicationComponent applicationComponent = App.getInstance().getApplicationComponent();
        GoodsComponent component= DaggerGoodsComponent.builder()
                .applicationComponent(applicationComponent)
                .fragmentModule(new FragmentModule(this))
                .goodsModule(new GoodsModule())
                .build();
        component.inject(this);
    }
    @Override
    protected void initView() {
      injectDependences();
      mPresenter.attachView(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_goods;
    }


    private void initData(List<RestaurantMenu> dataList) {
        int i = 0;
        int j = 0;
        boolean isFirst;
        for (RestaurantMenu dataItem : dataList) {
            isFirst = true;
            for (RestaurantMenu.FoodsBean foodsBean : dataItem.getFoods()) {
                if (isFirst) {
                    mTitlePois.add(j);
                    isFirst = false;
                }
                j++;
                foodsBean.setId(i);
                mGoodsitemEntities.add(foodsBean);
            }
            i++;
        }
        mGoodsRv.setHasFixedSize(false);
        mGoodsCateGoryListRv.setHasFixedSize(false);

        mGoodscatrgoryEntities.addAll(dataList);
        mGoodsCategoryListAdapter = new GoodsCategoryListAdapter(mGoodscatrgoryEntities, getActivity());
        mGoodsCateGoryListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mGoodsCateGoryListRv.setAdapter(mGoodsCategoryListAdapter);
        mGoodsCategoryListAdapter.setOnItemClickListener(new GoodsCategoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ((LinearLayoutManager) mGoodsRv.getLayoutManager()).scrollToPositionWithOffset(mTitlePois.get(position),0);
                mGoodsCategoryListAdapter.setCheckPosition(position);
            }
        });

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mGoodsRv.setLayoutManager(mLinearLayoutManager);
        mGoodsAdapter = new GoodsAdapter(getActivity(), mGoodsitemEntities, mGoodscatrgoryEntities);
        mGoodsAdapter.setOnItemClickListener(new OnItemClickViewListener<RestaurantMenu.FoodsBean, View, Integer, GoodsAdapter.ViewHolder>() {
            @Override
            public void onItemClick(RestaurantMenu.FoodsBean foodsBean, View view, Integer integer, GoodsAdapter.ViewHolder holder) {
                showFoodDetail(foodsBean,view,integer,holder);
            }
        });
        mHeaderAdapter = new BigramHeaderAdapter(getActivity(), mGoodsitemEntities, mGoodscatrgoryEntities);
        mTopStickyHeaders = new StickyHeadersBuilder()
                .setAdapter(mGoodsAdapter)
                .setRecyclerView(mGoodsRv)
                .setStickyHeadersAdapter(mHeaderAdapter)
                .setOnHeaderClickListener(this)
                .build();
        mGoodsRv.addItemDecoration(mTopStickyHeaders);
        mGoodsRv.setAdapter(mGoodsAdapter);
        mGoodsRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i = 0; i < mTitlePois.size(); i++) {
                    if (mLinearLayoutManager.findFirstVisibleItemPosition() >= mTitlePois.get(i)) {
                        mGoodsCategoryListAdapter.setCheckPosition(i);
                    }
                }

            }
        });


    }

    private void showFoodDetail(RestaurantMenu.FoodsBean foodsBean, View view,Integer position, GoodsAdapter.ViewHolder holder) {
        View content = LayoutInflater.from(getActivity()).inflate(R.layout.layout_restaurant_detail_food_popupwindow, null);
        mFoodDetailPopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mFoodDetailPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_transparent));
        mFoodDetailPopupWindow.setFocusable(true);
        mFoodDetailPopupWindow.setOutsideTouchable(true);
        mFoodDetailPopupWindow.setAnimationStyle(R.style.PopupWindowAnimationFood);
        mFoodDetailPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AlphaUtil.backgroundAlpha(getActivity(),1f);
            }
        });
        if (!"".equals(foodsBean.getImage_path()))
            Glide.with(this).load(TextUtils.formatLagerImageUrl(foodsBean.getImage_path())).into(((ImageView)content.findViewById(R.id.restaurant_detail_foodpopup_foodiv)));
        ((TextView)content.findViewById(R.id.restaurant_detail_foodpopup_food_nametv)).setText(foodsBean.getName());
        ((TextView)content.findViewById(R.id.restaurant_detail_foodpopup_food_saleandratingtv)).setText(TextUtils.formatSalesAndPercentage(foodsBean.getMonth_sales(),foodsBean.getSatisfy_rate()));
        ((TextView)content.findViewById(R.id.restaurant_detail_foodpopup_moneytv)).setText(TextUtils.formatMoney(foodsBean.getMin_purchase()));
        content.findViewById(R.id.restaurant_detail_foodpopup_addtocarttv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodsAdapter.addShopCart(position,holder);
                mFoodDetailPopupWindow.dismiss();
            }
        });
        mFoodDetailPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        AlphaUtil.backgroundAlpha(getActivity(),0.5f);
    }


    @Override
    public void onNumChange() {

    }

    @Override
    public void onHeaderClick(View header, long headerId) {
        TextView text =header.findViewById(R.id.restaurant_detail_menutv);
        Toast.makeText(getActivity(), "Click on " + text.getText(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 添加 或者  删除  商品发送的消息处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoodsListEvent event) {
        if (event.buyNums.length > 0) {
            for (int i = 0; i < event.buyNums.length; i++) {
                mGoodscatrgoryEntities.get(i).setBugNum(event.buyNums[i]);
            }
            mGoodsCategoryListAdapter.changeData(mGoodscatrgoryEntities);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CartMessage event) {
        if (event.getType()==CartMessage.TYPE_ADD)
            mGoodsAdapter.addShopCart(event.getPosition(),event.getViewHolder());
        else
            mGoodsAdapter.deteleShopCart(event.getPosition(),event.getViewHolder());
    }

    public static GoodsFragment newInstance(RestaurantDetail restaurantDetail) {
        GoodsFragment newFragment = new GoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurantDetail", restaurantDetail);
        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public void showRestaurantMenu(List<RestaurantMenu> list) {
        if (list.size()>0)
            initData(list);
    }
}
