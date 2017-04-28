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


    private float strokeWidth;
    private int strokeColor;

    //    private
    public ShapeTextView(Context context) {
        this(context, null);
    }

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setShape();
    }

    private void setShape() {
        setGravity(Gravity.CENTER);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(shape);


        gradientDrawable.setCornerRadius(cornersRadius);

        if (cornersRadius == 0) {
            gradientDrawable.setCornerRadii(new float[]{cornersTopLeft, cornersTopLeft,
                    cornersTopRight, cornersTopRight,
                    cornersBottomLeft, cornersBottomLeft,
                    cornersBottomRight, cornersBottomRight});
        }

        gradientDrawable.setStroke((int) strokeWidth, strokeColor);


        StateListDrawable stateListDrawable = new StateListDrawable();


        gradientDrawable.setColor(solidPressedColor);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, gradientDrawable);

        GradientDrawable pG=new GradientDrawable();
        pG.setColor(solidNormalColor);
        stateListDrawable.addState(new int[]{}, pG);



        setBackground(stateListDrawable);

    }
}
