package com.dzg.takeaway.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dzg.takeaway.R;

/**
 * Created by dengzhouguang on 2017/12/14.
 */

public class Divider extends View {
    public Divider(Context context) {
        super(context);
    }

    public Divider(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Divider(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        int width = getWidth();
        paint.setStrokeWidth(1);
        // 画左端的白线，假设两端留白长度是30
        paint.setColor(getResources().getColor(R.color.color_cc));
        for (int i=0;i<width;){
            canvas.drawLine(i, 0,i+2 , 1, paint);
            i=i+4;
        }
    }
}
