# 从 Android Sample ApiDemos 中学习 android.animation API 的用法

http://li2.me/2016/01/android-sdk-sample-api-demos-for-animation.html


## 关于Android SDK Sample: ApiDemos

> 背景： HONEYCOMB 3.0 （[about versions android-3.0 highlights](http://developer.android.com/about/versions/android-3.0-highlights.html)） 发布于 2011.02，引入了New animation framework；3.1 发布于 2011.05.
>
> 在 [Android 开发者网站](http://developer.android.com/)  搜索『animation』，通过『blog』过滤搜索结果，其中一篇文章 [2011-02-24 Animation in Honeycomb](http://android-developers.blogspot.com/2011/02/animation-in-honeycomb.html) 排在搜索结果第2位，可见其重要程度，作者是 Chet Haase，一个致力于图形和动画研究的 Android 开发者，可以从他的 [个人博客graphics-geek.blogspot.com](http://graphics-geek.blogspot.com)  阅读更多相关主题的博文。

『Animation in Honeycomb』讨论了一个问题，已经有了能实现 move, scale, rotate, and fade 这些视图动画的 `android.view.animation` ，**为什么还要在 3.0 引入新 APIs `android.animation`？ 新 APIs 带来了哪些新特性？** 然后进一步展示了这些新特性的强大便利之处。

其中有一段话是这样的：

> But for a more detailed view of how things work, check out the **API Demos in the SDK for the new animations**. There are many small applications written for the new Animations category (at the top of the list of demos in the application, right before the word App. I like working on animation because it usually comes first in the alphabet).

> SDK samples 包含了一个例子 ApiDemos，这个例子详尽地展示了如何使用 3.0 SDK API。其中一部分 demos 正是关于**新的动画机制（android.animation）**。而和 animation 相关的 demos 排在整个列表的最前面，比 App demos 还靠前，这就是我为什么喜欢研究 animation 的原因。

（这个点可真奇特（简（可）单（爱）），不知道这些牛人在想什么，哈哈）



## 从SDK sample 中获取 ApiDemos

虽然是11年2月发布的，而现在已经是2016年的1月底了，虽然步子慢了五年，但3.x的动画依然没有过时，所以官方提供的 Sample 有什么理由不去学习呢？（除此之外，你认为有什么捷径吗？） （感觉像是发现了一直忽视的宝藏 ╰(￣▽￣)╮）

那么接下来，就把它导入 studio，需要特别说明的是 ApiDemos 位于新的 SDK sample 的 legacy 目录中，位于旧的sdk sample 根目录中：

```sh
$HOME/Library/Android/sdk/samples/android-21/legacy/ApiDemos
$HOME/Library/Android/sdk/samples/android-15/ApiDemos
```
ApiDemos 和动画有关的是（ApiDemos 全部列表见文章最后）：

```sh
$ tree -L 2
.
├── animation
│   ├── ActivityTransition.java
│   ├── ActivityTransitionDetails.java
│   ├── AnimationCloning.java
│   ├── AnimationLoading.java
│   ├── AnimationSeeking.java
│   ├── AnimatorEvents.java
│   ├── BouncingBalls.javppa
│   ├── CustomEvaluator.pjava
│   ├── FixedGridLayout.java
│   ├── LayoutAnimations.java
│   ├── LayoutAnimationsByDefault.java
│   ├── LayoutAnimationsHideShow.java
│   ├── ListFlipper.java
│   ├── MultiPropertyAnimation.java
│   ├── PathAnimations.java
│   ├── ReversingAnimation.java
│   ├── Rotate3dAnimation.java
│   ├── ShapeHolder.java
│   ├── Transition3d.java
│   └── Transitions.java
├── app // 这就是 Chet 说的，animation 在 app 上....上...上..上.面  ╮(╯▽╰)╭
├── graphics
│   ├── AnimateDrawable.java
│   ├── AnimateDrawables.java
│   ├── Arcs.java
│   ├── ShadowCardDrag.java
│   ├── ShadowCardStack.java
│   ├── Sweep.java
└── view // 3D transition, Interpolators, Push, Shake
    ├── Animation1.java
    ├── Animation2.java
    ├── Animation3.java
    ├── LayoutAnimation1.java
    ├── LayoutAnimation2.java
    ├── LayoutAnimation3.java
    ├── LayoutAnimation4.java
    ├── LayoutAnimation5.java
    ├── LayoutAnimation6.java
    ├── LayoutAnimation7.java
```


## 导入 ApiDemos 到 Android Studio

通过 Android Studio 导入 ApiDemos 时遇到了如下几个问题：

> Plugin is too old, please update to a more recent version, or set ANDROID_DAILY_OVERRIDE environment variable to ...

做了如下修改（**参照可以正常运行的 Android Studio 项目**）：
(1) 修改文件 `gradle-wrapper.properties` 中的 `distributionUrl=https\://services.gradle.org/distributions/gradle-2.4-all.zip`;（之前是2.8）
(2) 修改文件 `build.gradle (Project: your_app_name)` 中的 `dependencies: classpath 'com.android.tools.build:gradle:1.3.0`；（之前是2.0.0-alpha3）
[点击这里查看详细的解释](http://stackoverflow.com/a/34532399/2722270)


> Error:Execution failed for task ':app:mergeDebugResources'.
> ApiDemos/app/src/main/res/xml/preference_switch: Error: The file name must end with .xml

看来官方 sample 也会出现一些低级错误，重命名这个文件加上后缀，可以解决问题。


> ApiDemos/app/src/main/java/com/example/android/apis/os/MmsMessagingDemo.java
> Error:(19, 30) error: package com.google.android.mms does not exist

我的解决办法很粗暴，直接删掉 os 目录，同时要删掉在 manifest 中声明的对应组件。


> ApiDemos/app/src/main/java/com/example/android/apis/app/PrintBitmap.java
> Error:(23, 32) error: package android.support.v4.print does not exist

需要导入 support-v4。针对 Studio 2.0 preview4 的导入方法：
File >> Project Structure... >> Modules >> Dependencies >> +1 Library dependency


> Error:Execution failed for task ':app:processDebugManifest'.
> Manifest merger failed : uses-sdk:minSdkVersion 1 cannot be smaller than version 4 declared in library [com.android.support:support-v4:23.1.1] /Users/weiyi/Develop/AndroidApp/OpenSourceDemo/ApiDemos/app/build/intermediates/exploded-aar/com.android.support/support-v4/23.1.1/AndroidManifest.xml
  	Suggestion: use tools:overrideLibrary="android.support.v4" to force usage

解决办法是在 `build.gradle (Module)` 中增加 `mindSdkVersion`：

```xml
    defaultConfig {
        minSdkVersion 21
    }
```
因为我是从android-21中导入的ApiDemos，所以项目中有些地方使用了5.0的API，所以把min设置为21。


> Error:(65, 40) error: cannot find symbol method sqrt(float)

`FloatMath.sqrt(0.125f)` 等方法已经从 6.0 SDK 中移除了：All methods were removed from the public API in version 23.
修改 `compileSdkVersion` 为22（原来是23），同时修改 support-v4 的版本号为 22.2.1

------

经过这几步修改后，终于可以在 Android Strudio 上编译然后运行在 5.1 的系统上。
浪费了一天的时间 :(
写这篇文档又花了大半天 :)

所以，你如果想学习 ApiDemos，可以按照上述步骤自己从 SDK sample 中导入。如果想避免这些修改过程，可以clone我已经改好的：
[Learning_Android_Open_Source/ApiDemos](https://github.com/li2/Learning_Android_Open_Source/tree/master/ApiDemos
)

## 附录：ApiDemos 部分动画截图

![apidemos-animation-list](/assets/img/android/apidemos-animation.gif)
![apidemos-animation-list](/assets/img/android/apidemos-animation.png)


## 附录：ApiDemos 包含的全部示例

```sh
$ tree -L 2
.
├── ApiDemos.java
├── ApiDemosApplication.java
├── Shakespeare.java
├── accessibility
│   ├── ClockBackActivity.java
│   ├── ClockBackService.java
│   ├── CustomViewAccessibilityActivity.java
│   ├── TaskBackService.java
│   ├── TaskListActivity.java
│   └── TaskListView.java
├── animation
│   ├── ActivityTransition.java
│   ├── ActivityTransitionDetails.java
│   ├── AnimationCloning.java
│   ├── AnimationLoading.java
│   ├── AnimationSeeking.java
│   ├── AnimatorEvents.java
│   ├── BouncingBalls.java
│   ├── CustomEvaluator.java
│   ├── FixedGridLayout.java
│   ├── LayoutAnimations.java
│   ├── LayoutAnimationsByDefault.java
│   ├── LayoutAnimationsHideShow.java
│   ├── ListFlipper.java
│   ├── MultiPropertyAnimation.java
│   ├── PathAnimations.java
│   ├── ReversingAnimation.java
│   ├── Rotate3dAnimation.java
│   ├── ShapeHolder.java
│   ├── Transition3d.java
│   └── Transitions.java
├── app
│   ├── ActionBarDisplayOptions.java
│   ├── ActionBarMechanics.java
│   ├── ActionBarNavigation.java
│   ├── ActionBarNavigationTarget.java
│   ├── ActionBarSettingsActionProviderActivity.java
│   ├── ActionBarShareActionProviderActivity.java
│   ├── ActionBarTabs.java
│   ├── ActionBarUsage.java
│   ├── ActivityRecreate.java
│   ├── AlarmController.java
│   ├── AlarmService.java
│   ├── AlarmService_Service.java
│   ├── AlertDialogSamples.java
│   ├── Animation.java
│   ├── AppUpdateReceiver.java
│   ├── AppUpdateSspReceiver.java
│   ├── ContactsFilter.java
│   ├── ContactsFilterInstrumentation.java
│   ├── ContactsSelectInstrumentation.java
│   ├── CustomDialogActivity.java
│   ├── CustomTitle.java
│   ├── DeviceAdminSample.java
│   ├── DialogActivity.java
│   ├── DoNothing.java
│   ├── FinishAffinity.java
│   ├── ForegroundService.java
│   ├── ForwardTarget.java
│   ├── Forwarding.java
│   ├── FragmentAlertDialog.java
│   ├── FragmentArguments.java
│   ├── FragmentArgumentsFragment.java
│   ├── FragmentContextMenu.java
│   ├── FragmentCustomAnimations.java
│   ├── FragmentDialog.java
│   ├── FragmentDialogOrActivity.java
│   ├── FragmentHideShow.java
│   ├── FragmentLayout.java
│   ├── FragmentListArray.java
│   ├── FragmentMenu.java
│   ├── FragmentMenuFragment.java
│   ├── FragmentNestingTabs.java
│   ├── FragmentReceiveResult.java
│   ├── FragmentRetainInstance.java
│   ├── FragmentStack.java
│   ├── FragmentStackFragment.java
│   ├── FragmentTabs.java
│   ├── FragmentTabsFragment.java
│   ├── HelloWorld.java
│   ├── IncomingMessage.java
│   ├── IncomingMessageInterstitial.java
│   ├── IncomingMessageView.java
│   ├── IntentActivityFlags.java
│   ├── Intents.java
│   ├── IsolatedService.java
│   ├── IsolatedService2.java
│   ├── LauncherShortcuts.java
│   ├── LoaderCursor.java
│   ├── LoaderCustom.java
│   ├── LoaderRetained.java
│   ├── LoaderThrottle.java
│   ├── LocalSample.java
│   ├── LocalSampleInstrumentation.java
│   ├── LocalService.java
│   ├── LocalServiceActivities.java
│   ├── MenuInflateFromXml.java
│   ├── MessengerService.java
│   ├── MessengerServiceActivities.java
│   ├── NotificationDisplay.java
│   ├── NotifyWithText.java
│   ├── NotifyingController.java
│   ├── NotifyingService.java
│   ├── OneShotAlarm.java
│   ├── OverscanActivity.java
│   ├── PersistentState.java
│   ├── PresentationActivity.java
│   ├── PresentationWithMediaRouterActivity.java
│   ├── PrintBitmap.java
│   ├── PrintCustomContent.java
│   ├── PrintHtmlFromScreen.java
│   ├── PrintHtmlOffScreen.java
│   ├── QuickContactsDemo.java
│   ├── ReceiveResult.java
│   ├── RedirectEnter.java
│   ├── RedirectGetter.java
│   ├── RedirectMain.java
│   ├── RemoteService.java
│   ├── ReorderFour.java
│   ├── ReorderOnLaunch.java
│   ├── ReorderThree.java
│   ├── ReorderTwo.java
│   ├── RepeatingAlarm.java
│   ├── RotationAnimation.java
│   ├── SaveRestoreState.java
│   ├── ScreenOrientation.java
│   ├── SearchInvoke.java
│   ├── SearchQueryResults.java
│   ├── SearchSuggestionSampleProvider.java
│   ├── SecureDialogActivity.java
│   ├── SecureSurfaceViewActivity.java
│   ├── SecureWindowActivity.java
│   ├── SendResult.java
│   ├── ServiceStartArguments.java
│   ├── SetWallpaperActivity.java
│   ├── SoftInputModes.java
│   ├── StatusBarNotifications.java
│   ├── TextToSpeechActivity.java
│   ├── TranslucentActivity.java
│   ├── TranslucentBlurActivity.java
│   ├── VoiceRecognition.java
│   └── WallpaperActivity.java
├── appwidget
│   ├── ExampleAppWidgetConfigure.java
│   ├── ExampleAppWidgetProvider.java
│   └── ExampleBroadcastReceiver.java
├── content
│   ├── ChangedContacts.java
│   ├── ClipboardSample.java
│   ├── DocumentsSample.java
│   ├── ExternalStorage.java
│   ├── FileProvider.java
│   ├── InstallApk.java
│   ├── PickContact.java
│   ├── ReadAsset.java
│   ├── ResourcesLayoutReference.java
│   ├── ResourcesSample.java
│   ├── ResourcesSmallestWidth.java
│   ├── ResourcesWidthAndHeight.java
│   ├── StyledText.java
│   └── TextUndoActivity.java
├── graphics
│   ├── AlphaBitmap.java
│   ├── AnimateDrawable.java
│   ├── AnimateDrawables.java
│   ├── Arcs.java
│   ├── BitmapDecode.java
│   ├── BitmapMesh.java
│   ├── BitmapPixels.java
│   ├── CameraPreview.java
│   ├── Clipping.java
│   ├── ColorFilters.java
│   ├── ColorMatrixSample.java
│   ├── ColorPickerDialog.java
│   ├── Compass.java
│   ├── CompressedTextureActivity.java
│   ├── CreateBitmap.java
│   ├── Cube.java
│   ├── CubeMapActivity.java
│   ├── CubeRenderer.java
│   ├── DensityActivity.java
│   ├── DrawPoints.java
│   ├── FingerPaint.java
│   ├── FrameBufferObjectActivity.java
│   ├── GLES20Activity.java
│   ├── GLES20TriangleRenderer.java
│   ├── GLSurfaceViewActivity.java
│   ├── GradientDrawable1.java
│   ├── GraphicsActivity.java
│   ├── Layers.java
│   ├── MatrixPaletteActivity.java
│   ├── MatrixPaletteRenderer.java
│   ├── MeasureText.java
│   ├── PathEffects.java
│   ├── PathFillTypes.java
│   ├── Patterns.java
│   ├── PictureLayout.java
│   ├── Pictures.java
│   ├── PolyToPoly.java
│   ├── ProxyDrawable.java
│   ├── PurgeableBitmap.java
│   ├── PurgeableBitmapView.java
│   ├── Regions.java
│   ├── RoundRects.java
│   ├── ScaleToFit.java
│   ├── SensorTest.java
│   ├── ShadowCardDrag.java
│   ├── ShadowCardStack.java
│   ├── ShapeDrawable1.java
│   ├── StaticTriangleRenderer.java
│   ├── SurfaceViewOverlay.java
│   ├── Sweep.java
│   ├── TextAlign.java
│   ├── TouchPaint.java
│   ├── TouchRotateActivity.java
│   ├── TranslucentGLSurfaceViewActivity.java
│   ├── TriangleActivity.java
│   ├── TriangleRenderer.java
│   ├── Typefaces.java
│   ├── UnicodeChart.java
│   ├── Vertices.java
│   ├── WindowSurface.java
│   ├── Xfermodes.java
│   ├── kube
│   └── spritetext
├── hardware
│   └── ConsumerIr.java
├── media
│   ├── AudioFxDemo.java
│   ├── MediaPlayerDemo.java
│   ├── MediaPlayerDemo_Audio.java
│   ├── MediaPlayerDemo_Video.java
│   ├── VideoViewDemo.java
│   └── projection
├── nfc
│   ├── ForegroundDispatch.java
│   ├── ForegroundNdefPush.java
│   └── TechFilter.java
├── preference
│   ├── AdvancedPreferences.java
│   ├── DefaultValues.java
│   ├── FragmentPreferences.java
│   ├── LaunchingPreferences.java
│   ├── MyPreference.java
│   ├── PreferenceDependencies.java
│   ├── PreferenceWithHeaders.java
│   ├── PreferencesFromCode.java
│   ├── PreferencesFromXml.java
│   └── SwitchPreference.java
├── security
│   └── KeyStoreUsage.java
├── text
│   ├── Link.java
│   ├── LogTextBox.java
│   ├── LogTextBox1.java
│   └── Marquee.java
└── view
    ├── Animation1.java
    ├── Animation2.java
    ├── Animation3.java
    ├── AutoComplete1.java
    ├── AutoComplete2.java
    ├── AutoComplete3.java
    ├── AutoComplete4.java
    ├── AutoComplete5.java
    ├── AutoComplete6.java
    ├── Baseline1.java
    ├── Baseline2.java
    ├── Baseline3.java
    ├── Baseline4.java
    ├── Baseline6.java
    ├── Baseline7.java
    ├── BaselineNested1.java
    ├── BaselineNested2.java
    ├── BaselineNested3.java
    ├── Buttons1.java
    ├── CheckableFrameLayout.java
    ├── Cheeses.java
    ├── ChronometerDemo.java
    ├── ContentBrowserActivity.java
    ├── ContentBrowserNavActivity.java
    ├── Controls1.java
    ├── Controls2.java
    ├── Controls3.java
    ├── Controls4.java
    ├── Controls5.java
    ├── Controls6.java
    ├── Controls7.java
    ├── Controls8.java
    ├── Controls9.java
    ├── CustomLayout.java
    ├── CustomLayoutActivity.java
    ├── CustomView1.java
    ├── DateWidgets1.java
    ├── DateWidgets2.java
    ├── DragAndDropDemo.java
    ├── DraggableDot.java
    ├── ExpandableList1.java
    ├── ExpandableList2.java
    ├── ExpandableList3.java
    ├── Focus1.java
    ├── Focus2.java
    ├── Focus3.java
    ├── Focus5.java
    ├── GameActivity.java
    ├── GameControllerInput.java
    ├── GameView.java
    ├── Grid1.java
    ├── Grid2.java
    ├── Grid3.java
    ├── GridLayout1.java
    ├── GridLayout2.java
    ├── GridLayout3.java
    ├── HorizontalScrollView1.java
    ├── Hover.java
    ├── HoverInterceptorView.java
    ├── ImageButton1.java
    ├── ImageView1.java
    ├── InternalSelectionFocus.java
    ├── InternalSelectionScroll.java
    ├── InternalSelectionView.java
    ├── LabelView.java
    ├── LayoutAnimation1.java
    ├── LayoutAnimation2.java
    ├── LayoutAnimation3.java
    ├── LayoutAnimation4.java
    ├── LayoutAnimation5.java
    ├── LayoutAnimation6.java
    ├── LayoutAnimation7.java
    ├── LinearLayout1.java
    ├── LinearLayout10.java
    ├── LinearLayout2.java
    ├── LinearLayout3.java
    ├── LinearLayout4.java
    ├── LinearLayout5.java
    ├── LinearLayout6.java
    ├── LinearLayout7.java
    ├── LinearLayout8.java
    ├── LinearLayout9.java
    ├── List1.java
    ├── List10.java
    ├── List11.java
    ├── List12.java
    ├── List13.java
    ├── List14.java
    ├── List15.java
    ├── List16.java
    ├── List17.java
    ├── List2.java
    ├── List3.java
    ├── List4.java
    ├── List5.java
    ├── List6.java
    ├── List7.java
    ├── List8.java
    ├── List9.java
    ├── PopupMenu1.java
    ├── ProgressBar1.java
    ├── ProgressBar2.java
    ├── ProgressBar3.java
    ├── ProgressBar4.java
    ├── RadioGroup1.java
    ├── RatingBar1.java
    ├── RelativeLayout1.java
    ├── RelativeLayout2.java
    ├── RotatingButton.java
    ├── ScrollBar1.java
    ├── ScrollBar2.java
    ├── ScrollBar3.java
    ├── ScrollView1.java
    ├── ScrollView2.java
    ├── SearchViewActionBar.java
    ├── SearchViewAlwaysVisible.java
    ├── SearchViewFilterMode.java
    ├── SecureView.java
    ├── SecureViewOverlay.java
    ├── SeekBar1.java
    ├── Spinner1.java
    ├── SplitTouchView.java
    ├── Switches.java
    ├── SystemUIModes.java
    ├── SystemUIModesOverlay.java
    ├── TableLayout1.java
    ├── TableLayout10.java
    ├── TableLayout11.java
    ├── TableLayout12.java
    ├── TableLayout2.java
    ├── TableLayout3.java
    ├── TableLayout4.java
    ├── TableLayout5.java
    ├── TableLayout6.java
    ├── TableLayout7.java
    ├── TableLayout8.java
    ├── TableLayout9.java
    ├── Tabs1.java
    ├── Tabs2.java
    ├── Tabs3.java
    ├── Tabs4.java
    ├── Tabs5.java
    ├── Tabs6.java
    ├── TextClockDemo.java
    ├── TextSwitcher1.java
    ├── TranslucentBarsActivity.java
    ├── VideoPlayerActivity.java
    ├── Visibility1.java
    ├── WebView1.java
    └── WindowFocusObserver.java

16 directories, 391 files
```

------

by
weiyi.li [li2.me](http://li2.me) <weiyi.just2@gmail.com>
2016-01-26 ~ 2016-01-27
禁止转载
