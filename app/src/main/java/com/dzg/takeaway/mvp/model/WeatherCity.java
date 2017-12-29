package com.dzg.takeaway.mvp.model;

import java.util.List;

/**
 * Created by dengzhouguang on 2017/12/2.
 */

public class WeatherCity {

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * id : WTMKQ069CCJ7
         * name : 杭州
         * country : CN
         * path : 杭州,杭州,浙江,中国
         * timezone : Asia/Shanghai
         * timezone_offset : +08:00
         */

        private String id;
        private String name;
        private String country;
        private String path;
        private String timezone;
        private String timezone_offset;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public String getTimezone_offset() {
            return timezone_offset;
        }

        public void setTimezone_offset(String timezone_offset) {
            this.timezone_offset = timezone_offset;
        }
    }
}
