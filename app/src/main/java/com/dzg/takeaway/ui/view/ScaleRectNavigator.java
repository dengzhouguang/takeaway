package com.dzg.takeaway.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.dzg.takeaway.util.DensityUtil;

import net.lucode.hackware.magicindicator.NavigatorHelper;
import net.lucode.hackware.magicindicator.abs.IPagerNavigator;
import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder;
import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//
//
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//               佛祖保佑         永无BUG

/**
 * 类似CircleIndicator的效果
 */

public class ScaleRectNavigator extends View implements IPagerNavigator, NavigatorHelper.OnNavigatorScrollListener {
    private int mMinWidth;
    private int mMaxWidth;
    private int mNormalCircleColor = Color.LTGRAY;
    private int mSelectedCircleColor = 0xFFB4B4B4;
    private int mRectSpacing;
    private int mRectCount;
    private Context mContext;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<PointF> mRectPoints = new ArrayList<PointF>();
    private SparseArray<Float> mRectArray = new SparseArray<Float>();

    // 事件回调
    private boolean mTouchable;
    private OnCircleClickListener mCircleClickListener;
    private float mDownX;
    private float mDownY;
    private int mTouchSlop;

    private boolean mFollowTouch = true;    // 是否跟随手指滑动
    private NavigatorHelper mNavigatorHelper = new NavigatorHelper();
    private Interpolator mStartInterpolator = new LinearInterpolator();

    public ScaleRectNavigator(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMinWidth = UIUtil.dip2px(context, 3);
        mMaxWidth = UIUtil.dip2px(context, 5);
        mRectSpacing = UIUtil.dip2px(context, 8);
        mNavigatorHelper.setNavigatorScrollListener(this);
        mNavigatorHelper.setSkimOver(true);
        mContext=context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = width;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = (mRectCount - 1) * mMinWidth * 2 + mMaxWidth * 2 + (mRectCount - 1) * mRectSpacing + getPaddingLeft() + getPaddingRight();
                break;
            default:
                break;
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int result = 0;
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = height;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                result = mMaxWidth * 2 + getPaddingTop() + getPaddingBottom();
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0, j = mRectPoints.size(); i < j; i++) {
            PointF point = mRectPoints.get(i);
            float width = mRectArray.get(i, (float) mMinWidth);
            mPaint.setColor(ArgbEvaluatorHolder.eval((width - mMinWidth) / (mMaxWidth - mMinWidth), mNormalCircleColor, mSelectedCircleColor));
            canvas.drawRect(new Rect((int)(point.x- DensityUtil.dp2px(5)),(int)(getHeight()/2.0f-DensityUtil.dp2px(2)),(int)(point.x+DensityUtil.dp2px(5)),(int)(getHeight()/2.0f+DensityUtil.dp2px(2))),mPaint);
        }
    }
    private void prepareCirclePoints() {
        mRectPoints.clear();
        if (mRectCount > 0) {
            int y = Math.round(getHeight() / 2.0f);
            int centerSpacing = mMinWidth * 2 + mRectSpacing;
            int startX = mMaxWidth + getPaddingLeft();
            for (int i = 0; i < mRectCount; i++) {
                PointF pointF = new PointF(startX, y);
                mRectPoints.add(pointF);
                startX += centerSpacing;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mTouchable) {
                    mDownX = x;
                    mDownY = y;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mCircleClickListener != null) {
                    if (Math.abs(x - mDownX) <= mTouchSlop && Math.abs(y - mDownY) <= mTouchSlop) {
                        float max = Float.MAX_VALUE;
                        int index = 0;
                        for (int i = 0; i < mRectPoints.size(); i++) {
                            PointF pointF = mRectPoints.get(i);
                            float offset = Math.abs(pointF.x - x);
                            if (offset < max) {
                                max = offset;
                                index = i;
                            }
                        }
                        mCircleClickListener.onClick(index);
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mNavigatorHelper.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mNavigatorHelper.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mNavigatorHelper.onPageScrollStateChanged(state);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        prepareCirclePoints();
    }

    @Override
    public void notifyDataSetChanged() {
        prepareCirclePoints();
        invalidate();
    }

    @Override
    public void onAttachToMagicIndicator() {
    }

    @Override
    public void onDetachFromMagicIndicator() {
    }

    public void setMinRadius(int minRadius) {
        mMinWidth = minRadius;
        prepareCirclePoints();
        invalidate();
    }

    public void setMaxRadius(int maxRadius) {
        mMaxWidth = maxRadius;
        prepareCirclePoints();
        invalidate();
    }

    public void setNormalCircleColor(int normalCircleColor) {
        mNormalCircleColor = normalCircleColor;
        invalidate();
    }

    public void setSelectedCircleColor(int selectedCircleColor) {
        mSelectedCircleColor = selectedCircleColor;
        invalidate();
    }

    public void setCircleSpacing(int circleSpacing) {
        mRectSpacing = circleSpacing;
        prepareCirclePoints();
        invalidate();
    }

    public void setStartInterpolator(Interpolator startInterpolator) {
        mStartInterpolator = startInterpolator;
        if (mStartInterpolator == null) {
            mStartInterpolator = new LinearInterpolator();
        }
    }

    public void setRectCount(int count) {
        mRectCount = count;  // 此处不调用invalidate，让外部调用notifyDataSetChanged
        mNavigatorHelper.setTotalCount(mRectCount);
    }

    public void setTouchable(boolean touchable) {
        mTouchable = touchable;
    }

    public void setFollowTouch(boolean followTouch) {
        mFollowTouch = followTouch;
    }

    public void setSkimOver(boolean skimOver) {
        mNavigatorHelper.setSkimOver(skimOver);
    }

    public void setRectClickListener(OnCircleClickListener circleClickListener) {
        if (!mTouchable) {
            mTouchable = true;
        }
        mCircleClickListener = circleClickListener;
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if (mFollowTouch) {
            float radius = mMinWidth + (mMaxWidth - mMinWidth) * mStartInterpolator.getInterpolation(enterPercent);
            mRectArray.put(index, radius);
            invalidate();
        }
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        if (mFollowTouch) {
            float radius = mMaxWidth + (mMinWidth - mMaxWidth) * mStartInterpolator.getInterpolation(leavePercent);
            mRectArray.put(index, radius);
            invalidate();
        }
    }

    @Override
    public void onSelected(int index, int totalCount) {
        if (!mFollowTouch) {
            mRectArray.put(index, (float) mMaxWidth);
            invalidate();
        }
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        if (!mFollowTouch) {
            mRectArray.put(index, (float) mMinWidth);
            invalidate();
        }
    }

    public interface OnCircleClickListener {
        void onClick(int index);
    }
}
