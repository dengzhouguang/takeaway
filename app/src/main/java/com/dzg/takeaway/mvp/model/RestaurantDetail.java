package com.dzg.takeaway.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengzhouguang on 2017/12/6.
 */

public class RestaurantDetail implements Serializable{

    /**
     * activities : [{"attribute":"6.0","description":"新用户下单立减6元(不与其它活动同享)","icon_color":"70bc46","icon_name":"首","id":676984777,"is_exclusive_with_food_activity":true,"name":"新用户立减(不与其他活动共享)","tips":"新用户下单立减6元(不与其它活动同享)","type":103},{"attribute":"{\"33\": {\"1\": 3.0, \"0\": 0.0}, \"22\": {\"1\": 2.0, \"0\": 0.0}}","description":"满22减2，满33减3","icon_color":"f07373","icon_name":"减","id":219472538,"is_exclusive_with_food_activity":true,"name":"满减","tips":"满22减2，满33减3","type":102},{"description":"特价优惠","icon_color":"f07373","icon_name":"特","id":219479450,"name":"特价优惠","tips":"特价优惠"}]
     * address : 广东海洋大学主校区门口正对面（麻章区农业科学研究所1号地第1门）
     * albums : [{"count":1,"cover_image_hash":"af280e938e2723fcb27168b6e5a6fea5jpeg","name":"门面","photos":[{"description":"门头照","id":8334571,"image_hash":"af280e938e2723fcb27168b6e5a6fea5jpeg","type":"FRONT"}]},{"count":1,"cover_image_hash":"6143048ef06fc61e8a75986e263eda91jpeg","name":"大堂","photos":[{"description":"店内照","id":8334572,"image_hash":"6143048ef06fc61e8a75986e263eda91jpeg","type":"HALL"}]}]
     * authentic_id : 5342083340172931
     * delivery_mode : {"border":"FFFFFF","color":"","id":2,"is_solid":true,"text":"商家配送","text_color":"FFFFFF"}
     * description :
     * distance : 955
     * favored : false
     * flavors : [{"id":237,"name":"地方小吃"},{"id":212,"name":"汉堡"}]
     * float_delivery_fee : 0
     * float_minimum_order_amount : 15
     * id : 156533931
     * image_path : 34e18ee531652517eee31eb92555ccc1jpeg
     * is_new : false
     * is_premium : false
     * is_stock_empty : 0
     * is_valid : 1
     * latitude : 21.144485
     * longitude : 110.30264
     * max_applied_quantity_per_order : 1
     * name : 佳斯顿炸鸡汉堡
     * next_business_time : 16:05
     * only_use_poi : false
     * opening_hours : ["10:00/14:10","16:05/23:00"]
     * order_lead_time : 48
     * phone : 13536404753 15119567488 694753
     * piecewise_agent_fee : {"description":"免配送费","extra_fee":0,"is_extra":false,"rules":[{"fee":0,"price":15}],"tips":"免配送费"}
     * promotion_info : 温馨提示:因近段时间学校管制特严，所有外卖不能送上楼，请保持电话畅通以便联系，谢谢！需要帮助请电13536404753（69）
     * rating : 4.4
     * rating_count : 1184
     * recent_order_num : 5809
     * recommend : {"is_ad":false,"reason":""}
     * regular_customer_count : 0
     * scheme : eleme://catering?restaurant_id=156533931
     * shop_sign : {"brand_story":"","image_hash":""}
     * status : 1
     * supports : []
     * type : 0
     */

    private String address;
    private long authentic_id;
    private DeliveryModeBean delivery_mode;
    private String description;
    private int distance;
    private boolean favored;
    private double float_delivery_fee;
    private double float_minimum_order_amount;
    private int id;
    private String image_path;
    private boolean is_new;
    private boolean is_premium;
    private int is_stock_empty;
    private int is_valid;
    private double latitude;
    private double longitude;
    private int max_applied_quantity_per_order;
    private String name;
    private String next_business_time;
    private boolean only_use_poi;
    private int order_lead_time;
    private String phone;
    private PiecewiseAgentFeeBean piecewise_agent_fee;
    private String promotion_info;
    private double rating;
    private int rating_count;
    private int recent_order_num;
    private RecommendBean recommend;
    private int regular_customer_count;
    private String scheme;
    private ShopSignBean shop_sign;
    private int status;
    private int type;
    private List<ActivitiesBean> activities;
    private List<AlbumsBean> albums;
    private List<FlavorsBean> flavors;
    private List<String> opening_hours;
    private List<?> supports;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getAuthentic_id() {
        return authentic_id;
    }

    public void setAuthentic_id(long authentic_id) {
        this.authentic_id = authentic_id;
    }

    public DeliveryModeBean getDelivery_mode() {
        return delivery_mode;
    }

    public void setDelivery_mode(DeliveryModeBean delivery_mode) {
        this.delivery_mode = delivery_mode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isFavored() {
        return favored;
    }

    public void setFavored(boolean favored) {
        this.favored = favored;
    }

    public double getFloat_delivery_fee() {
        return float_delivery_fee;
    }

    public void setFloat_delivery_fee(int float_delivery_fee) {
        this.float_delivery_fee = float_delivery_fee;
    }

    public double getFloat_minimum_order_amount() {
        return float_minimum_order_amount;
    }

    public void setFloat_minimum_order_amount(int float_minimum_order_amount) {
        this.float_minimum_order_amount = float_minimum_order_amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public boolean isIs_new() {
        return is_new;
    }

    public void setIs_new(boolean is_new) {
        this.is_new = is_new;
    }

    public boolean isIs_premium() {
        return is_premium;
    }

    public void setIs_premium(boolean is_premium) {
        this.is_premium = is_premium;
    }

    public int getIs_stock_empty() {
        return is_stock_empty;
    }

    public void setIs_stock_empty(int is_stock_empty) {
        this.is_stock_empty = is_stock_empty;
    }

    public int getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(int is_valid) {
        this.is_valid = is_valid;
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

    public int getMax_applied_quantity_per_order() {
        return max_applied_quantity_per_order;
    }

    public void setMax_applied_quantity_per_order(int max_applied_quantity_per_order) {
        this.max_applied_quantity_per_order = max_applied_quantity_per_order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNext_business_time() {
        return next_business_time;
    }

    public void setNext_business_time(String next_business_time) {
        this.next_business_time = next_business_time;
    }

    public boolean isOnly_use_poi() {
        return only_use_poi;
    }

    public void setOnly_use_poi(boolean only_use_poi) {
        this.only_use_poi = only_use_poi;
    }

    public int getOrder_lead_time() {
        return order_lead_time;
    }

    public void setOrder_lead_time(int order_lead_time) {
        this.order_lead_time = order_lead_time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PiecewiseAgentFeeBean getPiecewise_agent_fee() {
        return piecewise_agent_fee;
    }

    public void setPiecewise_agent_fee(PiecewiseAgentFeeBean piecewise_agent_fee) {
        this.piecewise_agent_fee = piecewise_agent_fee;
    }

    public String getPromotion_info() {
        return promotion_info;
    }

    public void setPromotion_info(String promotion_info) {
        this.promotion_info = promotion_info;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRating_count() {
        return rating_count;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public int getRecent_order_num() {
        return recent_order_num;
    }

    public void setRecent_order_num(int recent_order_num) {
        this.recent_order_num = recent_order_num;
    }

    public RecommendBean getRecommend() {
        return recommend;
    }

    public void setRecommend(RecommendBean recommend) {
        this.recommend = recommend;
    }

    public int getRegular_customer_count() {
        return regular_customer_count;
    }

    public void setRegular_customer_count(int regular_customer_count) {
        this.regular_customer_count = regular_customer_count;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public ShopSignBean getShop_sign() {
        return shop_sign;
    }

    public void setShop_sign(ShopSignBean shop_sign) {
        this.shop_sign = shop_sign;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<ActivitiesBean> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivitiesBean> activities) {
        this.activities = activities;
    }

    public List<AlbumsBean> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumsBean> albums) {
        this.albums = albums;
    }

    public List<FlavorsBean> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<FlavorsBean> flavors) {
        this.flavors = flavors;
    }

    public List<String> getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(List<String> opening_hours) {
        this.opening_hours = opening_hours;
    }

    public List<?> getSupports() {
        return supports;
    }

    public void setSupports(List<?> supports) {
        this.supports = supports;
    }

    public static class DeliveryModeBean implements Serializable{
        /**
         * border : FFFFFF
         * color :
         * id : 2
         * is_solid : true
         * text : 商家配送
         * text_color : FFFFFF
         */

        private String border;
        private String color;
        private int id;
        private boolean is_solid;
        private String text;
        private String text_color;

        public String getBorder() {
            return border;
        }

        public void setBorder(String border) {
            this.border = border;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIs_solid() {
            return is_solid;
        }

        public void setIs_solid(boolean is_solid) {
            this.is_solid = is_solid;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getText_color() {
            return text_color;
        }

        public void setText_color(String text_color) {
            this.text_color = text_color;
        }
    }

    public static class PiecewiseAgentFeeBean implements Serializable{
        /**
         * description : 免配送费
         * extra_fee : 0
         * is_extra : false
         * rules : [{"fee":0,"price":15}]
         * tips : 免配送费
         */

        private String description;
        private double extra_fee;
        private boolean is_extra;
        private String tips;
        private List<RulesBean> rules;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getExtra_fee() {
            return extra_fee;
        }

        public void setExtra_fee(int extra_fee) {
            this.extra_fee = extra_fee;
        }

        public boolean isIs_extra() {
            return is_extra;
        }

        public void setIs_extra(boolean is_extra) {
            this.is_extra = is_extra;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public List<RulesBean> getRules() {
            return rules;
        }

        public void setRules(List<RulesBean> rules) {
            this.rules = rules;
        }

        public static class RulesBean implements Serializable{
            /**
             * fee : 0
             * price : 15
             */

            private double fee;
            private double price;

            public double getFee() {
                return fee;
            }

            public void setFee(int fee) {
                this.fee = fee;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }
        }
    }

    public static class RecommendBean implements Serializable{
        /**
         * is_ad : false
         * reason :
         */

        private boolean is_ad;
        private String reason;

        public boolean isIs_ad() {
            return is_ad;
        }

        public void setIs_ad(boolean is_ad) {
            this.is_ad = is_ad;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    public static class ShopSignBean implements Serializable{
        /**
         * brand_story :
         * image_hash :
         */

        private String brand_story;
        private String image_hash;

        public String getBrand_story() {
            return brand_story;
        }

        public void setBrand_story(String brand_story) {
            this.brand_story = brand_story;
        }

        public String getImage_hash() {
            return image_hash;
        }

        public void setImage_hash(String image_hash) {
            this.image_hash = image_hash;
        }
    }

    public static class ActivitiesBean implements Serializable{
        /**
         * attribute : 6.0
         * description : 新用户下单立减6元(不与其它活动同享)
         * icon_color : 70bc46
         * icon_name : 首
         * id : 676984777
         * is_exclusive_with_food_activity : true
         * name : 新用户立减(不与其他活动共享)
         * tips : 新用户下单立减6元(不与其它活动同享)
         * type : 103
         */

        private String attribute;
        private String description;
        private String icon_color;
        private String icon_name;
        private int id;
        private boolean is_exclusive_with_food_activity;
        private String name;
        private String tips;
        private int type;

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon_color() {
            return icon_color;
        }

        public void setIcon_color(String icon_color) {
            this.icon_color = icon_color;
        }

        public String getIcon_name() {
            return icon_name;
        }

        public void setIcon_name(String icon_name) {
            this.icon_name = icon_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIs_exclusive_with_food_activity() {
            return is_exclusive_with_food_activity;
        }

        public void setIs_exclusive_with_food_activity(boolean is_exclusive_with_food_activity) {
            this.is_exclusive_with_food_activity = is_exclusive_with_food_activity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class AlbumsBean implements Serializable{
        /**
         * count : 1
         * cover_image_hash : af280e938e2723fcb27168b6e5a6fea5jpeg
         * name : 门面
         * photos : [{"description":"门头照","id":8334571,"image_hash":"af280e938e2723fcb27168b6e5a6fea5jpeg","type":"FRONT"}]
         */

        private int count;
        private String cover_image_hash;
        private String name;
        private List<PhotosBean> photos;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getCover_image_hash() {
            return cover_image_hash;
        }

        public void setCover_image_hash(String cover_image_hash) {
            this.cover_image_hash = cover_image_hash;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<PhotosBean> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosBean> photos) {
            this.photos = photos;
        }

        public static class PhotosBean implements Serializable{
            /**
             * description : 门头照
             * id : 8334571
             * image_hash : af280e938e2723fcb27168b6e5a6fea5jpeg
             * type : FRONT
             */

            private String description;
            private int id;
            private String image_hash;
            private String type;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage_hash() {
                return image_hash;
            }

            public void setImage_hash(String image_hash) {
                this.image_hash = image_hash;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class FlavorsBean implements Serializable{
        /**
         * id : 237
         * name : 地方小吃
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
