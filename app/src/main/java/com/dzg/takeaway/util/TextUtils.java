package com.dzg.takeaway.util;

import com.dzg.takeaway.mvp.model.RestaurantDetail;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by dengzhouguang on 2017/12/1.
 */

public class TextUtils {
    public static String pre = "http://fuss10.elemecdn.com/";
    public static String end = "?imageMogr2/thumbnail/70x70/format/webp/quality/85";
    public static String large_end = "?imageMogr2/thumbnail/360x360/format/webp/quality/85";
    public static String medium_end = "?imageMogr2/thumbnail/160x160/format/webp/quality/85";
    public static String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(new Date(time));
    }

    public static String formatDistance(int distance) {
        if (distance < 1000)
            return distance + "m";
        else
            return new DecimalFormat("######0.0").format(distance / (double) 1000) + "km";
    }

    public static String formatDec(double d){
        return new DecimalFormat("######0.0").format(d);
    }

    public static String formatRating(double rating){
            return rating+"";
    }

    public static String formatNum(double num){
        return TextUtils.formatDouble(num)+"";
    }

    public static String formatSales(int sales){
        return "月售" + sales + "单";
    }

    public static String formatDistanceAndTime(int distance,int time){
        return TextUtils.formatDistance(distance) + "  |  " + time + "分钟";
    }

    public static String formatMinMoneyToDeDelivery(double money){
    return "￥"+TextUtils.formatDouble(money)+"起送";
    }

    public static String formatHigherNearby(double per){
        per=Double.valueOf(new DecimalFormat("######0.0").format(per*100));
        return "高于周边商家"+formatDouble(per)+"%";
    }

    public static String formatDeliveryTime(int time){
        return "约" + time + "分钟";
    }

    public static String formatSum(double rating,int sales,int distance){
        return "评价" + rating + "|月售" + sales + "单|" + formatDistance(distance);
    }

    public static String formatMoney(double money){
        return "￥"+TextUtils.formatDouble(money);
    }

    public static String formatMoney2(double money){
        return TextUtils.formatDouble(money)+"元";
    }

    public static String formatRelative(int size){
        return "查看其他"+size+"个相关商品";
    }

    public static String formatDelivery(double delivery_min,double delivery_money){
        String des="￥"+TextUtils.formatDouble(delivery_min)+"起送 | ";
        if (delivery_money==0)
            des=des+"免配送费";
        else
            des=des+"配送费￥"+TextUtils.formatDouble(delivery_money);
        return des;
    }

    public static String formatAll(double rating,int sales,String dev,int time,int distance){
        return "评分"+rating+"·月售"+sales+"单·"+dev +" 约"+time+"分钟·距离"+formatDistance(distance);
    }

    public static String formatFavourable(int size){
        return size+"个活动";
    }

    public static String formatSalesAndPercentage(int sales,double percentage){
        return "月售"+sales+"份  好评率"+percentage+"%";
    }

    public static String formatHowMoney(double money){
        return "还差￥"+TextUtils.formatDouble(money)+"起送";
    }

    public static String formatHtml(String name,String evaluate){
        /*"<h1 style=\"color:blue; text-align:center\">This is a header</h1>\n" +
                "<p style=\"color:red\">This is a paragraph.</p>"*/
        return "<font color=\"#0088cc\">"+name+"</font>"+"<font scolor=\"#363636\">"+evaluate+":</font>";
    }

    //    http://fuss10.elemecdn.com/3/4e/18ee531652517eee31eb92555ccc1jpeg.jpeg?imageMogr2/thumbnail/70x70/format/webp/quality/85
    //    03511f89c92c5e17dd0aa4b0384c9959jpeg
    public static String formatImageUrl(String imagePath) {
        String path1 = imagePath.substring(0, 1);
        String path2 = imagePath.substring(1, 3);
        String path3 = imagePath.substring(3, imagePath.length());
        String fileSuffix = "jpeg";
        if (path3.endsWith("jpeg"))
            fileSuffix = "jpeg";
        else if (path3.endsWith("png"))
            fileSuffix = "png";
        return pre + path1 + "/" + path2 + "/" + path3 + "." + fileSuffix + end;
    }


    public static String formatLagerImageUrl(String imagePath) {
        String path1 = imagePath.substring(0, 1);
        String path2 = imagePath.substring(1, 3);
        String path3 = imagePath.substring(3, imagePath.length());
        String fileSuffix = "jpeg";
        if (path3.endsWith("jpeg"))
            fileSuffix = "jpeg";
        else if (path3.endsWith("png"))
            fileSuffix = "png";
        return pre + path1 + "/" + path2 + "/" + path3 + "." + fileSuffix + large_end;
    }

    public static String formatMediumImageUrl(String imagePath) {
        String path1 = imagePath.substring(0, 1);
        String path2 = imagePath.substring(1, 3);
        String path3 = imagePath.substring(3, imagePath.length());
        String fileSuffix = "jpeg";
        if (path3.endsWith("jpeg"))
            fileSuffix = "jpeg";
        else if (path3.endsWith("png"))
            fileSuffix = "png";
        return pre + path1 + "/" + path2 + "/" + path3 + "." + fileSuffix + medium_end;
    }

    public static String formatDouble(double d){
        if (Math.round(d) - d == 0) {
            return  String.valueOf((long) d);
        }else
            return String.valueOf(d);
    }

    public static String formatTime(int time) {
        return time +"分钟";
    }

    public static String formatCategory(List<RestaurantDetail.FlavorsBean> flavors) {
        StringBuilder stringBuilder=new StringBuilder();
        for (RestaurantDetail.FlavorsBean bean:flavors){
            stringBuilder.append(bean.getName()+"/");
        }
        return stringBuilder.toString().substring(0,stringBuilder.length()-1);
    }

    public static String formatOpenHours(List<String> opening_hours) {
        StringBuilder stringBuilder=new StringBuilder();
        for (String s : opening_hours) {
            stringBuilder.append(s+",");
        }
        return stringBuilder.toString().substring(0,stringBuilder.length()-1);
    }
}
