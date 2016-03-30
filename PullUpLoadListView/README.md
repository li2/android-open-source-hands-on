# Android_ListView上拉加载更多

## ListView上拉加载更多的UI需求

（1）向上滑动 ListView，当最后一个条目滚入屏幕时开始加载更多条目，在列表底部增加一个 footerView：一个 infinite progressBar，一个 textView 显示 “Loading...”；
（2）根据数据加载的结果更新 view：
（2.1）如果已经没有更多条目，则更新 footerView：仅包含一个 textView 显示“No More”；
（2.2）如果成功获取更多条目，则更新 ListView，同时移除（隐藏） footerView；
（2.3）如果加载失败（网络异常等原因），移除（隐藏） footerView。

综上述，需要有一个 footerView，它包含两种状态：

```xml
-------------------------------
|        @  Loading...        | (借用 @ 当做infinite progressBar)
-------------------------------

-------------------------------
|        No More              |
-------------------------------
```

这是一个挺简单 UI 需求，比常见的实现方式少了一种状态：

```xml
-------------------------------
|        查看更多             |
-------------------------------
```
在这种状态下，点击 footerView 也可以和『上拉』一样加载更多条目。我对比了手Q和微信，手Q就多了这个『查看更多』的状态（当然，必须在上拉时恰好让它停在最后一个条目，不然上拉过头后，就立刻变成『Loading...』）。
**本需求并不需要这个状态，所以下面的实现分析不会考虑它，所以整体实现相对简单。**

为了实现上述需求，需要考虑三个问题：

- 如何定义 footerView？
- 何时加载更多？
- 数据加载完毕后，如何更新视图？


## 如何定义 footerView？

如上述，**footerView 包含两种状态：加载中、没有加载**。
『加载中』包含两个控件，infinite progressBar 和 textView，放进一个 LinearLayout；『没有加载』只包含一个控件，textView，也把它放进一个 LinearLayout；然后把这两个 LinearLayout 放到一个 FrameLayout 内。根据状态决定显示哪个 LinearLayout。因此只需要一个 public method:

```java
public class PullUpLoadListViewFooter extends LinearLayout {
    public enum State {
        LOADING,
        NOT_LOADING,
    }
    public void updateView(State state, String content) {}
}
```


## 何时加载更多？

**向上滑动 ListView，当最后一个条目滚入屏幕时开始加载更多条目**。ListView 可以监听滚动事件，因此知道何时加载更多。但数据加载的工作显然应该交给控制器，也就是 ListView 的托管者比如 Activity 来完成。
所以，在 ListView 中定义一个接口，并在 ListView 滚动事件中回调这个接口方法：

```java
public class PullUpLoadListView extends ListView {
    
    public interface OnPullUpLoadListener {
        void onPullUpLoading();
    }

    public void setOnPullUpLoadListener(OnPullUpLoadListener listener) {
        mOnPullUpLoadListener = listener;
    }
    
    private OnPullUpLoadListener mOnPullUpLoadListener;
    
    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // Start a new loading when the last item scrolls into screen, instead of overriding method onTouchEvent.
            // 检查是否到listView底部，检查callbacks是否注册：
            if (needLoad(...)) {
                startPullUpLoad();
            }
        }
    };
}
```
`startPullUpLoad()` 是listView 处理上拉加载的核心代码：秀出显示『Loading...』的footerView；设置标志位表示『已经处于上拉加载状态中』防止重复加载；回调。

```java
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
```

再由 Activity 实现该接口完成加载工作：

```java
listView.setOnPullUpLoadListener(new PullUpLoadListView.OnPullUpLoadListener() {
    @Override
    public void onPullUpLoading() {
        if (shouldLoadMore) {
            // Loading more data
            new LoadDataAsyncTask().execute();
        } else {
            // Already has no more data
            // 下面会讲到这个方法
            listView.onPullUpLoadFinished(true);
        }
    }
});
```

## 数据加载完毕后，如何更新视图？

ListView 提供一个public方法，根据数据加载的结果更新视图：

```java
public class PullUpLoadListView extends ListView {
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
}
```
Activity 完成加载数据后，调用 ListView 提供的方法，并更新 adapter 数据集：

```java
listView.onPullUpLoadFinished(false);
// Add more data to adapter and notify data set changed to update listView.
adapter.addMoreItems(newItems);
```

## 源码实现总结

上述实现基于 [nicolasjafelle/PagingListView](https://github.com/nicolasjafelle/PagingListView)，对 [PagingListView.java](https://github.com/nicolasjafelle/PagingListView/blob/master/PagingListViewProject/PagingListView/src/main/java/com/paging/listview/PagingListView.java) 做了两处较大改动：
（1）数据加载完成后，由PagingListView负责更新adapter，**考虑到ListView可能并不清楚adapter的接口，所以还是交给activity比较好**；

```java
// PagingListView的实现
public void onFinishLoading(boolean hasMoreItems, List<? extends Object> {
    ...
    ((PagingBaseAdapter) adapter).addMoreItems(newItems);
}
```
（2）PagingListView维护了一个私有成员`boolean hasMoreItems`，然后在滚动事件回调`onScroll(...)`中，如果该值为false，就不会加载更多数据。
我觉得不应该由ListView来维护『是否具有更多的item』，这样会带来一些困惑和额外的工作。比如该值为false的情况下，当外部清空list item后，必须重置 `hasMoreItems`，否则无法继续加载。

**这样逻辑显得比较乱，而『是否可以加载更多』，应该分成两部分：**
由 ListView 判断『没有处于加载状态』并且『已经滚到了最后一个条目』则允许加载；
由 Activity 判断『还有更多的数据』则允许加载。
这样就显得清晰很多了。

[你可以从这里获取源码：Learning_Android_Open_Source/Pull_Up_Load_ListView_Sample](https://github.com/li2/Learning_Android_Open_Source/tree/master/Pull_Up_Load_ListView_Sample)

如下的GIF演示了上拉加载的过程：
![demo](https://github.com/li2/Learning_Android_Open_Source/blob/master/Pull_Up_Load_ListView_Sample/demo.gif)





PagingListView
==============

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-PagingListView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/251)

PagingListView has the ability to add more items on it like Gmail does. Basically is a ListView with the ability to add more items on it when reaches the end of the list.<br>
While "pull to refresh" pattern works at the top of the List and show the latest added items, the PagingListView works at the bottom of the List and shows the first added items.﻿


Developed By
================

* Nicolas Jafelle - <nicolasjafelle@gmail.com>


Forked by
================

* weiyi.li - <weiyi.just2@gmail.com> <http://li2.me> <http://segmentfault.com/u/li2>



License
================

    Copyright 2013 Nicolas Jafelle

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
