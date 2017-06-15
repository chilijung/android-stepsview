package io.canner.stepsview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class StepsViewIndicator extends View {

    private Paint paint = new Paint();
    private Paint selectedPaint = new Paint();
    private Paint progressTextPaint = new Paint();
    private int mNumOfStep;
    private float mProgrssStrokeWidth;
    private float mLineHeight;
    private float mCircleRadius;
    private float mMargins;
    private int mProgressColor;
    private int mBarColor;
    private int mProgressTextColor;
    private boolean hideProgressText;

    private float mCenterY;
    private float mLineY;
    private List<Float> mThumbContainerXPosition = new ArrayList<>();
    private int mCompletedPosition;
    private OnDrawListener mDrawListener;

    public StepsViewIndicator(Context context) {
        this(context, null);
    }

    public StepsViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepsViewIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mLineHeight = mProgrssStrokeWidth;
    }

    public void setStepTotal(int size) {
        mNumOfStep = size;
        invalidate();
    }

    public void setDrawListener(OnDrawListener drawListener) {
        mDrawListener = drawListener;
    }

    public List<Float> getThumbContainerXPosition() {
        return mThumbContainerXPosition;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterY = 0.5f * getHeight();
        float mLeftX = mMargins;
        mLineY = mCenterY - (mLineHeight / 2);
        float mRightX = getWidth() - mMargins;
        float mDelta = (mRightX - mLeftX) / (mNumOfStep - 1);

        mThumbContainerXPosition.add(mLeftX);
        for (int i = 1; i < mNumOfStep - 1; i++) {
            mThumbContainerXPosition.add(mLeftX + (i * mDelta));
        }
        mThumbContainerXPosition.add(mRightX);
        mDrawListener.onReady();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width  = measureDimension(getWidth(), widthMeasureSpec);
        int height = measureDimension(200, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    protected int measureDimension( int defaultSize, int measureSpec ) {

        int result = defaultSize;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //1. layout给出了确定的值，比如：100dp
        //2. layout使用的是match_parent，但父控件的size已经可以确定了，比如设置的是具体的值或者match_parent
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize; //建议：result直接使用确定值
        }
        //1. layout使用的是wrap_content
        //2. layout使用的是match_parent,但父控件使用的是确定的值或者wrap_content
        else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize); //建议：result不能大于specSize
        }
        //UNSPECIFIED,没有任何限制，所以可以设置任何大小
        //多半出现在自定义的父控件的情况下，期望由自控件自行决定大小
        else {
            result = defaultSize;
        }

        return result;
    }

    public void setCompletedPosition(int position) {
        mCompletedPosition = position;
    }

    public void reset() {
        setCompletedPosition(0);
    }

    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
    }

    public void setBarColor(int barColor) {
        mBarColor = barColor;
    }

    public void setProgressTextColor(int textColor) {
        mProgressTextColor = textColor;
    }

    public void setProgressStrokeWidth(float width) {
        mProgrssStrokeWidth = width;
    }

    public void setMargins(float margin) {
        mMargins = margin;
    }

    public void setCircleRadius(float radius) {
        mCircleRadius = radius;
    }

    public void setHideProgressText(boolean hide) {
        hideProgressText = hide;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mDrawListener.onReady();
        // bar progress paint
        paint.setAntiAlias(true);
        paint.setColor(mBarColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        // progress paint
        selectedPaint.setAntiAlias(true);
        selectedPaint.setColor(mProgressColor);
        selectedPaint.setStyle(Paint.Style.STROKE);
        selectedPaint.setStrokeWidth(1);

        // progress text
        progressTextPaint.setAntiAlias(true);
        progressTextPaint.setTextSize(mCircleRadius);
        progressTextPaint.setColor(mProgressTextColor);

        paint.setStyle(Paint.Style.FILL);
        selectedPaint.setStyle(Paint.Style.FILL);

        // draw lines
        for (int i = 0; i < mThumbContainerXPosition.size() - 1; i++) {
            final float pos = mThumbContainerXPosition.get(i);
            final float pos2 = mThumbContainerXPosition.get(i + 1);
            canvas.drawRect(pos, mLineY, pos2, mLineY + mProgrssStrokeWidth,
                    (i < mCompletedPosition) ? selectedPaint : paint);
        }

        float quarterRadius = mCircleRadius / 4;
        // Draw circles
        for (int i = 0; i < mThumbContainerXPosition.size(); i++) {
            final float pos = mThumbContainerXPosition.get(i);
            canvas.drawCircle(pos, mCenterY, mCircleRadius,
                    (i <= mCompletedPosition) ? selectedPaint : paint);

            if (!hideProgressText) {
                canvas.drawText(String.valueOf(i + 1), pos - quarterRadius, mCenterY + quarterRadius, progressTextPaint);
            }
            // in current completed position color with alpha
            if (i == mCompletedPosition) {
                selectedPaint.setColor(getColorWithAlpha(mProgressColor, 0.2f));
                canvas.drawCircle(pos, mCenterY, mCircleRadius * 1.3f, selectedPaint);
            }
        }
    }

    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    public interface OnDrawListener {
        void onReady();
    }
}
