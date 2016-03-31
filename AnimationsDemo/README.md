[Android Training - Adding Animations](http://developer.android.com/training/animation/index.html)
============

这是 Android Training 的一部分，演示如何向应用中添加一些常见的动画，分为5个部分，包括详细的文档和代码。

- 两个重叠视图之间的淡入淡出动画（crossfading between two overlapping views）
- 卡片翻转动画（a "card-flip" animation using custom fragment transactions）
- ViewPager 切换动画（ViewPager.PageTransformer）
- 缩略图扩大动画（expanding or touch-to-zoom animation）
- 布局内的子视图增加、删除、更新时的动画（built-in animations of layout）


## 淡入淡出动画

`ViewPropertyAnimator`  用于视图属性动画。

```java
view.animate()
    .alpha(0f)
    .setDuration(mShortAnimationDuration)
    .setListener(new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
	
        }
    });
```


## 卡片翻转动画实现方式1：替换默认的 fragment transactions 动画

```java
getFragmentManager()
    // 创建并提交一个新的 transaction。
    .beginTransaction()

    // 设置 transaction （以及从回退栈弹出时）开始和结束时的动画。
    .setCustomAnimations(...)

    // 用 `CardBackFragment` 替换当前的 `CardFrontFragment`
    .replace(R.id.container, new CardBackFragment())

    // 保存 transaction 到 fragment 回退栈中，
    // 当用户按 Back 键，或者调用 popBackStack() 时，就能够执行相反的 transaction.
    .addToBackStack(null)
    .commit();
```

## 卡片翻转动画实现方式2

```java
FlipAnimation flipAnimation = new FlipAnimation(cardFrontView, cardBackView);
```


## ViewPager 切换动画（ViewPager.PageTransformer）

继承接口 `ViewPager.PageTransformer`，并覆写方法 `transformPage()` 以自定义 ViewPager 切换动画。然后调用 `setPageTransformer(...)` 替换默认的动画。

[第三方 library 包含多种切换动画](https://github.com/ToxicBakery/ViewPagerTransforms)。

`PagerTransformerParallax` 实现了类似雅虎天气（Yahoo Weather App）ViewPager 切换时的「视差 parallax」效果：ViewPager 背景图片的移动速度比内容的移动速度慢一倍。[详情参阅 gist](https://gist.github.com/li2/2b73cbb9bbe6eee92488)


```java
// 第三方
mPager.setPageTransformer(false, new DepthPageTransformer());

// 以下三种方式是我的实现
mPager.setPageTransformer(false, new PagerTransformerCrossFade());
mPager.setPageTransformer(false, new PagerTransformerOverlap());
mPager.setPageTransformer(false, new PagerTransformerParallax(R.id.pagerBackground));
```
