package com.dzg.takeaway.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dzg.takeaway.App;
import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.EvaluateAdapter;
import com.dzg.takeaway.adapter.listener.EvaluateCategoryAdapter;
import com.dzg.takeaway.adapter.listener.OnItemClickListener;
import com.dzg.takeaway.injector.component.ApplicationComponent;
import com.dzg.takeaway.injector.component.DaggerEvaluateComponent;
import com.dzg.takeaway.injector.component.EvaluateComponent;
import com.dzg.takeaway.injector.module.EvaluateModule;
import com.dzg.takeaway.injector.module.FragmentModule;
import com.dzg.takeaway.mvp.contract.EvaluateContract;
import com.dzg.takeaway.mvp.model.Evaluate;
import com.dzg.takeaway.mvp.model.EvaluateCategory;
import com.dzg.takeaway.mvp.model.RatingScore;
import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.mvp.presenter.EvaluatePresenter;
import com.dzg.takeaway.util.MathUtil;
import com.dzg.takeaway.util.TextUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class EvaluateFragment extends BaseFragment implements EvaluateContract.View{
    @Inject
    EvaluatePresenter mPresenter;
    @BindView(R.id.evaluate_ratingtv)
    TextView mMultipleRatingTv;
    @BindView(R.id.evaluate_higher_nearbytv)
    TextView mHigherNearbyTv;
    @BindView(R.id.evaluate_server_startv)
    TextView mServerRatingTv;
    @BindView(R.id.evaluate_ratingshop_startv)
    TextView mFoodRatingTv;
    @BindView(R.id.evaluate_delivery_tv)
    TextView mDeliveryTv;
    @BindView(R.id.evaluate_server_starrb)
    RatingBar mServerRatingRb;
    @BindView(R.id.evaluate_server_starrb2)
    RatingBar mFoodRatingBar;
    @BindView(R.id.evaluate_filteriv)
    ImageView mFilterIv;
    @BindView(R.id.evaluate_categoryrv)
    RecyclerView mCategoryRv;
    @BindView(R.id.evaluate_evaluaterv)
    XRecyclerView mEvaluateRv;

    private RestaurantDetail mRestaurantDetail;
    private EvaluateCategoryAdapter mEvaluateCategoryAdapter;
    private EvaluateAdapter mEvaluateAdapter;
    private int mOffset = 0;
    private int mRecordType = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_evaluate;
    }

    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        mRestaurantDetail = (RestaurantDetail) arguments.getSerializable("restaurantDetail");
        injectDependences();
        mPresenter.attachView(this);
        initRv();
    }

    private void injectDependences() {
        ApplicationComponent applicationComponent = App.getInstance().getApplicationComponent();
        EvaluateComponent component= DaggerEvaluateComponent.builder()
                .applicationComponent(applicationComponent)
                .fragmentModule(new FragmentModule(this))
                .evaluateModule(new EvaluateModule())
                .build();
        component.inject(this);
    }

    private void initRv() {
        mEvaluateCategoryAdapter = new EvaluateCategoryAdapter(new ArrayList<>(), getActivity());
        mEvaluateCategoryAdapter.setOnItemClickListener(new OnItemClickListener<EvaluateCategory>() {
            @Override
            public void onItemClick(EvaluateCategory evaluateCategory) {
                mEvaluateCategoryAdapter.notifyDataSetChanged();
                mRecordType = evaluateCategory.getRecord_type();
                mOffset = 0;
                mPresenter.initEvaluate(mRestaurantDetail.getId(),mOffset,mRecordType);
            }
        });
        mCategoryRv.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mCategoryRv.setHasFixedSize(false);
        mCategoryRv.setAdapter(mEvaluateCategoryAdapter);

        mEvaluateAdapter = new EvaluateAdapter(new ArrayList<>(), getActivity());
        mEvaluateRv.setHasFixedSize(false);
        mEvaluateRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEvaluateRv.setPullRefreshEnabled(false);
        mEvaluateRv.setNestedScrollingEnabled(false);
        mEvaluateRv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                mPresenter.initEvaluate(mRestaurantDetail.getId(),mOffset,mRecordType);
            }
        });
        mEvaluateRv.setAdapter(mEvaluateAdapter);
    }
    @Override
    protected void initDatas() {
        mPresenter.initDatas(mRestaurantDetail.getId(),mRestaurantDetail.getLatitude(),mRestaurantDetail.getLongitude());
        mPresenter.initEvaluate(mRestaurantDetail.getId(),mOffset,mRecordType);
    }

    public void showContent(RatingScore ratingScore) {
        mMultipleRatingTv.setText(TextUtils.formatNum(ratingScore.getStar_level()));
        mHigherNearbyTv.setText(TextUtils.formatHigherNearby(ratingScore.getCompare_rating()));
        mServerRatingTv.setText(TextUtils.formatDec(ratingScore.getService_score()));
        mFoodRatingTv.setText(TextUtils.formatDec(ratingScore.getFood_score()));
        mDeliveryTv.setText(TextUtils.formatTime(mRestaurantDetail.getOrder_lead_time()));
        float serviceScore = Float.valueOf(TextUtils.formatDec(ratingScore.getService_score()));
        float foodScore = Float.valueOf(TextUtils.formatDec(ratingScore.getFood_score()));
        mServerRatingRb.setRating(MathUtil.parseStar(serviceScore));
        mFoodRatingBar.setRating(MathUtil.parseStar(foodScore));
        mPresenter.initEvaluateCategory(mRestaurantDetail.getId());
    }

    @Override
    public void showEvaluateCategory(List<EvaluateCategory> list) {
        mEvaluateCategoryAdapter.setDatas(list);
        mEvaluateCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEvaluateSuccess(List<Evaluate> list) {
        if (mOffset == 0)
            mEvaluateAdapter.setDatas(list);
        else
            mEvaluateAdapter.addDatas(list);
        mEvaluateAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEvaluateComplete() {
        mOffset++;
        mEvaluateRv.loadMoreComplete();
    }

    @Override
    public void showError() {
        mEvaluateRv.loadMoreComplete();
    }


    public static EvaluateFragment newInstance(RestaurantDetail restaurantDetail) {
        EvaluateFragment newFragment = new EvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurantDetail", restaurantDetail);
        newFragment.setArguments(bundle);
        return newFragment;
    }
}

