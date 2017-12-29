package com.dzg.takeaway.mvp.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/29.
 */

public class Address implements Serializable{
    private static final long serialVersionUID = 2421263553592651152L;
    /**
     * id : 16661187699763599770
     * name : 广东海洋大学
     * address : 广东省湛江市麻章区海大路1号(湖光岩景区东门)
     * short_address : 海大路1号(湖光岩景区东门)
     * latitude : 21.15053
     * longitude : 110.30151
     * city : 湛江市
     * request_id : 1912838916374350916
     * city_id : 79
     * geohash : w7y2mfr4g2x
     * count : 245
     */

    private String id;
    private String name;
    private String address;
    private String short_address;
    private double latitude;
    private double longitude;
    private String city;
    private String request_id;
    private int city_id;
    private String geohash;
    private int count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", short_address='" + short_address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", city='" + city + '\'' +
                ", request_id='" + request_id + '\'' +
                ", city_id=" + city_id +
                ", geohash='" + geohash + '\'' +
                ", count=" + count +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShort_address() {
        return short_address;
    }

    public void setShort_address(String short_address) {
        this.short_address = short_address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
