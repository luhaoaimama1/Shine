package zone.com.lightsweep.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import zone.com.lightsweep.ShineImageView;
import zone.com.lightsweep.ShineView;
import zone.com.lightsweep.utils.ShineViewUtils;

/**
 * Created by fuzhipeng on 16/8/10.
 */
public class ImageViewShineImpl implements ShineCallback {
    private ShineImageView mShineView;
    private Bitmap sourceBm;
    private BitmapShader bmShader;
    private ComposeShader composeShader;
    private LinearGradient mLinearGradient;
    private float moveTotalLength;
    private Bitmap resultBm;
    private Canvas canvas;
    private Matrix matrix;
    private Paint paint;

    @Override
    public void setShineView(ShineView shineView) {
        this.mShineView = (ShineImageView) shineView;
    }

    @Override
    public void initSweepLight() {
        initShader();
        setShader2InitBm();
    }

    @Override
    public void onAnimationUpdate(float percent) {
        matrix.reset();
        matrix.postTranslate(moveTotalLength * percent, 0);
        mLinearGradient.setLocalMatrix(matrix);

        resultBm.eraseColor(Color.TRANSPARENT);//把位图清空
        canvas.setBitmap(resultBm);
        canvas.drawRect(0, 0, sourceBm.getWidth(), sourceBm.getHeight(), paint);

        mShineView.setImageBitmap(resultBm);
    }

    @Override
    public void onAnimationEnd() {
        mShineView.setImageBitmap(sourceBm);
    }

    private void initShader() {
        sourceBm = mShineView.getBm();

        if (mShineView.getReflectWidth() == ShineImageView.DEFAULT_REFLECTION_WIDTH)
            mShineView.setReflectWidth(sourceBm.getWidth());

        bmShader = new BitmapShader(mShineView.getBm(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //get edgeColor
        int edgeColor = ShineViewUtils.getEdgeColor(mShineView.getReflectColor());

        double rorateRadians = Math.toRadians(mShineView.getReflectDegree());
        float leftH = (float) (mShineView.getReflectWidth() * Math.sin(rorateRadians));
        float leftW = (float) (mShineView.getReflectWidth() * Math.cos(rorateRadians));
        moveTotalLength = (float) (mShineView.getReflectWidth() / Math.cos(rorateRadians)
                + sourceBm.getWidth() + sourceBm.getHeight() * Math.tan(rorateRadians)) + 1;

        if (mShineView.getReflectColors()==null) {
            mLinearGradient = new LinearGradient(
                    -leftW, -leftH,//left
                    0, 0,//right
                    new int[]{edgeColor, mShineView.getReflectColor(), edgeColor}, null,
                    Shader.TileMode.CLAMP);
        }else{
            mLinearGradient = new LinearGradient(
                    -leftW, -leftH,//left
                    0, 0,//right
                    mShineView.getReflectColors(), mShineView.getReflectColorsPositions(),
                    mShineView.getReflectTile());
        }
        composeShader = new ComposeShader(bmShader, mLinearGradient, PorterDuff.Mode.SRC_ATOP);
//        composeShader = new ComposeShader(bmShader, mLinearGradient, PorterDuff.Mode.ADD);
    }


    private void setShader2InitBm() {
        resultBm = Bitmap.createBitmap(sourceBm.getWidth(), sourceBm.getHeight(), Bitmap.Config.ARGB_4444);
        canvas = new Canvas(resultBm);
        matrix = new Matrix();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setShader(composeShader);
    }

}
