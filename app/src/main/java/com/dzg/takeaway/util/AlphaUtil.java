package com.dzg.takeaway.util;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by dengzhouguang on 2017/12/13.
 */

public class AlphaUtil {
    public static void backgroundAlpha(Activity activity,float bgAlpha){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }
}
