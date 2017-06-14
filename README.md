# Android-stepsView
[![Release](https://jitpack.io/v/Canner/android-stepsview.svg)](https://jitpack.io/#Canner/android-stepsview)

Yet another android stepsView library.

a fork verson of https://github.com/anton46/Android-StepsView

## Install

add your `build.grade`:

```groovy
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
dependencies {
    compile 'com.github.canner:android-stepsview:1.1.0'
}
```

## Usage


```xml
 <io.canner.stepsview.StepsView
        android:id="@+id/stepsView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
```

```java
mStepsView.setLabels(steps)
        .setBarColorIndicator(getContext().getResources().getColor(R.color.material_blue_grey_800))
        .setProgressColorIndicator(getContext().getResources().getColor(R.color.orange))
        .setLabelColorIndicator(getContext().getResources().getColor(R.color.orange))
        .setCompletedPosition(0)
        .drawView();
```

## More options

setting up `stepviews` we use chain methods such as example above. And here is more.

### setLabelTextSize(float size)

set label size

### setProgressStrokeWidth(float width)

set the stroke width in between step progress.

### setProgressMargins(float margin)

set margins of the view

### setCircleRadius(float radius)

set the steps' radius.

### setProgressTextColor(int textColor)

set text color in the step.

### hideProgressText(boolean hide)

hide the text in the progress.

## Demo

<img src="./demo.png" width="250"/>


## Improvements

the original version is https://github.com/anton46/Android-StepsView with many improvements.

- more concise text position and progress bar position
- make labels align to center
- add number in progress
- add more customize options

## License

Apache 2.0

