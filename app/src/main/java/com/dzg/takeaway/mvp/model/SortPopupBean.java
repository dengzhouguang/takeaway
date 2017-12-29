package com.dzg.takeaway.mvp.model;

/**
 * Created by dengzhouguang on 2017/12/5.
 */

public class SortPopupBean {
    private String name;
    private boolean isSelected;

    public SortPopupBean(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public SortPopupBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
