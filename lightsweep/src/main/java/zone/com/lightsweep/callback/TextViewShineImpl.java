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
    private ShineTextView mShineView;
    private LinearGradient mLinearGradient;
    private float moveTotalLength;
    private Matrix matrix;

    @Override
    public void setShineView(ShineView shineView) {
        this.mShineView = (ShineTextView) shineView;
    }

    @Override
    public void initSweepLight() {
        if (mShineView.getReflectWidth() == ShineImageView.DEFAULT_REFLECTION_WIDTH)
            mShineView.setReflectWidth(mShineView.getWidth());

        TextPaint paint = mShineView.getPaint();
        int edgeColor = mShineView.getTextColor();

        double rorateRadians = Math.toRadians(mShineView.getReflectDegree());
        float leftH = (float) (mShineView.getReflectWidth() * Math.sin(rorateRadians));
        float leftW = (float) (mShineView.getReflectWidth() * Math.cos(rorateRadians));
        moveTotalLength = (float) (mShineView.getReflectWidth() / Math.cos(rorateRadians)
                + mShineView.getWidth() + mShineView.getHeight() * Math.tan(rorateRadians)) + 1;

        if (mShineView.getReflectColors()==null)
            mLinearGradient = new LinearGradient(
                    -leftW, -leftH,//left
                    0, 0,//right
                    new int[]{edgeColor, mShineView.getReflectColor(), edgeColor}, null,
                    Shader.TileMode.CLAMP);
        else
            mLinearGradient = new LinearGradient(
                    -leftW, -leftH,//left
                    0, 0,//right
                    mShineView.getReflectColors(), mShineView.getReflectColorsPositions(),
                    mShineView.getReflectTile());

        paint.setShader(mLinearGradient);
        matrix=new Matrix();
    }

    @Override
    public void onAnimationUpdate(float percent) {
        matrix.reset();
        matrix.postTranslate(moveTotalLength*percent,0);
        mLinearGradient.setLocalMatrix(matrix);
        mShineView.postInvalidate();
    }

    @Override
    public void onAnimationEnd() {
        TextPaint paint = mShineView.getPaint();
        paint.setShader(null);
        mShineView.postInvalidate();
    }
}
