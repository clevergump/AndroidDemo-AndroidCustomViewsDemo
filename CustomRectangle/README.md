# CustomRectangle 自定义矩形

一. **练习的目标:**   

1. 一个继承自View类的控件, 需要让 `onMeasure()` 方法处理layout文件中为长宽设置为 `wrap_content` 时的情况. (详情见 `CustomRectangle1.java`)
原因请见<a href="http://blog.csdn.net/clevergump/article/details/50545257" target="_blank">这篇文章</a>的分析.
2. 自定义属性的用法. (详情见 `CustomRectangle2.java`)
3. 绘制矩形时, `Canvas.drawRect(float left, float top, float right, float bottom, Paint paint)` 
方法各参数<font color="red">含义</font>的正确理解. (详情见 `CustomRectangle2.java`)
<br/><br/>

二. **注意事项:**   

1. 千万不要尝试在代码中直接去获取我们为android系统自带的那些属性在layout文件中所设置的数值, 
尤其是宽高, 因为我们设置的宽高只是我们想要的宽高, 但实际上系统在绘制该控件时可能无法满足我们的要求, 
甚至如果我们设置的宽高是个超级大的数值, 那么也显然无法满足, 其实控件的实际宽高是由我们在layout文件
中设置的想要的宽高和父控件对该控件的限制共同计算出的一个折中数值, 我们可以通过 `getMeasuredWidth()`, 
`getMeasuredHeight()`, `getWidth()`, `getHeight()` 方法来获取到这个折中的数值.
2. Canvas.drawRect(float left, float top, float right, float bottom, Paint paint)方法是用来绘制矩形的, 
但是, 我们需要正确理解前四个参数(四个坐标值)的含义.   
    - 如果是使用 `Paint.Style.Fill` 的 `Paint` 绘制矩形, 那么需注意   
      这四个参数的含义是相对于该矩形自身的左上角那个点的水平或垂直距离, 而不是相对于其父控件或者手机屏幕四条边的距离.
    - 如果是使用 `Paint.Style.Stroke` 的 `Paint` 绘制矩形(也就是要绘制一个有边框厚度的矩形), 那么需注意:   
      这四个参数的含义是相对于该矩形四条边框各自的中线所组成的矩形的左上角那个点的水平或垂直距离, 而不是相对于其父控件或者手机屏幕四条边的距离.   

    我们可以用下图来描述上述两种情况:   
    ![canvas.drawRect()的参数说明](image/canvas.drawRect()参数说明.png)   
    
    - 如果是使用 `Paint.Style.Fill` 的 `Paint` 绘制图中内部的那个小矩形, 
    那么该方法中的四个坐标参数就是相对于B点的值.
    - 如果是使用 `Paint.Style.Stroke` 的 `Paint` 绘制图中用虚斜线表示的矩形, 
    由于该矩形的边是有厚度的, 所以该方法中的四个坐标参数就是相对于图中红色虚线表示的
    矩形的左上角那个点(C点)的值
    