package com.rubick.cac.android.contact.ui.widget;

import android.view.animation.Interpolator;

/**
 * somthing
 *
 * <p/>
 *
 * Created by Yin Yong on 2015/3/22 0022.
 */
public class DecelerateAccelerateInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        float t = (input - 0.5f);
        return 4f * t * t* t + 0.5f;
    }
}
