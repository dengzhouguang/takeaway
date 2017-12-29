package com.dzg.takeaway.mvp.model;

import java.util.List;

public class Evaluate {

    /**
     * avatar :
     * highlights : []
     * item_rating_list : [{"food_id":749196394,"image_hash":"","rate_name":"奥尔良卤肉双拼便当","rating_star":5,"rating_text":""}]
     * rated_at : 2017-12-12
     * rating_star : 5
     * rating_text :
     * time_spent_desc :
     * username : 匿**户
     */

    private String avatar;
    private String rated_at;
    private int rating_star;
    private String rating_text;
    private String time_spent_desc;
    private String username;
    private List<?> highlights;
    private List<ItemRatingListBean> item_rating_list;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRated_at() {
        return rated_at;
    }

    public void setRated_at(String rated_at) {
        this.rated_at = rated_at;
    }

    public int getRating_star() {
        return rating_star;
    }

    public void setRating_star(int rating_star) {
        this.rating_star = rating_star;
    }

    public String getRating_text() {
        return rating_text;
    }

    public void setRating_text(String rating_text) {
        this.rating_text = rating_text;
    }

    public String getTime_spent_desc() {
        return time_spent_desc;
    }

    public void setTime_spent_desc(String time_spent_desc) {
        this.time_spent_desc = time_spent_desc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<?> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<?> highlights) {
        this.highlights = highlights;
    }

    public List<ItemRatingListBean> getItem_rating_list() {
        return item_rating_list;
    }

    public void setItem_rating_list(List<ItemRatingListBean> item_rating_list) {
        this.item_rating_list = item_rating_list;
    }

    public static class ItemRatingListBean {
        /**
         * food_id : 749196394
         * image_hash :
         * rate_name : 奥尔良卤肉双拼便当
         * rating_star : 5
         * rating_text :
         */

        private int food_id;
        private String image_hash;
        private String rate_name;
        private int rating_star;
        private String rating_text;

        public int getFood_id() {
            return food_id;
        }

        public void setFood_id(int food_id) {
            this.food_id = food_id;
        }

        public String getImage_hash() {
            return image_hash;
        }

        public void setImage_hash(String image_hash) {
            this.image_hash = image_hash;
        }

        public String getRate_name() {
            return rate_name;
        }

        public void setRate_name(String rate_name) {
            this.rate_name = rate_name;
        }

        public int getRating_star() {
            return rating_star;
        }

        public void setRating_star(int rating_star) {
            this.rating_star = rating_star;
        }

        public String getRating_text() {
            return rating_text;
        }

        public void setRating_text(String rating_text) {
            this.rating_text = rating_text;
        }
    }
}
