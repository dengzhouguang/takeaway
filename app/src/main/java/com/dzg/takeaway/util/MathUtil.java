package com.dzg.takeaway.util;

/**
 * Created by dengzhouguang on 2017/12/27.
 */

public class MathUtil {
    public static float parseStar(float f){
        float num=f-(int)f;
        if (num>0){
            return (float) ((int)f+0.19+num*0.62);
        }
        return f;
    }
}
