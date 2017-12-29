package com.dzg.takeaway.mvp.model;

import com.dzg.takeaway.ui.view.dragflowlayout.IDraggable;
import com.heaven7.adapter.BaseSelector;

/**
 * Created by dengzhouguang on 2017/12/4.
 */

public class HotBean extends BaseSelector implements IDraggable {
    String text;
    boolean draggable = true;
    public HotBean(String text) {
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
