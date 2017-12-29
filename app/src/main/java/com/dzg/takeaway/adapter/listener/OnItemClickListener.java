package com.dzg.takeaway.adapter.listener;

/**
 * 通用的RecyclerView 的ItemClickListener，包含点击
 */
public interface OnItemClickListener<T>
{
    void onItemClick(T t);
}