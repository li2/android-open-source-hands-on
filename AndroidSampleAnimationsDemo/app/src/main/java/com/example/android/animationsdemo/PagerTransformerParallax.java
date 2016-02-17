package com.example.android.animationsdemo;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by weiyi on 2/17/16.
 */
public class PagerTransformerParallax implements ViewPager.PageTransformer {

    private int id;

    public PagerTransformerParallax(int id) {
        this.id = id;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void transformPage(View view, float position) {
        View backgroundView = view.findViewById(id);
        if (backgroundView != null && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB ) {
            if (position > -1 && position < 1) {
                float width = view.getWidth();
                backgroundView.setTranslationX(-width * position * 0.5f);
            }
        }
    }
}
