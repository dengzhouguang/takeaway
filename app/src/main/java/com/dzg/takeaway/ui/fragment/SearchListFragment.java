package com.dzg.takeaway.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dzg.takeaway.R;
import com.dzg.takeaway.adapter.LocationSearchAdapter;
import com.dzg.takeaway.adapter.listener.OnItemClickListener;
import com.dzg.takeaway.mvp.model.Address;
import com.dzg.takeaway.mvp.model.Constants;
import com.dzg.takeaway.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dengzhouguang on 2017/12/2.
 */

public class SearchListFragment extends BaseFragment {
    @BindView(R.id.searchlistrv)
    RecyclerView mRecyclerView;
    
    private LocationSearchAdapter mAdapter;
    private ArrayList<Address> mDatas;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        Bundle args = getArguments();
        mDatas= (ArrayList<Address>) args.getSerializable("list");
        initRecyclerView();
    }

    @Override
    protected void initDatas() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_searchlist;
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAdapter = new LocationSearchAdapter(mDatas);
       mAdapter.setOnItemClickListener(new OnItemClickListener<Address>() {
           @Override
           public void onItemClick(Address address) {
               Object object = SPUtils.getObjectFromShare("addresses");
               List<Address> addresses;
               if (object != null) {
                   addresses = (List<Address>) object;
               } else
                   addresses = new ArrayList<>();
               addresses.add(0, address);
               SPUtils.putObjectToShare("addresses", addresses);
               Intent intent = new Intent();
               intent.putExtra("address", address);
               getActivity().setResult(Constants.RESULT_CODE, intent);
               getActivity().finish();
           }
       });
        mRecyclerView.setAdapter(mAdapter);
    }

    public static SearchListFragment newInstance(ArrayList<Address> list) {
        SearchListFragment newFragment = new SearchListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list",  list);
        newFragment.setArguments(bundle);
        return newFragment;

    }
}
