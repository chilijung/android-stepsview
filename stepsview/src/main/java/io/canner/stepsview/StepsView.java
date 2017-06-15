package io.canner.stepsview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class StepsView extends LinearLayout implements StepsViewIndicator.OnDrawListener {

    private StepsViewIndicator mStepsViewIndicator;
    private FrameLayout mLabelsLayout;
    private String[] mLabels;
    private int mProgressColorIndicator = Color.YELLOW;
    private int mLabelColorIndicator = Color.BLACK;
    private int mBarColorIndicator = Color.BLACK;
    private float mLabelTextSize = 20;
    private int mCompletedPosition = 0;
    private int mTotalSteps;

    public StepsView(Context context) {
        this(context, null);
    }

    public StepsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepsView(Context context, AttributeSet attrs,
                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        TypedArray arr = context.obtainStyledAttributes(attrs,
                R.styleable.StepsView);
        int numSteps = arr.getInt(R.styleable.StepsView_numOfSteps, 2);
        int completePos = arr.getInt(R.styleable.StepsView_completePosition, 0);
//        int labelsResId = arr.getResourceId(R.styleable.StepsView_labels, 0);
        int barColorResId = arr.getResourceId(R.styleable.StepsView_barColor, Color.GRAY);
        int progressColorResId = arr.getResourceId(R.styleable.StepsView_progressColor, Color.YELLOW);
        int labelColorResId = arr.getResourceId(R.styleable.StepsView_labelColor, Color.BLACK);
        int progressTextColorResId = arr.getResourceId(R.styleable.StepsView_progressTextColor, Color.WHITE);
        boolean hideProgressText = arr.getBoolean(R.styleable.StepsView_hideProgressText, false);
        float labelSize =arr.getFloat(R.styleable.StepsView_labelSize, 20);
        float progressMargin = arr.getFloat(R.styleable.StepsView_progressMargin, 100);
        float circleRadius = arr.getFloat(R.styleable.StepsView_circleRadius, 50);
        float progressStrokeWidth = arr.getFloat(R.styleable.StepsView_progressStrokeWidth, 5);

        mStepsViewIndicator.setStepTotal(numSteps);
        mTotalSteps = numSteps;
        this.setCompletedPosition(completePos);
        this.setBarColorIndicator(barColorResId);
        this.setProgressColorIndicator(progressColorResId);
        this.setLabelColorIndicator(labelColorResId);
        this.setProgressTextColor(progressTextColorResId);
        this.setHideProgressText(hideProgressText);
        this.setLabelTextSize(labelSize);
        this.setProgressMargins(progressMargin);
        this.setCircleRadius(circleRadius);
        this.setProgressStrokeWidth(progressStrokeWidth);
        arr.recycle();
        // initial draw view
        drawView();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.widget_steps_view, this);
        mStepsViewIndicator = (StepsViewIndicator) rootView.findViewById(R.id.steps_indicator_view);
        mStepsViewIndicator.setDrawListener(this);
        mLabelsLayout = (FrameLayout) rootView.findViewById(R.id.labels_container);
    }

    public String[] getLabels() {
        return mLabels;
    }

    public StepsView setLabels(String[] labels) {
        mLabels = labels;
        mStepsViewIndicator.setStepTotal(labels.length);
        mTotalSteps = labels.length;
        return this;
    }

    public int getProgressColorIndicator() {
        return mProgressColorIndicator;
    }

    public StepsView setProgressColorIndicator(int progressColorIndicator) {
        mProgressColorIndicator = progressColorIndicator;
        mStepsViewIndicator.setProgressColor(mProgressColorIndicator);
        return this;
    }

    public int getLabelColorIndicator() {
        return mLabelColorIndicator;
    }

    public StepsView setLabelColorIndicator(int labelColorIndicator) {
        mLabelColorIndicator = labelColorIndicator;
        return this;
    }

    public int getBarColorIndicator() {
        return mBarColorIndicator;
    }

    public StepsView setBarColorIndicator(int barColorIndicator) {
        mBarColorIndicator = barColorIndicator;
        mStepsViewIndicator.setBarColor(mBarColorIndicator);
        return this;
    }

    public int getCompletedPosition() {
        return mCompletedPosition;
    }

    public StepsView setCompletedPosition(int completedPosition) {
        mCompletedPosition = completedPosition;
        mStepsViewIndicator.setCompletedPosition(mCompletedPosition);
        return this;
    }

    public StepsView setLabelTextSize(float size) {
        mLabelTextSize = size;
        return this;
    }

    public StepsView setProgressStrokeWidth(float width) {
        mStepsViewIndicator.setProgressStrokeWidth(width);
        return this;
    }

    public StepsView setProgressMargins(float margin) {
        mStepsViewIndicator.setMargins(margin);
        return this;
    }

    public StepsView setCircleRadius(float radius) {
        mStepsViewIndicator.setCircleRadius(radius);
        return this;
    }

    public StepsView setProgressTextColor(int textColor) {
        mStepsViewIndicator.setProgressTextColor(textColor);
        return this;
    }

    public StepsView setHideProgressText(boolean hide) {
        mStepsViewIndicator.setHideProgressText(hide);
        return this;
    }

    public void drawView() {
        Log.v("stepsView", "draw view");
        if (mTotalSteps == 0) {
            throw new IllegalArgumentException("Total steps cannot be zero.");
        }

        if (mCompletedPosition < 0 || mCompletedPosition > mTotalSteps - 1) {
            throw new IndexOutOfBoundsException(String.format("Index : %s, Size : %s", mCompletedPosition, mLabels.length));
        }

        mStepsViewIndicator.invalidate();
    }

    @Override
    public void onReady() {
        drawLabels();
    }

    private void drawLabels() {
        List<Float> indicatorPosition = mStepsViewIndicator.getThumbContainerXPosition();

        if (mLabels != null) {
            for (int i = 0; i < mLabels.length; i++) {
                TextView textView = new TextView(getContext());
                textView.setText(mLabels[i]);
                textView.setTextColor(mLabelColorIndicator);
                textView.setTextSize(mLabelTextSize);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.measure(0, 0);
                float textWidth = textView.getMeasuredWidth();
                textView.setX(indicatorPosition.get(i) - (textWidth / 2));

                mLabelsLayout.addView(textView);
            }
        }
    }
}
