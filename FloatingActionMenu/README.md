Floating Action Button (FAB) Menu Example

开源项目 [futuresimple/android-floating-action-button](https://github.com/futuresimple/android-floating-action-button) 实现了浮动按钮菜单，即：点击FAB就会弹出菜单。
这个库提供了自定义的 UI 组件，使用方法参考 [原项目的 sample](https://github.com/futuresimple/android-floating-action-button/blob/master/sample/src/main/res/layout/activity_main.xml)。

我在其基础上做了一些定制化处理：

1. 实现 label 的圆角矩形背景 [commit](https://github.com/li2/Learning_Android_Open_Source/commit/58685ab18463681b98ae23cf28659249835ba300)：

    通过自定义 shape drawable fab_menu_label_background.xml 实现圆角矩形（rounded rectangle）；

1. 为 label 添加 Elevation 效果 [commit](https://github.com/li2/Learning_Android_Open_Source/commit/b468174f0f443e49716e7271eeeb776fcab675c4)：
    
    > To set the elevation of a view in a layout definition, use the `android:elevation` attribute. To set the elevation of a view in the code of an activity, use the `View.setElevation()` method. [Refer to material elevation](http://developer.android.com/training/material/shadows-clipping.html#Elevation)

1. 增加一个 mask view 层，在 floating menu 展开时覆盖在当前 activity 之上 [commit](https://github.com/li2/Learning_Android_Open_Source/commit/1fe0bf4545d2c793d46ad48dce20c8a8fa4e8296)：

    把 floating action menu 放到单独的 layout 文件中，然后通过 include 语句包含在 activity layout 内。目的是使 activity layout 看起来简洁。[Refer to Re-using Layouts](http://developer.android.com/training/improving-layouts/reusing-layouts.html)


效果图：

![floating action button menu](https://github.com/li2/Learning_Android_Open_Source/blob/master/FloatingActionMenu/floating_action_button_menu.png)

图片从 https://design.google.com/icons/ 获取；颜色从图片中抓取的 http://html-color-codes.info/colors-from-image/ 。
