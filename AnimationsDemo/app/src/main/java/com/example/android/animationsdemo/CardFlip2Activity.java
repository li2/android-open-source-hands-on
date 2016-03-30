package com.example.android.animationsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/*
https://2cupsoftech.wordpress.com/2012/09/18/3d-flip-between-two-view-or-viewgroup-on-android/
已知bug：cardView width 和 height 越大，动画变形就越大。
 */
public class CardFlip2Activity extends Activity {

    private View cardView;
    private View cardFrontView;
    private View cardBackView;
    private static final int FLIP_ANIMATION_DURATION= 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip2);

        cardView = findViewById(R.id.card_view);
        cardFrontView = findViewById(R.id.card_front);
        cardBackView = findViewById(R.id.card_back);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });
    }

    private void flipCard() {
        FlipAnimation flipAnimation = new FlipAnimation(cardFrontView, cardBackView);
        flipAnimation.setDuration(FLIP_ANIMATION_DURATION);

        if (cardFrontView.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        cardView.startAnimation(flipAnimation);
    }
}
