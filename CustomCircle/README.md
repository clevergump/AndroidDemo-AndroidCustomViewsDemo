## <span id="CustomCircle">CustomCircle 自定义圆</span>

####1. **注意事项:**   
- 注意圆的半径的正确计算.   
    如果要使用 `Paint.Style.Stroke` 的 `Paint` 来绘制一个有一定边框厚度的圆(此时这个圆属于圆环), 那么在调用 `Canvas.drawCircle(float cx, float cy, float radius, Paint paint)` 方法时, 圆半径 radius 是等于圆心到这个圆环内外两个圆边框正中间的那个中心圆的距离.           
    我们可以用下图来帮助理解:   
    ![使用Stroke的画笔绘制圆][CustomCircle 自定义圆-使用Stroke的画笔绘制圆]   
    如上图所示, 如果我们要使用 `Stroke` 样式的 `Paint` 来绘制上图中边框A和B组成的圆环, 那么我们在调用 `Canvas.drawCircle(float cx, float cy, float radius, Paint paint)` 方法时, 需要传入的半径 `radius` 的数值其实就等于AB两个圆之间的那个红色虚线圆C的半径.
   
####2. **运行效果图:**   
![运行效果图][CustomCircle 自定义圆-运行效果图]

---




[CustomCircle 自定义圆-使用Stroke的画笔绘制圆]: https://github.com/clevergump/AndroidDemo-CustomViewsDemo/blob/master/CustomCircle/image/%E4%BD%BF%E7%94%A8Stroke%E7%9A%84%E7%94%BB%E7%AC%94%E7%BB%98%E5%88%B6%E5%9C%86.png
[CustomCircle 自定义圆-运行效果图]: image/运行效果图.gif