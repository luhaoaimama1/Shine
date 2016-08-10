package zone.com.lightsweep;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import zone.com.lightsweep.utils.ShineViewUtils;

public class ShineTextView extends TextView implements ShineView {

    private int reflectColor;
    private int textColor;
    private float reflectDegree, reflectWidth;

    public ShineTextView(Context context) {
        this(context,null);
    }

    public ShineTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ShineImageView, 0, 0);
            if (a != null) {
                try {
                    textColor = a.getColor(R.styleable.ShineImageView_android_textColor, Color.BLACK);
                    setTextColor(textColor);
                    reflectColor = a.getColor(R.styleable.ShineImageView_reflectionColor, DEFAULT_REFLECTION_COLOR);
                    reflectWidth = a.getFloat(R.styleable.ShineImageView_reflectionWidth, DEFAULT_REFLECTION_WIDTH);
                    reflectDegree = a.getFloat(R.styleable.ShineImageView_reflectionRorate, DEFAULT_REFLECTION_RORATE);
                    ShineViewUtils.checkReflectRorate(reflectDegree);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    a.recycle();
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    public void setTextColor(int textColor) {
        super.setTextColor(textColor);
        this.textColor = textColor;
    }

    //getPaint
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public int getReflectColor() {
        return reflectColor;
    }

    @Override
    public float getReflectDegree() {
        return reflectDegree;
    }

    @Override
    public float getReflectWidth() {
        return reflectWidth;
    }

    @Override
    public void setReflectColor(int reflectColor) {
        this.reflectColor = reflectColor;
    }

    @Override
    public void setReflectRorate(float degree) {
        ShineViewUtils.checkReflectRorate(degree);
        this.reflectDegree = degree;
    }

    @Override
    public void setReflectWidth(float reflectWidth) {
        this.reflectWidth = reflectWidth;
    }

    public int getTextColor() {
        return textColor;
    }
}