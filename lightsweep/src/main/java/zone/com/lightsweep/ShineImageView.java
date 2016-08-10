package zone.com.lightsweep;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import zone.com.lightsweep.utils.BitmapUtils;
import zone.com.lightsweep.utils.ShineViewUtils;

public class ShineImageView extends ImageView implements ShineView {


    private Bitmap bm;

    private int reflectColor;
    private float reflectDegree, reflectWidth;

    public ShineImageView(Context context) {
        super(context);
    }

    public ShineImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShineImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ShineImageView, 0, 0);
            if (a != null) {
                try {
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

    private void initializeBitmap() {
        if (bm == null)
            bm = BitmapUtils.drawable2Bitmap(getDrawable());
    }

    @Override
    public void setReflectColor(int reflectColor) {
        this.reflectColor = reflectColor;
    }


    /**
     * [0,-90] 0表示竖直 90表示横向,顺时针旋转
     *
     * @param degree
     */
    @Override
    public void setReflectRorate(float degree) {
        ShineViewUtils.checkReflectRorate(degree);
        this.reflectDegree = degree;
    }

    @Override
    public void setReflectWidth(float reflectWidth) {
        this.reflectWidth = reflectWidth;
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        initializeBitmap();
    }


    public Bitmap getBm() {
        initializeBitmap();
        return bm;
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

}