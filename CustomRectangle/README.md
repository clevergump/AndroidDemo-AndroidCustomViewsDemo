# CustomRectangle:

1. **项目介绍:**   
这是继承自View类的自定义控件中最简单的一个练习. 该项目主要练习的是: 一个继承自View类的控件, 需要让 onMeasure()方法处理layout文件中为长宽设置为 wrap_content 时的情况.

2. **需要注意的是:**   
千万不要尝试直接去获取android系统自带的那些属性的数值, 尤其是宽高, 因为控件的实际宽高是由layout文件设置的我们想要的宽高和父控件对该控件的限制共同计算出的一个折中数值, 我们可以通过 `getMeasuredWidth()`, `getMeasuredHeight()`, `getWidth()`, `getHeight()`方法来获取到这个折中的数值.

3. **为避免犯类似错误, 需要记住以下结论:**   
    - layout文件设置的宽高只是我们想要的宽高, 而不是最终的宽高. 即使我们设置的是具体的数值.
    - 控件的实际宽高一定是由layout文件中设置的我们想要的宽高值, 与父控件对他的限制, 二者共同折中后得出的结果.
    - 在自定义控件中, 要获取控件宽高在折中后的数值, 可以通过 `getMeasuredWidth()`, `getMeasuredHeight()`, `getWidth()`, `getHeight()`方法来获取.