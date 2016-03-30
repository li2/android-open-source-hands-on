package com.example.android.animationsdemo;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by weiyi on 2/17/16.
 */
public class PagerTransformerOverlap implements ViewPager.PageTransformer {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void transformPage(View view, float position) {
        if (view != null && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB ) {
            if (position > -1 && position < 1) {
                float width = view.getWidth();
                if (position <= 0) {
                    view.setTranslationX(-width * position * 0.5f);
                }
            }
        }
    }
}
