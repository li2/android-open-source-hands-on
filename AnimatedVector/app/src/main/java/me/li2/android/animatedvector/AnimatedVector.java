package me.li2.android.animatedvector;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class AnimatedVector extends AppCompatActivity {

    private ImageView mAddRemoveImage;
    private AnimatedVectorDrawable mAddToRemoveDrawable;
    private AnimatedVectorDrawable mRemoveToAddDrawable;
    private boolean mIsAddState;

    private static final int ANSWER_HIDE_DELAY = 500;
    private CheckableFab mCheckableFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_vector);

        mAddRemoveImage = (ImageView) findViewById(R.id.image_add_remove);
        mAddRemoveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatedVectorDrawable drawable =
                        mIsAddState ? mRemoveToAddDrawable : mAddToRemoveDrawable;
                mAddRemoveImage.setImageDrawable(drawable);
                drawable.start();
                mIsAddState = !mIsAddState;
            }
        });

        mAddToRemoveDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_add_to_remove);
        mRemoveToAddDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_remove_to_add);


        mCheckableFab = (CheckableFab) findViewById(R.id.checkable_fab);
        mCheckableFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjustFab(mAnswerIsRight);
                mAnswerIsRight = !mAnswerIsRight;
            }
        });

        resetFab();
    }

    private boolean mAnswerIsRight = false;

    private void resetFab() {
        mCheckableFab.show();
        final int backgroundColor = ContextCompat.getColor(AnimatedVector.this, R.color.white);
        mCheckableFab.setChecked(true);
        mCheckableFab.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
    }

    @SuppressLint("NewApi")
    private void adjustFab(boolean answerCorrect) {
        final int backgroundColor = ContextCompat.getColor(AnimatedVector.this,
                answerCorrect ? android.R.color.holo_green_dark : android.R.color.holo_red_light);

        mCheckableFab.setChecked(answerCorrect);
        mCheckableFab.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
        mHandler.postDelayed(mHideFabRunnable, ANSWER_HIDE_DELAY);
    }

    private Handler mHandler = new Handler();

    private Runnable mHideFabRunnable = new Runnable() {
        @Override
        public void run() {
            mCheckableFab.hide();
            mHandler.postDelayed(mResetFabRunnable, ANSWER_HIDE_DELAY);
        }
    };

    private Runnable mResetFabRunnable = new Runnable() {
        @Override
        public void run() {
            resetFab();
        }
    };
}
