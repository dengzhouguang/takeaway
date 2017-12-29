package com.dzg.takeaway.mvp.model;

/**
 * Created by dengzhouguang on 2017/12/13.
 */

public class RatingScore {

    /**
     * compare_rating : 0.35379464285714285
     * food_score : 4.2258
     * positive_rating : 0.85
     * restaurant_id : 155127428
     * service_score : 4.32067
     * star_level : 4.3
     */

    private double compare_rating;
    private double food_score;
    private double positive_rating;
    private int restaurant_id;
    private double service_score;
    private double star_level;

    public double getCompare_rating() {
        return compare_rating;
    }

    public void setCompare_rating(double compare_rating) {
        this.compare_rating = compare_rating;
    }

    public double getFood_score() {
        return food_score;
    }

    public void setFood_score(double food_score) {
        this.food_score = food_score;
    }

    public double getPositive_rating() {
        return positive_rating;
    }

    public void setPositive_rating(double positive_rating) {
        this.positive_rating = positive_rating;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public double getService_score() {
        return service_score;
    }

    public void setService_score(double service_score) {
        this.service_score = service_score;
    }

    public double getStar_level() {
        return star_level;
    }

    public void setStar_level(double star_level) {
        this.star_level = star_level;
    }
}
