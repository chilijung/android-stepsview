# Android-stepsView
[![Release](https://jitpack.io/v/Canner/android-stepsview.svg)](https://jitpack.io/#Canner/android-stepsview)

A more complete version of android stepsView library.

## Demo

<img src="./demo.png" width="250"/>


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
    compile 'com.github.canner:android-stepsview:<VERSION>'
}
```

## Usage

layout.xml

```xml
 <io.canner.stepsview.StepsView
        android:id="@+id/stepsView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        />
```

you could also set options using chain methods

```java
StepsView mStepsView = (StepsView) findViewById(R.id.stepview);

mStepsView.setLabels(steps)
        .setBarColorIndicator(getContext().getResources().getColor(R.color.material_blue_grey_800))
        .setProgressColorIndicator(getContext().getResources().getColor(R.color.orange))
        .setLabelColorIndicator(getContext().getResources().getColor(R.color.orange))
        .setCompletedPosition(0)
        .drawView();
```

## Setup options in layout

```xml
<io.canner.stepsview.StepsView
    xmlns:custom="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    custom:labels="@array/labels2"      <====== set labels
    custom:labelSize="20"     <================ set label size
    custom:numOfSteps="5"     <================ set steps
    custom:circleRadius="40"     <============= set radius
    custom:progressMargin="150"     <========== set margin
    custom:completePosition="2"     <========== set complete position
    custom:barColor="@color/orange"     <====== set bar color
    custom:labelColor="@color/red"     <======= set label color
    custom:progressColor="@color/blue"     <=== set progress color
    custom:progressTextColor="@color/black"  <= set progress text color
    style="@style/stepsView"
    />
```

## Options

setting up `stepviews` we use chain methods such as example above. And here is more.

### setLabels(String[] labels)

set labels

### setBarColorIndicator(int ResId)

set bar color

### setProgressColorIndicator(int ResId)

set progress color

### setLabelColorIndicator(int ResId)

set label color

### setCompletePosition(int pos)

set complete step position

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

## Improvements

the original version is https://github.com/anton46/Android-StepsView with many improvements.

- more concise text position and progress bar position
- make labels align to center
- add number in progress
- add more customize options
- support syntax in xml layout

## License

Apache 2.0

