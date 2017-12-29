package com.dzg.takeaway.adapter.listener;

/**
 * 通用的RecyclerView 的ItemClickListener，包含点击
 */
public interface OnItemClickViewListener<T,E,P,S>
{
    void onItemClick(T t,E e,P p,S s);
}