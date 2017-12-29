package com.dzg.takeaway.adapter.listener;

/**
 * 通用的RecyclerView 的ItemClickListener，包含点击
 */
public interface OnItem2ClickListener<T,K>
{
    void onItemClick(T t,K k);
}