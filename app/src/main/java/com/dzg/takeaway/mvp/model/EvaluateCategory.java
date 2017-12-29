package com.dzg.takeaway.mvp.model;

/**
 * Created by dengzhouguang on 2017/12/13.
 */

public class EvaluateCategory {

    /**
     * amount : 110
     * name : 全部
     * record_type : 1
     */

    private int amount;
    private String name;
    private int record_type;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRecord_type() {
        return record_type;
    }

    public void setRecord_type(int record_type) {
        this.record_type = record_type;
    }
}
