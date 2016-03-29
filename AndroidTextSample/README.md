[Android SpannableString Example](http://li2.me/2016/03/android-how-to-use-spannable-to-format-string.html)

为了格式化字符串，可以使用 [SpannableStringBuilder](http://developer.android.com/reference/android/text/SpannableStringBuilder.html)。
SpannableStringBuilder 有一个方法 `setSpan (Object what, int start, int end, int flags)`，可以把由 start 和 end 指定的部分字符串替换成给定的对象，给定的对象可以是：

- `ForegroundColorSpan` 添加前景色；
- `BackgroundColorSpan` 添加背景色；
- `RelativeSizeSpan` 改变文字的相对大小；
- `StyleSpan` 粗体、斜体等样式；
- `UnderlineSpan` 添加下划线；
- `StrikethroughSpan` 添加删除线；
- `SuperscriptSpan` 上标；
- `SubscriptSpan` 下标；
- `ClickableSpan` 添加 URL 超链接样式；
- `ImageSpan` 添加图片到字符串，实现图文混排（How to add image to text in TextView）；
- 这些 span 继承（或间接继承)自 [CharacterStyle](http://developer.android.com/reference/android/text/style/CharacterStyle.html)。

效果如下图：

![android-spannable-string-api](https://github.com/li2/Learning_Android_Open_Source/blob/master/AndroidTextSample/android-spannable-string-api.png)
