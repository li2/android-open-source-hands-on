package me.li2.android.animatedvector;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class AnimatedVector extends AppCompatActivity {

    private ImageView mAddRemoveImage;
    private AnimatedVectorDrawable mAddToRemoveDrawable;
    private AnimatedVectorDrawable mRemoveToAddDrawable;
    private boolean mIsAddState;


    private static final int ANSWER_HIDE_DELAY = 500;
    private CheckableFab mCheckableFab;
    private Button mResetFab;
    private Runnable mHideFabRunnable;
    private Handler mHandler = new Handler();

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
                Random random = new Random();
                boolean answerCorrect = (random.nextInt() % 2 == 0);
                adjustFab(answerCorrect);
            }
        });
        resetFab();

        mResetFab = (Button) findViewById(R.id.reset_fab);
        mResetFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFab();
            }
        });
    }

    private void resetFab() {
        mCheckableFab.show();
        final int backgroundColor = ContextCompat.getColor(AnimatedVector.this,
                android.R.color.holo_blue_dark);
        mCheckableFab.setChecked(true);
        mCheckableFab.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
    }

    @SuppressLint("NewApi")
    private void adjustFab(boolean answerCorrect) {
        final int backgroundColor = ContextCompat.getColor(AnimatedVector.this,
                answerCorrect ? android.R.color.holo_green_dark : android.R.color.holo_red_light);

        mCheckableFab.setChecked(answerCorrect);
        mCheckableFab.setBackgroundTintList(ColorStateList.valueOf(backgroundColor));
        mHideFabRunnable = new Runnable() {
            @Override
            public void run() {
                mCheckableFab.hide();
            }
        };
        mHandler.postDelayed(mHideFabRunnable, ANSWER_HIDE_DELAY);
    }
}
