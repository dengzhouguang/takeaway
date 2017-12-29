package com.dzg.takeaway.mvp.model;

import com.dzg.takeaway.ui.view.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/**
 * 介绍：美团里的城市bean
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/28.
 */

public class CitySelectBean extends BaseIndexPinyinBean {
    private String city;//城市名字

    public CitySelectBean() {
    }

    public CitySelectBean(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public CitySelectBean setCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public String getTarget() {
        return city;
    }
}
