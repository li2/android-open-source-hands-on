浮动操作按钮（floating action button）的状态根据“checked”状态而改变，并伴随动画。

这个项目核心代码来自 [android-topeka](https://github.com/googlesamples/android-topeka)，原项目比较大，为了学习此种 FAB 动画的实现方法，特意从中摘录出来。

这篇文章 [如何学习Android Animation？](http://li2.me/2016/01/how-to-learn-android-animation.html) 介绍了 topeka，查看章节「2015-06-16 More Material Design with Topeka for Android」以阅读详细内容。

FAB 动画的核心是 `answer_quiz_fab.xml`, 这个文件定义了一个 state list drawables：可以在一个 drawable 中定义多个 drawable，根据状态选择显示哪个 drawable。常用于 button、checkbox 等。

> Lets you assign a number of graphic images to a single Drawable and swap out the visible item by a string ID value.
> It can be defined in an XML file with the <selector> element. Each state Drawable is defined in a nested <item> element.
> [Refer to drawable/StateListDrawable](http://developer.android.com/reference/android/graphics/drawable/StateListDrawable.html)

《Android Programming 2nd big nerd》Chapter21 XML Drawables P372 对此也有描述。

动画效果（正常动画效果为450ms，为了录制gif，特意修改为2000ms）：
![checkable fab.gif](https://cloud.githubusercontent.com/assets/6058601/13045489/9315ae70-d40e-11e5-9ecd-2a74768f02ab.gif)
