package com.dzg.takeaway.event;


import com.dzg.takeaway.mvp.model.Food;

import java.util.List;


public class MessageEvent {
    public List<Food> mList;

    public MessageEvent(List<Food>list) {
        this.mList=list;
    }
}
