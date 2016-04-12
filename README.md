关于这个项目
============

**PERSONAL**
------------

Android 开发学习过程中，有两处重要的代码资料来源，一是 Android Training 及其 Sample，二是 GitHub。

Github 上已经有各种高质量的「集锦」，它们的工作是按某种主题分类整理高质量的开源项目，给出链接、总结优缺点。这样的集锦是有意义的。

但这个项目并不是「开源项目集锦」，把这些开源项目放在一个文件夹里，「如此搬运」并没有意义。这里是我记录学习 Android 开源库过程，以及整理的笔记。


```xml
├── AnimatedVector
├── AnimationsDemo
├── ApiDemos
├── CustomView
├── FloatingActionMenu
├── MaterialPreference
├── PullUpLoadListView
└── TextDemo
```


AnimationsDemo
------------

2016-03-07

[Android Training - Adding Animations](http://developer.android.com/training/animation/index.html)，演示如何向应用中添加一些常见的动画，分为5个部分，包括详细的文档和代码。

- 两个重叠视图之间的淡入淡出动画（crossfading between two overlapping views）
- 卡片翻转动画（a "card-flip" animation using custom fragment transactions）
- ViewPager 切换动画（ViewPager.PageTransformer）
- 缩略图扩大动画（expanding or touch-to-zoom animation）
- 布局内的子视图增加、删除、更新时的动画（built-in animations of layout）



ApiDemos
------------

2016-01-27

ApiDemos 是 3.0 SDK sample 包含的一个例子，这个例子展示了如何使用 3.0 SDK API。（其中一部分演示了新的动画机制的用法（android.animation））

需要特别说明的是，ApiDemos 位于新的 SDK sample 的 legacy 目录中，位于旧的sdk sample 根目录中：

```sh
$HOME/Library/Android/sdk/samples/android-21/legacy/ApiDemos
$HOME/Library/Android/sdk/samples/android-15/ApiDemos
```
ApiDemos 可以从 SDK 中获取，之所以上传到 Github，是因为从 sdk sample 导入到 Android Studio 花费了好大的功夫。所以想保存起来，备用。

如果你想了解详细的过程，可以点击这里查看：

- [如何学习Android Animation？](http://li2.me/2016/01/how-to-learn-android-animation.html)
- [从 Android Sample ApiDemos 中学习 android.animation API 的用法。](http://li2.me/2016/01/android-sdk-sample-api-demos-for-animation.html)



FloatingActionMenu
------------

2016-02-14

开源项目 [futuresimple/android-floating-action-button](https://github.com/futuresimple/android-floating-action-button) 实现了浮动按钮菜单，即：点击FAB就会弹出菜单。
![floating action button menu](https://github.com/li2/Learning_Android_Open_Source/blob/master/FloatingActionMenu/floating_action_button_menu.png)



PullUpLoadListView
------------

2016-01-12

![pull up load ListView.gif](http://li2.me/assets/img/android/android-pull-up-load-listview.gif)



TextDeom
------------

2016-03-28

[Android SpannableString Example](http://li2.me/2016/03/android-spannablestring-example.html)

为了格式化字符串，可以使用 [SpannableStringBuilder](http://developer.android.com/reference/android/text/SpannableStringBuilder.html)。

![android-spannable-string-api](https://github.com/li2/Learning_Android_Open_Source/blob/master/TextDemo/android-spannable-string-api.png)



About
------------

- weiyi.li
- [我的个人博客 li2.me](http://li2.me)
- <weiyi.just2@gmail.com>
