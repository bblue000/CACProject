package com.rubick.cac.android.contact.ui.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

/**
 * <p/>
 *
 * Created by Yin Yong on 2015/3/20.
 */
public class CACCardContainer extends RelativeLayout {

    protected final float ORIGN_SCALE = 0.25f;
    protected final float COMEOUT_TERM_1_SCALE = 0.75f;
    protected final float COMEOUT_TERM_2_SCALE = 1.25f;//1.25f 2f - 0.618f
    protected final float COMEOUT_TERM_3_SCALE = 1.f;

    protected final float ORIGN_TRANS_RATIO = 0.f;
    protected final float COMEOUT_TERM_1_TRANS_RATIO = .25f;
    protected final float COMEOUT_TERM_2_TRANS_RATIO = .5f;

    protected final float ORIGN_ROTATE = 75f;
    protected final float COMEOUT_TERM_1_ROTATE = 75f;
    protected final float COMEOUT_TERM_2_ROTATE = 0.f;

    protected final long DUR_TERM_1 = 600L;//600
    protected final long DUR_TERM_2 = 600L;//1000
    protected final long DUR_TERM_3 = 300L;//600

    protected final Interpolator ANIM_INC_TERM_1 = new AccelerateInterpolator(1.5f); //new AccelerateInterpolator(1.5f);
    protected final Interpolator ANIM_INC_TERM_2 = new DecelerateInterpolator(2f - 0.618f);//new DecelerateInterpolator(2.5f);
    protected final Interpolator ANIM_INC_TERM_3 = new OvershootInterpolator(2.5f);//new OvershootInterpolator(5.f);
//            new Interpolator() {
//        private Interpolator mInterpolator = new OvershootInterpolator(5f);
//        @Override
//        public float getInterpolation(float input) {
//            final float factorDec = 1.f;
//            final float maxDec = .4f;
//
//            final float maxOverShoot = 1.f;
//            final float tOverShoot = input - maxOverShoot;
//            final float tension = 2.0f;
//            return input < maxDec ?
//                    (factorDec == 1.f ?
//                        (float)(maxDec - (maxDec - input) * (maxDec - input))
//                        :
//                        (float)(maxDec - Math.pow((maxDec - input), 2 * factorDec))
//                    )
//                    :
//                    tOverShoot * tOverShoot * ((tension + 1) * tOverShoot + tension) + 1.0f;
//        }
//    };
    protected final Camera mCamera = new Camera();
    protected final Matrix mTempMatrix = new Matrix();
    protected float mScaleX = 0.5f;
    protected float mScaleY = 0.5f;
    protected float mRotateX = 75f;
    protected float mTransY = 0;

    protected boolean mSwitchFlag = false;

    public CACCardContainer(Context context) {
        this(context, null);
    }

    public CACCardContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CACCardContainer(Context context, AttributeSet attrs, int defStyleAttr) {
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
//        if (!mSwitchFlag) {
//            super.dispatchDraw(canvas);
//            return;
//        }
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

        resetValues();
        invalidate();

        mComeOutTerm1Anim.setDuration(DUR_TERM_1);
        mComeOutTerm1Anim.setStartOffset(500L);
        mComeOutTerm1Anim.setAnimationListener(mComeOutTerm1AnimListener);
        mComeOutTerm1Anim.setInterpolator(ANIM_INC_TERM_1);
        startAnimation(mComeOutTerm1Anim);

        getParent().requestDisallowInterceptTouchEvent(true);
    }

    public void resetValues() {
        clearAnimation();
        mScaleX = ORIGN_SCALE;
        mScaleY = ORIGN_SCALE;
        mRotateX = ORIGN_ROTATE;
        mTransY = ORIGN_TRANS_RATIO * getContentHeight();
    }

    protected Animation mComeOutTermAllInOneAnim = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float deltaScale = COMEOUT_TERM_3_SCALE - ORIGN_SCALE;
            mScaleX = ORIGN_SCALE + (deltaScale * interpolatedTime);
            mScaleY = ORIGN_SCALE + (deltaScale * interpolatedTime);

            // 根据scroll的值计算Y轴偏移量
            final float contentHeight = getContentHeight();
            mTransY = ORIGN_TRANS_RATIO * contentHeight + ((contentHeight * ORIGN_TRANS_RATIO) * interpolatedTime);

            final float deltaRotate = COMEOUT_TERM_2_ROTATE - ORIGN_ROTATE;
            mRotateX = ORIGN_ROTATE + deltaRotate * interpolatedTime;
            invalidate();
        }
    };


    protected Animation mComeOutTerm1Anim = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float deltaScale = COMEOUT_TERM_1_SCALE - ORIGN_SCALE;
            mScaleX = ORIGN_SCALE + (deltaScale * interpolatedTime);
            mScaleY = ORIGN_SCALE + (deltaScale * interpolatedTime);

            // 根据scroll的值计算Y轴偏移量
            final float contentHeight = getContentHeight();
            mTransY = ORIGN_TRANS_RATIO * contentHeight + ((contentHeight * COMEOUT_TERM_1_TRANS_RATIO) * interpolatedTime);

            final float deltaRotate = COMEOUT_TERM_1_ROTATE - ORIGN_ROTATE;
            mRotateX = ORIGN_ROTATE + deltaRotate * interpolatedTime;
            invalidate();
        }
    };

    protected Animation.AnimationListener mComeOutTerm1AnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            animateTerm2();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    protected void animateTerm2() {
        mComeOutTerm2Anim.setDuration(DUR_TERM_2);
        mComeOutTerm2Anim.setAnimationListener(mComeOutTerm2AnimListener);
        mComeOutTerm2Anim.setInterpolator(ANIM_INC_TERM_2);
        startAnimation(mComeOutTerm2Anim);
    }

    protected Animation mComeOutTerm2Anim = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float deltaScale = COMEOUT_TERM_2_SCALE - COMEOUT_TERM_1_SCALE;
            mScaleX = COMEOUT_TERM_1_SCALE + (deltaScale * interpolatedTime);
            mScaleY = COMEOUT_TERM_1_SCALE + (deltaScale * interpolatedTime);

            // 根据scroll的值计算Y轴偏移量
            float contentHeight = getChildAt(0).getHeight();
            mTransY = (contentHeight * COMEOUT_TERM_1_TRANS_RATIO) + ((contentHeight * COMEOUT_TERM_2_TRANS_RATIO) * interpolatedTime);

            final float deltaRotate = COMEOUT_TERM_2_ROTATE - COMEOUT_TERM_1_ROTATE;
            mRotateX = COMEOUT_TERM_1_ROTATE + deltaRotate * interpolatedTime;
            invalidate();

            if (interpolatedTime == 1.f) {
                Log.e("yytest", "111");
                clearAnimation();
            }
        }
    };

    protected Animation.AnimationListener mComeOutTerm2AnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            animateTerm3();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    protected boolean mAnimTerm3Flag = false;
    protected void animateTerm3() {
        mAnimTerm3Flag = false;

        mComeOutTerm3Anim.setDuration(DUR_TERM_3);
        mComeOutTerm3Anim.setAnimationListener(mComeOutTerm3AnimListener);
        mComeOutTerm3Anim.setInterpolator(ANIM_INC_TERM_3);
        startAnimation(mComeOutTerm3Anim);
    }

    protected Animation mComeOutTerm3Anim = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float deltaScale = COMEOUT_TERM_3_SCALE - COMEOUT_TERM_2_SCALE;
            mScaleX = COMEOUT_TERM_2_SCALE + (deltaScale * interpolatedTime);
            mScaleY = COMEOUT_TERM_2_SCALE + (deltaScale * interpolatedTime);

            // 根据scroll的值计算Y轴偏移量
//            float contentHeight = getChildAt(0).getHeight();
//            mTransY = (contentHeight * COMEOUT_TERM_1_TRANS_RATIO) + ((contentHeight * COMEOUT_TERM_2_TRANS_RATIO) * interpolatedTime);
//
//            final float deltaRotate = COMEOUT_TERM_2_ROTATE - COMEOUT_TERM_1_ROTATE;
//            mRotateX = COMEOUT_TERM_1_ROTATE + deltaRotate * interpolatedTime;
            invalidate();
        }
    };

    protected Animation.AnimationListener mComeOutTerm3AnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            mAnimTerm3Flag = true;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
