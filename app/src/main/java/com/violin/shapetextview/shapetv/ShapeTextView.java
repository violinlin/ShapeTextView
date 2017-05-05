package com.violin.shapetextview.shapetv;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.violin.shapetextview.R;

/**
 * Created by whl on 2017/4/28.
 */

public class ShapeTextView extends TextView {
    private final int SHAPE_RECTANGEL = 0;
    private final int SHAPE_OVAL = 1;

    private int shape;
    private int solidNormalColor;
    private int solidPressedColor;
    private float cornersRadius;
    private float cornersTopLeft;
    private float cornersTopRight;
    private float cornersBottomLeft;
    private float cornersBottomRight;

    //    渐变颜色属性
    private int gradientStartColor;
    private int gradientCenterCcolor;
    private int gradientEndColor;

    private int gradientOrientation;

    private float strokeWidth;
    private int strokeColor;

    private int defaultColor = Color.parseColor("#00000000");
    private GradientDrawable.Orientation[] orientations;

    public ShapeTextView(Context context) {
        this(context, null);
    }

    public ShapeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView);
        shape = array.getInteger(R.styleable.ShapeTextView_shape, SHAPE_RECTANGEL);


        solidNormalColor = array.getColor(R.styleable.ShapeTextView_solidNormal, defaultColor);
        solidPressedColor = array.getColor(R.styleable.ShapeTextView_solidPressed, defaultColor);


        cornersRadius = array.getDimension(R.styleable.ShapeTextView_cornersRadius, 0);

        cornersTopLeft = array.getDimension(R.styleable.ShapeTextView_cornerTopLeft, 0);
        cornersTopRight = array.getDimension(R.styleable.ShapeTextView_cornerTopRight, 0);
        cornersBottomLeft = array.getDimension(R.styleable.ShapeTextView_cornerBottomLeft, 0);
        cornersBottomRight = array.getDimension(R.styleable.ShapeTextView_cornerBottomRight, 0);

        strokeWidth = array.getDimension(R.styleable.ShapeTextView_strokeWidth, 0);

        strokeColor = array.getColor(R.styleable.ShapeTextView_strokeColor, defaultColor);

        gradientStartColor = array.getColor(R.styleable.ShapeTextView_gradientStartColor, defaultColor);

        gradientCenterCcolor = array.getColor(R.styleable.ShapeTextView_gradientCenterColor, defaultColor);

        gradientEndColor = array.getColor(R.styleable.ShapeTextView_gradientEndColor, defaultColor);

        TypedArray orientationArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView);

        gradientOrientation = orientationArray.getInteger(R.styleable.ShapeTextView_gradientOrientation, 6);

        array.recycle();


        orientations = new GradientDrawable.Orientation[]{
                GradientDrawable.Orientation.TOP_BOTTOM,
                GradientDrawable.Orientation.TR_BL,
                GradientDrawable.Orientation.RIGHT_LEFT,
                GradientDrawable.Orientation.BR_TL,
                GradientDrawable.Orientation.BOTTOM_TOP,
                GradientDrawable.Orientation.BL_TR,
                GradientDrawable.Orientation.LEFT_RIGHT,
                GradientDrawable.Orientation.TL_BR
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setShape();
    }

    private void setShape() {
        setGravity(Gravity.CENTER);
        setClickable(true);
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
        if (solidNormalColor != defaultColor) {
            drawableNormal.setColor(solidNormalColor);
        } else {
//            设置渐变色
            int[] gradientColors;
            if (gradientStartColor != defaultColor && gradientEndColor != defaultColor) {
                gradientColors = new int[]{gradientStartColor, gradientEndColor};
                if (gradientCenterCcolor != defaultColor) {
                    gradientColors = new int[]{gradientStartColor, gradientCenterCcolor, gradientEndColor};
                }
                drawableNormal.setColors(gradientColors);


                drawableNormal.setOrientation(orientations[gradientOrientation]);
            } else {
                drawableNormal.setColor(solidNormalColor);
            }

        }


        // pressed state
        GradientDrawable drawablePressed = new GradientDrawable();
        drawablePressed.setShape(shape);
        drawablePressed.setCornerRadius(cornersRadius);
        if (cornersRadius == 0) {
            drawablePressed.setCornerRadii(new float[]{
                    cornersTopLeft, cornersTopLeft,
                    cornersTopRight, cornersTopRight,
                    cornersBottomRight, cornersBottomRight,
                    cornersBottomLeft, cornersBottomLeft});
        }

        drawablePressed.setStroke((int) strokeWidth, strokeColor);

        drawablePressed.setColor(solidPressedColor);

        // 设置背景选择器
        StateListDrawable stateListDrawable = new StateListDrawable();

        if (solidPressedColor != defaultColor) {
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
        }

        stateListDrawable.addState(new int[]{}, drawableNormal);

        setBackground(stateListDrawable);

    }
}
