package com.rubick.cac.android.contact.ui.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/3/20.
 */
public class CACCardAnimAllInOneContainer extends RelativeLayout {

    protected final float ORIGN_SCALE = 0.25f;
    protected final float COMEOUT_SCALE = 1.f;

    protected final float ORIGN_TRANS_RATIO = 0.f;
    protected final float COMEOUT_RATIO = .0f;

    protected final float ORIGN_ROTATE = 75f;
    protected final float COMEOUT_ROTATE = 0.f;

    protected final long DURATION = 1000L;

    protected final Interpolator ANIM_INC = new LinearInterpolator();
    protected final Camera mCamera = new Camera();
    protected final Matrix mTempMatrix = new Matrix();
    protected float mScaleX = 0.5f;
    protected float mScaleY = 0.5f;
    protected float mRotateX = 75f;
    protected float mTransY = 0;

    protected boolean mSwitchFlag = false;
    protected boolean flag;

    public CACCardAnimAllInOneContainer(Context context) {
        this(context, null);
    }

    public CACCardAnimAllInOneContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CACCardAnimAllInOneContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCACCardContainer();
    }

    protected void initCACCardContainer() {
        resetValues();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (!mSwitchFlag) {
            super.dispatchDraw(canvas);
            return;
        }
        float containerWidth = getContainerWidth();
        float containerHeight = getContainerHeight();

        int savedLayer = canvas.save();
        final Camera camera = mCamera;
        final Matrix m = mTempMatrix;
        camera.save();
        camera.rotateX(mRotateX);
        camera.rotateY(0);
        camera.rotateZ(-0);
        camera.getMatrix(m);
        camera.restore();
        m.preTranslate(-containerWidth / 2f, -containerHeight / 2f);
        m.postTranslate(containerWidth / 2f, containerHeight / 2f);

        m.postTranslate(0, mTransY);
        canvas.scale(mScaleX, mScaleY, containerWidth / 2f, containerHeight / 2f);
        canvas.concat(m);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(savedLayer);
    }

    protected int getContainerWidth() {
        return getWidth();
    }

    protected int getContainerHeight() {
        return getHeight();
    }


    protected int getContentWidth() {
        return getChildCount() > 0 ? getChildAt(0).getWidth() : 0;
    }

    protected int getContentHeight() {
        return getChildCount() > 0 ? getChildAt(0).getHeight() : 0;
    }

    public void dump() {
        mSwitchFlag = true;
        flag = !flag;

        resetValues();
        invalidate();

        mComeOutTermAllInOneAnim.setDuration(DURATION);
        mComeOutTermAllInOneAnim.setStartOffset(100L);
        mComeOutTermAllInOneAnim.setInterpolator(ANIM_INC);
        startAnimation(mComeOutTermAllInOneAnim);
    }

    protected void resetValues() {
        mScaleX = ORIGN_SCALE;
        mScaleY = ORIGN_SCALE;
        mRotateX = ORIGN_ROTATE;
        mTransY = ORIGN_TRANS_RATIO * getContentHeight();
    }

    protected Animation mComeOutTermAllInOneAnim = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float deltaScale = COMEOUT_SCALE - ORIGN_SCALE;
            mScaleX = ORIGN_SCALE + (deltaScale * interpolatedTime);
            mScaleY = ORIGN_SCALE + (deltaScale * interpolatedTime);

            // 根据scroll的值计算Y轴偏移量
            final float contentHeight = getContentHeight();
            mTransY = ORIGN_TRANS_RATIO * contentHeight + ((contentHeight * COMEOUT_RATIO) * interpolatedTime);

            final float deltaRotate = COMEOUT_ROTATE - ORIGN_ROTATE;
            mRotateX = ORIGN_ROTATE + deltaRotate * interpolatedTime;
            invalidate();
        }
    };

}
