# AndroidCustomViewsDemo
这是 Android 自定义控件的一个 Demo 练习项目. modules 列表如下:

- <a href="#customrectangle">CustomRectangle 自定义矩形</a>
- <a href="#customcircle">CustomCircle 自定义圆</a>
- <a href="#progresscircle">ProgressCircle 带进度指示功能的自定义圆</a>
- <a href="#dragandmoveview">DragAndMoveView 跟随手指一起移动的控件</a>
- <a href="#myviewpagerdemo">MyViewPagerDemo 仿Android原生控件ViewPager制作的一个简易版的 ViewPager demo</a>






---

# <span id="customrectangle">CustomRectangle</span>

### 1. **练习的目标:**   
- 一个继承自View类的控件, 需要让 `onMeasure()` 方法处理layout文件中为长宽设置为 `wrap_content` 时的情况 (详情见 
[CustomRectangle1.java][]).
原因请见<a href="http://blog.csdn.net/clevergump/article/details/50545257" target="_blank">这篇文章</a>的分析.
- 自定义属性的用法. (详情见 [CustomRectangle2.java][])
- 绘制矩形时, `Canvas.drawRect(float left, float top, float right, float bottom, Paint paint)` 
方法各参数<font color="red">含义</font>的正确理解. (详情见 [CustomRectangle2.java][])

### 2. **注意事项:**   
- 千万不要尝试在代码中直接去获取我们为android系统自带的那些属性在layout文件中所设置的数值, 
尤其是宽高, 因为我们设置的宽高只是我们想要的宽高, 但实际上系统在绘制该控件时可能无法满足我们的要求, 
甚至如果我们设置的宽高是个超级大的数值, 那么也显然无法满足, 其实控件的实际宽高是由我们在layout文件中
设置的想要的宽高和父控件对该控件的限制共同计算出的一个折中数值, 我们可以通过 `getMeasuredWidth()`, 
`getMeasuredHeight()`, `getWidth()`, `getHeight()` 方法来获取到这个折中的数值.
- Canvas.drawRect(float left, float top, float right, float bottom, Paint paint)方法是用来绘制矩形的, 
但是, 我们需要正确理解前四个参数(四个坐标值)的含义.   
    - 如果是使用 `Paint.Style.Fill` 的 `Paint` 绘制矩形, 那么需注意:   
    这四个参数的含义是相对于该矩形自身左上角那个点的水平或垂直距离, 而不是相对于其父控件左上角的点或者手机屏幕左上角的点的距离.   
    - 如果是使用 `Paint.Style.Stroke` 的 `Paint` 绘制矩形(也就是要绘制一个有边框厚度的矩形), 那么需注意:   
    这四个参数的含义是相对于该矩形四条边框各自的中线所组成的矩形的左上角那个点的水平或垂直距离, 而不是相对于其父控件左上角的点或者手机屏幕左上角的点的距离.   

    我们可以用下图来描述上述两种情况:   
    ![canvas.drawRect()的参数说明][CustomRectangle 自定义矩形-canvas.drawRect()的参数说明] 
    
    - 如果是使用 `Paint.Style.Fill` 的 `Paint` 绘制图中内部的那个小矩形(也就是以B点作为左上角的那四条黑线所组成的矩形), 那么该方法中的四个坐标参数就是相对于B点的距离.
    - 如果是使用 `Paint.Style.Stroke` 的 `Paint` 绘制图中用灰色虚斜线表示的矩形, 由于该矩形的四条边都是有厚度的, 所以该方法中的四个坐标参数就是相对于图中红色虚线表示的矩形的左上角那个点(C点)的距离.   

    可以进行对比记忆的是:   
`View` 类的 `getLeft()`, `getRight()`, `getTop()`, `getBottom()` 方法得到的坐标是四条边相对于**<font color="red">父控件</font>**左上角的点的距离.

### 3. **运行效果图:**   
![运行效果图][CustomRectangle 自定义矩形-运行效果图]




---

# <span id="customcircle">CustomCircle</span>

### 1. **注意事项:**   
- 注意圆的半径的正确计算.   
    如果要使用 `Paint.Style.Stroke` 的 `Paint` 来绘制一个有一定边框厚度的圆(此时这个圆属于圆环), 那么在调用 `Canvas.drawCircle(float cx, float cy, float radius, Paint paint)` 方法时, 圆半径 radius 是等于圆心到这个圆环内外两个圆边框正中间的那个中心圆的距离.           
    我们可以用下图来帮助理解:   
    ![使用Stroke的画笔绘制圆][CustomCircle 自定义圆-使用Stroke的画笔绘制圆]   
    如上图所示, 如果我们要使用 `Stroke` 样式的 `Paint` 来绘制上图中边框A和B组成的圆环, 那么我们在调用 `Canvas.drawCircle(float cx, float cy, float radius, Paint paint)` 方法时, 需要传入的半径 `radius` 的数值其实就等于AB两个圆之间的那个红色虚线圆C的半径.

   
### 2. **运行效果图:**   
![运行效果图][CustomCircle 自定义圆-运行效果图]

---

# <span id="progresscircle">ProgressCircle</span>   

A custom view for showing progress-percentage. Click **[HERE](https://github.com/clevergump/Android-ProgressCircle)** for more details.   

一个能显示进度百分比的自定义圆. 点击**[这里](https://github.com/clevergump/Android-ProgressCircle)**查看详情.

![ProgressCircle-running_image](https://github.com/clevergump/Android-ProgressCircle/blob/master/sample/image/progresscircle_in_image_loading.gif)

---

# <span id="dragandmoveview">DragAndMoveView</span>
  
这是一个按照手指的滑动轨迹进行移动的自定义控件的 Demo. 实现思路是改变控件的 `MarginLayoutParams` 
的 `leftMargin` 和 `topMargin`. 这种实现方式移动的是控件自身, 所以不论移动到什么位置, 该控件都依然可以响应交互事件. 当然, 也有其他实现方式可以完成类似功能. 其中, 要注意两种情况:
- View动画, 这种方式虽然也能实现控件跟随手指的移动效果, 但其实View动画只是让控件的一个幻影在发生移动, 而控件本身却没有发生移动, 所以在新位置的控件(其实是其幻影)将无法响应交互事件.
- `scrollTo()`, `scrollBy()`: 由于这两个方法移动的是控件的内容, 不是控件本身, 所以如果直接对要移动的控件调用这些方法进行移动, 那么实际上控件本身并不会移动. 只有在要移动的控件外层嵌套一个 ViewGroup, 然后对该ViewGroup调用这些方法, 才能让处于该 ViewGroup内的控件发生移动. 这种方式的缺点是: 灵活性略差, 因为控件只能在 ViewGroup范围内进行移动. 关于 `scrollTo()` 和 `scrollBy()` 的详情示例, 请见[这里](https://github.com/clevergump/Android-Test/tree/master/ScrollTest).

### 该 demo 的介绍   

1. 该 demo 以 [DragAndMoveButton][] 控件为例, 来描述控件随手指的拖动轨迹移动的场景. 
2. 在设置该控件的布局参数时, 要注意某些特殊的参数设置会影响到该控件的自由移动. 例如: 在 `RelativeLayout` 中设置该控件 `android:layout_centerInParent="true"`, 那么该控件将会被一直锁定在屏幕的中央位置而无法跟随手指的移动轨迹自由移动.
3. 在布局文件中的使用:   
	该控件包含如下的自定义属性:
	```xml
    <declare-styleable name="DragAndMoveButton">
        <attr name="minLeftMargin" format="dimension"/>
        <attr name="minTopMargin" format="dimension"/>
        <attr name="minRightMargin" format="dimension"/>
        <attr name="minBottomMargin" format="dimension"/>
    </declare-styleable>
	```
	上述自定义属性的含义是:
	- minLeftMargin: 最小的 leftMargin
	- minTopMargin: 最小的 topMargin
	- minRightMargin: 最小的 rightMargin
	- minBottomMargin: 最小的 bottomMargin

	你可以在你的布局文件中, 按照如下方式来使用该控件:
	```xml
	<com.example.drag_and_move_view.widget.DragAndMoveButton
        android:id="@+id/drag_btn"
        android:layout_width="200dp"
        android:layout_height="50dp"
        android:text="DragAndMoveButton"
        android:gravity="center"
        android:layout_marginTop="50dp"
        android:layout_marginLeft="60dp"
        DragAndMoveButton:minLeftMargin="20dp"
        DragAndMoveButton:minTopMargin="20dp"
        DragAndMoveButton:minRightMargin="20dp"
        DragAndMoveButton:minBottomMargin="20dp"/>
	```


### 通过该demo的练习, 要掌握的知识点:
1. touchSlop (系统能够识别到的最小滑动距离) 的获取方式:

	```java
	int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	```
	只有当滑动距离大于 touchSlop 的时候, 才去响应滑动事件, 这能增强用户体验.
2. `MarginLayoutParams` 这个 `LayoutParams` 的子类在控件连续移动过程中的用法, 欲了解该类的具体细节请查阅源码.
3. 注意 `getX()`与 `getRawX()`, `getY()`与 `getRawY()` 的区别:

	- `getX()` 和 `getY()` 表示手指当前的位置相对于当前控件左上角的 x, y坐标.
	- `getRawX()` 和 `getRawY()` 表示手指当前的位置相对于手机屏幕左上角的 x, y坐标, **既不是相对于该控件左上角的坐标, 也不是相对于该控件所在父容器的左上角的坐标.**
4. View 的加载和 Activity 的加载是异步的, 所以在 Activity中不能直接去获取 View 的宽高以及四条边的坐标, 因为获取时, 该 View 有可能还未加载完毕, 还未加载完毕的 View 所获取到的宽高以及四条边的坐标都为0. 为了确保获取某个 View 的宽高以及四条边的坐标的操作, 一定是在该 View 加载完毕后才去进行, 可以在该 View 的如下回调方法中去获取:

	```java
	view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            int left = view.getLeft();
            int top = view.getTop();
            int right = view.getRight();
            int bottom = view.getBottom();
        }
    });
	```

### 运行效果图:   

![运行效果图][DragAndMoveView 自定义随手指的拖动一起移动的控件-运行效果图]

---

# <span id="myviewpagerdemo">MyViewPagerDemo</span>
A simple version of Android official ViewPager just for learning purpose, where bugs may exist and more enhancement and extensible functions will be added in the future. Click **[HERE](https://github.com/clevergump/AndroidDemo-MyViewPagerDemo)** for more details.   

仿Android原生控件ViewPager制作的一个简易版的 ViewPager demo, 会进一步完善. 当然目前的版本可能存在一定的 bug. 点击**[这里](https://github.com/clevergump/AndroidDemo-MyViewPagerDemo)**查看详情.

### 运行效果图:   
![running_effect](https://github.com/clevergump/AndroidDemo-MyViewPagerDemo/blob/master/app/image/running_effect.gif)


---


[CustomRectangle1.java]: CustomRectangle/src/main/java/com/example/custom_rectangle/widget/CustomRectangle1.java
[CustomRectangle2.java]: CustomRectangle/src/main/java/com/example/custom_rectangle/widget/CustomRectangle2.java
[CustomRectangle 自定义矩形-canvas.drawRect()的参数说明]: CustomRectangle/image/canvas.drawRect()的参数说明.png
[CustomRectangle 自定义矩形-运行效果图]: CustomRectangle/image/运行效果图.png
[CustomCircle 自定义圆-使用Stroke的画笔绘制圆]: CustomCircle/image/使用Stroke的画笔绘制圆.png
[CustomCircle 自定义圆-运行效果图]: CustomCircle/image/运行效果图.gif
[DragAndMoveButton]: DragAndMoveView/src/main/java/com/example/drag_and_move_view/widget/DragAndMoveButton.java
[DragAndMoveView 自定义随手指的拖动一起移动的控件-运行效果图]: DragAndMoveView/image/running_effect_image.gif

> ***GitHub Markdown tips:***   
在使用超链接在文档内部跳转时, 超链接#后边的内容在Github上是有特殊要求的:   
1. 所有 `<span>` 标签的id属性的数值中所有英文都会自动转为小写字母. 所以, 我们在写文档内跳转的超链接时, 需写成如下形式: `<a href="#全部小写字母">`. 例如:   
```html
<a href="#customrectangle">CustomRectangle 自定义矩形</a>
<a href="#customcircle">CustomCircle 自定义圆</a>
```   
<br/>
2. 所有 `<span>` 标签对之间的文字内容如有中文, 则都会在id属性的数值中自动添加该中文, 具体格式待验证. 本文使用的是
```html
<a href="customrectangle-自定义矩形">CustomRectangle 自定义矩形</a>
<a href="#customcircle-自定义圆">CustomCircle 自定义圆</a>
```
