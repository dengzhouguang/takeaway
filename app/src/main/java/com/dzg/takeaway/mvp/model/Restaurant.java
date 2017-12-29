package com.dzg.takeaway.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/29.
 */

public class Restaurant implements Serializable{

    /**
     * activities : [{"attribute":"6.0","description":"新用户下单立减6元","icon_color":"70bc46","icon_name":"首","id":604411121,"is_exclusive_with_food_activity":true,"name":"新用户立减(不与其他活动共享)","tips":"新用户下单立减6元","type":103},{"attribute":"{\"22\": {\"1\": 2.0, \"0\": 0.0}}","description":"满22减2","icon_color":"f07373","icon_name":"减","id":216811562,"is_exclusive_with_food_activity":true,"name":"满减活动","tips":"满22减2","type":102},{"description":"特价活动(限10:00-22:10)","icon_color":"f07373","icon_name":"特","id":216895346,"name":"特价活动","tips":"特价活动"},{"description":"折扣活动(限10:00-22:00)","icon_color":"f07373","icon_name":"特","id":216803954,"name":"折扣活动","tips":"折扣活动"},{"description":"特价活动(限10:00-22:10)","icon_color":"f1884f","icon_name":"特","id":216801730,"name":"特价活动","tips":"特价活动"}]
     * address : 湛江市麻章区湖光镇湖光市场一楼烧腊行8号
     * authentic_id : 8361999713863853
     * description : 女生暂不送上楼哦。本店会全力尽快送餐，敬请砸单.来吧团灭全鸡
     * distance : 919
     * favored : false
     * float_delivery_fee : 0
     * float_minimum_order_amount : 11
     * id : 155127853
     * image_path : 813e7691c639241e850803bc456128bcjpeg
     * is_new : false
     * is_premium : false
     * is_stock_empty : 0
     * is_valid : 1
     * latitude : 21.144631
     * longitude : 110.3012
     * max_applied_quantity_per_order : -1
     * name : 品克炸鸡
     * next_business_time :
     * only_use_poi : false
     * opening_hours : ["10:00/13:50","15:50/23:00"]
     * order_lead_time : 49
     * phone : 13824809737
     * piecewise_agent_fee : {"description":"免配送费","extra_fee":0,"is_extra":false,"rules":[{"fee":0,"price":11}],"tips":"免配送费"}
     * promotion_info : 我能抵抗一切.除了鸡的诱惑【商家配送】【瓶装可乐】
     * rating : 4.3
     * rating_count : 0
     * recent_order_num : 2083
     * recommend : {"is_ad":false,"reason":""}
     * regular_customer_count : 0
     * scheme : eleme://catering?restaurant_id=155127853
     * status : 1
     * supports : []
     * type : 0
     * bidding : {"core":{"index":4,"target":{"restaurantId":858998,"weight":40,"probability":0.08179999887943268},"come_from":1,"next":{"restaurantId":1508650,"weight":20,"probability":0.09059999883174896}}}
     */

    private String address;
    private long authentic_id;
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "address='" + address + '\'' +
                ", authentic_id=" + authentic_id +
                ", description='" + description + '\'' +
                ", distance=" + distance +
                ", favored=" + favored +
                ", float_delivery_fee=" + float_delivery_fee +
                ", float_minimum_order_amount=" + float_minimum_order_amount +
                ", id=" + id +
                ", image_path='" + image_path + '\'' +
                ", is_new=" + is_new +
                ", is_premium=" + is_premium +
                ", is_stock_empty=" + is_stock_empty +
                ", is_valid=" + is_valid +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", max_applied_quantity_per_order=" + max_applied_quantity_per_order +
                ", name='" + name + '\'' +
                ", next_business_time='" + next_business_time + '\'' +
                ", only_use_poi=" + only_use_poi +
                ", order_lead_time=" + order_lead_time +
                ", phone='" + phone + '\'' +
                ", piecewise_agent_fee=" + piecewise_agent_fee +
                ", promotion_info='" + promotion_info + '\'' +
                ", rating=" + rating +
                ", rating_count=" + rating_count +
                ", recent_order_num=" + recent_order_num +
                ", recommend=" + recommend +
                ", regular_customer_count=" + regular_customer_count +
                ", scheme='" + scheme + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", bidding='" + bidding + '\'' +
                ", activities=" + activities +
                ", opening_hours=" + opening_hours +
                ", supports=" + supports +
                '}';
    }

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
    private int status;
    private int type;
    private String bidding;
    private List<ActivitiesBean> activities;
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

    public String getBidding() {
        return bidding;
    }

    public void setBidding(String bidding) {
        this.bidding = bidding;
    }

    public List<ActivitiesBean> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivitiesBean> activities) {
        this.activities = activities;
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

    public static class PiecewiseAgentFeeBean implements Serializable{
        /**
         * description : 免配送费
         * extra_fee : 0
         * is_extra : false
         * rules : [{"fee":0,"price":11}]
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
             * price : 11
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

            public void setPrice(int price) {
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

    public static class ActivitiesBean implements Serializable{
        /**
         * attribute : 6.0
         * description : 新用户下单立减6元
         * icon_color : 70bc46
         * icon_name : 首
         * id : 604411121
         * is_exclusive_with_food_activity : true
         * name : 新用户立减(不与其他活动共享)
         * tips : 新用户下单立减6元
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
}
