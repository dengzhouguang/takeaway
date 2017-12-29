package com.dzg.takeaway.mvp.model;

import java.util.List;

/**
 * Created by dengzhouguang on 2017/12/4.
 */

public class SearchRestaurant {
    private FilterBean filter;
    private String rank_id;
    private int search_type;
    private List<String> highlights;
    private List<RestaurantWithFoodsBean> restaurant_with_foods;

    public FilterBean getFilter() {
        return filter;
    }

    public void setFilter(FilterBean filter) {
        this.filter = filter;
    }

    public String getRank_id() {
        return rank_id;
    }

    public void setRank_id(String rank_id) {
        this.rank_id = rank_id;
    }

    public int getSearch_type() {
        return search_type;
    }

    public void setSearch_type(int search_type) {
        this.search_type = search_type;
    }

    public List<String> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<String> highlights) {
        this.highlights = highlights;
    }

    public List<RestaurantWithFoodsBean> getRestaurant_with_foods() {
        return restaurant_with_foods;
    }

    public void setRestaurant_with_foods(List<RestaurantWithFoodsBean> restaurant_with_foods) {
        this.restaurant_with_foods = restaurant_with_foods;
    }

    public static class FilterBean {
        private List<MultiBean> multi;
        private List<SingleBean> single;

        public List<MultiBean> getMulti() {
            return multi;
        }

        public void setMulti(List<MultiBean> multi) {
            this.multi = multi;
        }

        public List<SingleBean> getSingle() {
            return single;
        }

        public void setSingle(List<SingleBean> single) {
            this.single = single;
        }

        public static class MultiBean {
            /**
             * key : delivery_mode
             * name : 蜂鸟专送
             * values : ["1"]
             */

            private String key;
            private String name;
            private List<String> values;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getValues() {
                return values;
            }

            public void setValues(List<String> values) {
                this.values = values;
            }
        }

        public static class SingleBean {
            /**
             * key : restaurant_category_ids
             * name : 汉堡
             * values : ["212"]
             */

            private String key;
            private String name;
            private List<String> values;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getValues() {
                return values;
            }

            public void setValues(List<String> values) {
                this.values = values;
            }
        }
    }

    public static class RestaurantWithFoodsBean {
        private RestaurantBean restaurant;
        private List<FoodsBean> foods;

        public RestaurantBean getRestaurant() {
            return restaurant;
        }

        public void setRestaurant(RestaurantBean restaurant) {
            this.restaurant = restaurant;
        }

        public List<FoodsBean> getFoods() {
            return foods;
        }

        public void setFoods(List<FoodsBean> foods) {
            this.foods = foods;
        }

        public static class RestaurantBean {
            /**
             * address : 湛江市霞山区社坛路23号澳华花园一层07号商铺之一
             * authentic_id : 2372022499905407
             * delivery_mode : {"border":"","color":"2395FF","gradient":{"rgb_from":"00AAFF","rgb_to":"0085FF"},"id":1,"is_solid":true,"text":"蜂鸟专送","text_color":"FFFFFF"}
             * description : 本店配料标准薯条配番茄酱一包，手扒鸡，脆皮鸡配胡椒粉二包，超出部分请到配料区购买，给您带来不便，敬请见谅
             * distance : 2790
             * favored : false
             * float_delivery_fee : 5.5
             * float_minimum_order_amount : 15
             * id : 153479607
             * image_path : fb7a8d6c61b6f26b0d9dd6c869a22cd9jpeg
             * is_new : false
             * is_premium : false
             * is_stock_empty : 0
             * is_valid : 1
             * latitude : 21.195023
             * longitude : 110.390569
             * max_applied_quantity_per_order : -1
             * name : 享客炸鸡
             * next_business_time :
             * only_use_poi : false
             * opening_hours : ["00:00/01:45","10:00/23:55"]
             * order_lead_time : 25
             * phone : 0759-2226627
             * piecewise_agent_fee : {"description":"配送费¥5.5","extra_fee":2.5,"is_extra":true,"rules":[{"fee":5.5,"price":15}],"tips":"配送费¥5.5"}
             * promotion_info : 图片仅供参考，产品以实物为准，给您带来不便，敬请见谅
             * rating : 4.6
             * rating_count : 0
             * recent_order_num : 297
             * recommend : {"is_ad":false,"reason":""}
             * regular_customer_count : 0
             * scheme : eleme://catering?restaurant_id=153479607
             * status : 1
             * supports : [{"description":"已加入\u201c外卖保\u201d计划，食品安全有保障","icon_color":"999999","icon_name":"保","id":7,"name":"外卖保"}]
             * type : 0
             */

            private String address;
            private long authentic_id;
            private DeliveryModeBean delivery_mode;
            private String description;
            private int distance;
            private boolean favored;
            private double float_delivery_fee;
            private int float_minimum_order_amount;
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
            private int status;
            private int type;
            private List<String> opening_hours;
            private List<SupportsBean> supports;

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

            public void setFloat_delivery_fee(double float_delivery_fee) {
                this.float_delivery_fee = float_delivery_fee;
            }

            public int getFloat_minimum_order_amount() {
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

            public List<String> getOpening_hours() {
                return opening_hours;
            }

            public void setOpening_hours(List<String> opening_hours) {
                this.opening_hours = opening_hours;
            }

            public List<SupportsBean> getSupports() {
                return supports;
            }

            public void setSupports(List<SupportsBean> supports) {
                this.supports = supports;
            }

            public static class DeliveryModeBean {
                /**
                 * border :
                 * color : 2395FF
                 * gradient : {"rgb_from":"00AAFF","rgb_to":"0085FF"}
                 * id : 1
                 * is_solid : true
                 * text : 蜂鸟专送
                 * text_color : FFFFFF
                 */

                private String border;
                private String color;
                private GradientBean gradient;
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

                public GradientBean getGradient() {
                    return gradient;
                }

                public void setGradient(GradientBean gradient) {
                    this.gradient = gradient;
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

                public static class GradientBean {
                    /**
                     * rgb_from : 00AAFF
                     * rgb_to : 0085FF
                     */

                    private String rgb_from;
                    private String rgb_to;

                    public String getRgb_from() {
                        return rgb_from;
                    }

                    public void setRgb_from(String rgb_from) {
                        this.rgb_from = rgb_from;
                    }

                    public String getRgb_to() {
                        return rgb_to;
                    }

                    public void setRgb_to(String rgb_to) {
                        this.rgb_to = rgb_to;
                    }
                }
            }

            public static class PiecewiseAgentFeeBean {
                /**
                 * description : 配送费¥5.5
                 * extra_fee : 2.5
                 * is_extra : true
                 * rules : [{"fee":5.5,"price":15}]
                 * tips : 配送费¥5.5
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

                public void setExtra_fee(double extra_fee) {
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

                public static class RulesBean {
                    /**
                     * fee : 5.5
                     * price : 15
                     */

                    private double fee;
                    private double price;

                    public double getFee() {
                        return fee;
                    }

                    public void setFee(double fee) {
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

            public static class RecommendBean {
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

            public static class SupportsBean {
                /**
                 * description : 已加入“外卖保”计划，食品安全有保障
                 * icon_color : 999999
                 * icon_name : 保
                 * id : 7
                 * name : 外卖保
                 */

                private String description;
                private String icon_color;
                private String icon_name;
                private int id;
                private String name;

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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public static class FoodsBean {
            /**
             * activities : []
             * category_id : 516903485
             * food_id : 651150180
             * id : 651150180
             * image_path : 4fb89703084d19694a80c4b5415c7d2ajpeg
             * is_new : false
             * month_sales : 7
             * name : 鲜虾汉堡
             * price : 8
             * restaurant_id : 153479607
             * satisfy_rate : 100
             * scheme : eleme://catering?target_food_id=651150180&restaurant_id=153479607&category_id=516903485
             * stock : 913
             */

            private int category_id;
            private int food_id;
            private int id;
            private String image_path;
            private boolean is_new;
            private int month_sales;
            private String name;
            private double price;
            private int restaurant_id;
            private int satisfy_rate;
            private String scheme;
            private int stock;
            private List<?> activities;

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public int getFood_id() {
                return food_id;
            }

            public void setFood_id(int food_id) {
                this.food_id = food_id;
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

            public int getMonth_sales() {
                return month_sales;
            }

            public void setMonth_sales(int month_sales) {
                this.month_sales = month_sales;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getRestaurant_id() {
                return restaurant_id;
            }

            public void setRestaurant_id(int restaurant_id) {
                this.restaurant_id = restaurant_id;
            }

            public int getSatisfy_rate() {
                return satisfy_rate;
            }

            public void setSatisfy_rate(int satisfy_rate) {
                this.satisfy_rate = satisfy_rate;
            }

            public String getScheme() {
                return scheme;
            }

            public void setScheme(String scheme) {
                this.scheme = scheme;
            }

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            public List<?> getActivities() {
                return activities;
            }

            public void setActivities(List<?> activities) {
                this.activities = activities;
            }
        }
    }
    @Override
    public String toString() {
        return "SearchRestaurant{" +
                "filter=" + filter +
                ", rank_id='" + rank_id + '\'' +
                ", search_type=" + search_type +
                ", highlights=" + highlights +
                ", restaurant_with_foods=" + restaurant_with_foods +
                '}';
    }
}
