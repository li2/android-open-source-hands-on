package com.paging.listview.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/*
The Footer View only have two state:
(1) Loading state, means that the listView is trying to load more items,
 with a infinite progress bar, a text label shows "loading..."
(2) Not loading state, means that the listView does nothing, or has completed loading.
 with a text label shows "nothing" or "No more".

So, we only need have a public method to switch to different state, and update its label content.
 */

public class PullUpLoadListViewFooter extends LinearLayout {

    public enum State {
        LOADING,
        NOT_LOADING,
    }

    public PullUpLoadListViewFooter(Context context) {
        this(context, null);
    }

    public PullUpLoadListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void updateView(State state, String content) {
        if (state == State.LOADING) {
            mLoadingLinearLayout.setVisibility(View.VISIBLE);
            mNotLoadingLinearLayout.setVisibility(View.INVISIBLE);
            mLoadingLabel.setText(content);
        } else if (state == State.NOT_LOADING){
            mLoadingLinearLayout.setVisibility(View.INVISIBLE);
            mNotLoadingLinearLayout.setVisibility(View.VISIBLE);
            mNotLoadingLabel.setText(content);
        } else {
            // should never go here
        }
    }

    private LinearLayout mLoadingLinearLayout;
    private TextView mLoadingLabel;

    private LinearLayout mNotLoadingLinearLayout;
    private TextView mNotLoadingLabel;

    // pul in R.id.pul*** means "pull up load"
    private void init() {
        inflate(getContext(), R.layout.pul_listview_footer, this);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.pulListViewFooter_loading);
        mLoadingLabel = (TextView) findViewById(R.id.pulListViewFooter_loadingLabel);
        mNotLoadingLinearLayout = (LinearLayout) findViewById(R.id.pulListViewFooter_notLoading);
        mNotLoadingLabel = (TextView) findViewById(R.id.pulListViewFooter_notLoadingLabel);
    }
}
