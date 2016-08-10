package zone.com.lightsweep.callback;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;

import zone.com.lightsweep.ShineImageView;
import zone.com.lightsweep.ShineTextView;
import zone.com.lightsweep.ShineView;

/**
 * Created by fuzhipeng on 16/8/10.
 */
public class TextViewShineImpl implements  ShineCallback {
    private ShineTextView mShineImageView;
    private LinearGradient mLinearGradient;
    private float moveTotalLength;
    private Matrix matrix;

    @Override
    public void setShineView(ShineView shineView) {
        this.mShineImageView= (ShineTextView) shineView;
    }

    @Override
    public void initSweepLight() {
        if (mShineImageView.getReflectWidth() == ShineImageView.DEFAULT_REFLECTION_WIDTH)
            mShineImageView.setReflectWidth(mShineImageView.getWidth());

        TextPaint paint = mShineImageView.getPaint();
        int edgeColor =mShineImageView.getTextColor();

        double rorateRadians = Math.toRadians(mShineImageView.getReflectDegree());
        float leftH = (float) (mShineImageView.getReflectWidth() * Math.sin(rorateRadians));
        float leftW = (float) (mShineImageView.getReflectWidth() * Math.cos(rorateRadians));
        moveTotalLength = (float) (mShineImageView.getReflectWidth() / Math.cos(rorateRadians)
                + mShineImageView.getWidth() + mShineImageView.getHeight() * Math.tan(rorateRadians)) + 1;

        mLinearGradient = new LinearGradient(
                -leftW, -leftH,//left
                0, 0,//right
                new int[]{edgeColor, mShineImageView.getReflectColor(), edgeColor}, null,
                Shader.TileMode.CLAMP);
        paint.setShader(mLinearGradient);
        matrix=new Matrix();
    }

    @Override
    public void onAnimationUpdate(float percent) {
        matrix.reset();
        matrix.postTranslate(moveTotalLength*percent,0);
        mLinearGradient.setLocalMatrix(matrix);
        mShineImageView.postInvalidate();
    }

    @Override
    public void onAnimationEnd() {
        TextPaint paint = mShineImageView.getPaint();
        paint.setShader(null);
        mShineImageView.postInvalidate();
    }
}
