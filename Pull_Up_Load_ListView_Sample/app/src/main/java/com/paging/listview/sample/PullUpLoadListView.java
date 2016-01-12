package com.paging.listview.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by weiyi on 16/1/9.
 */

public class PullUpLoadListView extends ListView {

    public PullUpLoadListView(Context context) {
        this(context, null);
    }

    public PullUpLoadListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullUpLoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public interface OnPullUpLoadListener {
        void onPullUpLoading();
    }

    public void setOnPullUpLoadListener(OnPullUpLoadListener listener) {
        mOnPullUpLoadListener = listener;
    }

    // When loading finished, the controller should call this public method to update footer view.
    public void onPullUpLoadFinished(boolean hasNoMoreItems) {
        // Clear flag
        mIsPullUpLoading = false;

        if (hasNoMoreItems) {
            // when have no more items, update footer view to: NO MORE
            mFooterView.updateView(PullUpLoadListViewFooter.State.NOT_LOADING, FOOTER_VIEW_CONTENT_NO_MORE);
        } else {
            // The other cases: (1)Loading succeed and still has more items, (2)Loading failed,
            // should hide footer view.
            hideFooterView();
        }
    }


    private static final String FOOTER_VIEW_CONTENT_LOADING = "Loading....";
    private static final String FOOTER_VIEW_CONTENT_NO_MORE = "# No More #";

    private PullUpLoadListViewFooter mFooterView;
    // The flag used to avoid loading again when the list view is already in loading state.
    private boolean mIsPullUpLoading;
    // The controller should register this listener.
    private OnPullUpLoadListener mOnPullUpLoadListener;

    private void init() {
        mIsPullUpLoading = false;
        setOnScrollListener(mOnScrollListener);
    }

    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int scrollState) {
            // do nothing.
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // Start a new loading when the last item scrolls into screen, instead of overriding method onTouchEvent.
            if (needLoad(firstVisibleItem, visibleItemCount, totalItemCount)) {
                startPullUpLoad();
            }
        }

        private boolean needLoad(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            int lastVisibleItem = firstVisibleItem + visibleItemCount;
            boolean isAtListEnd = lastVisibleItem == totalItemCount;
            return !mIsPullUpLoading && isAtListEnd;
        }
    };

    private void startPullUpLoad() {
        if (mOnPullUpLoadListener != null) {
            // Show the foot view and update its state to LOADING.
            showFooterView();
            // Set flag
            mIsPullUpLoading = true;
            // Call the callback to notify the listView's hosted controller to load data.
            mOnPullUpLoadListener.onPullUpLoading();
        }
    }

    private void showFooterView() {
        if (mFooterView == null) {
            mFooterView = new PullUpLoadListViewFooter(getContext());
            addFooterView(mFooterView);
        }
        mFooterView.setVisibility(View.VISIBLE);
        mFooterView.updateView(PullUpLoadListViewFooter.State.LOADING, FOOTER_VIEW_CONTENT_LOADING);
    }

    // It's better to hide footer instead of removing.
    // Since after removing, we should create a new footer instance and add it into view hierarchy again,
    // this will call findViewById many times which waste time.
    private void hideFooterView() {
        if (mFooterView != null) {
            mFooterView.setVisibility(View.INVISIBLE);
        }
    }
}
