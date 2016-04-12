ApiDemos 可以从 SDK 中获取，之所以上传到 Github，是因为从 sdk sample 导入到 Android Studio 花费了好大的功夫。所以需要保存备用。

[如果你想了解详细的过程，可以点击这里查看](http://li2.me/2016/01/android-sdk-sample-api-demos-for-animation.html
)。

## ApiDemos 和动画有关的示例
（ApiDemos 全部列表见文章最后）

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
