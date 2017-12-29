package com.dzg.takeaway.mvp.model;

import com.dzg.takeaway.ui.view.dragflowlayout.IDraggable;
import com.heaven7.adapter.BaseSelector;

import java.io.Serializable;

/**
 * Created by dengzhouguang on 2017/12/4.
 */

public class HistoryBean extends BaseSelector implements IDraggable,Serializable {
    private static final long serialVersionUID = 2421263553592651150L;
    String text;
    boolean draggable = true;
    public HistoryBean(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean isDraggable() {
        return draggable;
    }

}
