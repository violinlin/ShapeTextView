# ShapeTextView

> 自定义ShapeTextView 其实就是代码代替xml实现shape的过程

## 效果预览

![enter image description here](http://7xvvky.com1.z0.glb.clouddn.com/blog/view/shapetextview.png)

## 属性的定义

> 每个View都有一些它的特殊属性，在创建新的View的时候，应该考虑到它所具有的属性并在`在res-values-styles`文件中定义View需要的属性。关于属性的介绍可参考[绘制钟表](https://violinlin.github.io/2016/06/27/%E8%87%AA%E5%AE%9A%E4%B9%89View%E2%80%93%E7%BB%98%E5%88%B6%E9%92%9F%E8%A1%A8/)

```xml
 <declare-styleable name="ShapeTextView">
        <attr name="shape" format="enum">
            <enum name="rectangle" value="0" />
            <enum name="oval" value="1" />
        </attr>
        <attr name="solidNormal" format="color" />
        <attr name="solidPressed" format="color" />
        <attr name="cornersRadius" format="dimension" />
        <attr name="cornerTopLeft" format="dimension" />
        <attr name="cornerTopRight" format="dimension" />
        <attr name="cornerBottomLeft" format="dimension" />
        <attr name="cornerBottomRight" format="dimension" />
        <attr name="strokeWidth" format="dimension" />
        <attr name="strokeColor" format="color" />
    </declare-styleable>
```

| 属性      |     类型 |   作用   |
| :-------- | --------:| :------: |
| shape    |   enum |  枚举类型，定义了`oval`和`rectangle`常用的两种  |
| solidNormal | color | 填充色（正常显示）|
| solidPressed | color | 填充色（点击显示）|
| cornersRadius | dimension | 圆角半径（`shape：rectangle`）可用|
| cornerTopLeft、cornerTopRight、cornerBottomLeft、cornerBottomRight | dimension | 自定义每个角的半径，不能同时设置`cornersRadius`属性，或设置`cornersRadius`为0|
| strokeWidth | dimension | 描边的宽度 |
| strokeColor | color | 描边的颜色 |


## 创建`ShapeTextView` 继承`TextView`

在构造方法中获取自定义的属性

```java
  public ShapeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView);
        shape = array.getInteger(R.styleable.ShapeTextView_shape, SHAPE_RECTANGEL);


        solidNormalColor = array.getColor(R.styleable.ShapeTextView_solidNormal, Color.parseColor("#00000000"));
        solidPressedColor = array.getColor(R.styleable.ShapeTextView_solidPressed, Color.parseColor("#00000000"));


        cornersRadius = array.getDimension(R.styleable.ShapeTextView_cornersRadius, 0);

        cornersTopLeft = array.getDimension(R.styleable.ShapeTextView_cornerTopLeft, 0);
        cornersTopRight = array.getDimension(R.styleable.ShapeTextView_cornerTopRight, 0);
        cornersBottomLeft = array.getDimension(R.styleable.ShapeTextView_cornerBottomLeft, 0);
        cornersBottomRight = array.getDimension(R.styleable.ShapeTextView_cornerBottomRight, 0);

        strokeWidth = array.getDimension(R.styleable.ShapeTextView_strokeWidth, 0);

        strokeColor = array.getColor(R.styleable.ShapeTextView_strokeColor, Color.parseColor("#00000000"));
        array.recycle();
    }
```

## 实现`shape`标签

> 使用`GradientDrawable`类在代码中实现`shape`标签中的属性

```java
   // normal state
        GradientDrawable drawableNormal = new GradientDrawable();
        // 设置Shape
        drawableNormal.setShape(shape);
        // 设置圆角半径
        drawableNormal.setCornerRadius(cornersRadius);
        // 圆角半径(每个圆角半径的值)
        if (cornersRadius == 0) {
            drawableNormal.setCornerRadii(new float[]{
                    cornersTopLeft, cornersTopLeft,
                    cornersTopRight, cornersTopRight,
                    cornersBottomRight, cornersBottomRight,
                    cornersBottomLeft, cornersBottomLeft});
        }
        //描边的宽度和颜色
        drawableNormal.setStroke((int) strokeWidth, strokeColor);
        //设置填充色
        drawableNormal.setColor(solidNormalColor);
```

## 实现`selector` 标签

> 使用`StateListDrawable`在代码中实现`selector`标签中的属性

```java
    // 设置背景选择器
        StateListDrawable stateListDrawable = new StateListDrawable();

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);

        stateListDrawable.addState(new int[]{}, drawableNormal);

		// 设置视图的背景
        setBackground(stateListDrawable);
```

## 重写`onDraw()`方法

```
@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setShape();//方法内主要内容为上面代码段
    }
```

