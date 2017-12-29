package com.dzg.takeaway.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.RestaurantImageAdapter;
import com.dzg.takeaway.adapter.listener.OnItem2ClickListener;
import com.dzg.takeaway.mvp.model.RestaurantDetail;
import com.dzg.takeaway.ui.activity.ViewBigImageActivity;
import com.dzg.takeaway.util.TextUtils;
import com.dzg.takeaway.util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dengzhouguang on 2017/12/14.
 */

public class RestaurantFragment extends BaseFragment {
    @BindView(R.id.fragment_restaurant_shoptv)
    TextView mShopTv;
    @BindView(R.id.fragment_restaurant_shop_categorytv)
    TextView mCategoryTv;
    @BindView(R.id.fragment_restaurant_shoprv)
    RecyclerView mShopRv;
    @BindView(R.id.fragment_restaurant_shop_addresstv)
    TextView mAddrssTv;
    @BindView(R.id.fragment_restaurant_shop_telephonetv)
    TextView mTelephoneTv;
    @BindView(R.id.fragment_restaurant_shop_opentimetv)
    TextView mOpenTimeTv;


    private RestaurantDetail mRestaurantDetail;
    private RestaurantImageAdapter mRestaurantImageAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_restaurant;
    }

    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        mRestaurantDetail = (RestaurantDetail) arguments.getSerializable("restaurantDetail");
        if (mRestaurantDetail != null) {
            mShopTv.setText(mRestaurantDetail.getDescription());
        }else
            mShopTv.setText(R.string.no_summary);
        mCategoryTv.setText(TextUtils.formatCategory(mRestaurantDetail.getFlavors()));
        mAddrssTv.setText(mRestaurantDetail.getAddress());
        if (mRestaurantDetail.getPhone().contains(" "))
        mTelephoneTv.setText(mRestaurantDetail.getPhone().substring(0,mRestaurantDetail.getPhone().indexOf(" ")));
        else
            mTelephoneTv.setText(mRestaurantDetail.getPhone());
        mOpenTimeTv.setText(TextUtils.formatOpenHours(mRestaurantDetail.getOpening_hours()));
        if (mRestaurantDetail.getAlbums()!=null&&mRestaurantDetail.getAlbums().size()>0){
        mShopRv.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mShopRv.setHasFixedSize(false);
        mRestaurantImageAdapter=new RestaurantImageAdapter((ArrayList<RestaurantDetail.AlbumsBean>) mRestaurantDetail.getAlbums(),getActivity());
        mRestaurantImageAdapter.setOnItemClickListener(new OnItem2ClickListener<ArrayList<RestaurantDetail.AlbumsBean>,Integer>() {
            @Override
            public void onItemClick(ArrayList<RestaurantDetail.AlbumsBean> albumsBeans, Integer position) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putSerializable("albumsBeans", albumsBeans);
                Intent intent = new Intent(getActivity(), ViewBigImageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mShopRv.setAdapter(mRestaurantImageAdapter);
        }else {
            mShopRv.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initDatas() {

    }

    @OnClick({R.id.fragment_restaurant_shop_telephonetv,R.id.fragment_restaurant_shop_aptituderl})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fragment_restaurant_shop_telephonetv:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+mTelephoneTv.getText()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.fragment_restaurant_shop_aptituderl:
                ToastUtil.showToast("营业资质");
                break;
            case R.id.fragment_restaurant_shop_reportrl:
                ToastUtil.showToast("举报商家");
                break;
                default:
                    break;
        }
    }

    public static RestaurantFragment newInstance(RestaurantDetail restaurantDetail) {
        RestaurantFragment newFragment = new RestaurantFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("restaurantDetail", restaurantDetail);
        newFragment.setArguments(bundle);
        return newFragment;
    }
}
