package me.li2.android.animatedvector;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AnimatedVector extends AppCompatActivity {

    private ImageView mAddRemoveImage;
    private CheckableFab mCheckableFab;

    private AnimatedVectorDrawable mAddToRemoveDrawable;
    private AnimatedVectorDrawable mRemoveToAddDrawable;

    private boolean mIsAddState;

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
                mCheckableFab.setChecked(!mCheckableFab.isChecked());
            }
        });
    }
}
