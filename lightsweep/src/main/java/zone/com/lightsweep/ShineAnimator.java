package zone.com.lightsweep;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;

import zone.com.lightsweep.callback.ImageViewShineImpl;
import zone.com.lightsweep.callback.ShineCallback;
import zone.com.lightsweep.callback.TextViewShineImpl;
import zone.com.zanimate.value.ValueAnimatorProxy;
import zone.com.zanimate.value.ValueAnimatorProxyAbstract;

/**
 * Created by fuzhipeng on 16/8/9.
 * todo 休息时间
 */
public class ShineAnimator extends ValueAnimatorProxyAbstract<ShineAnimator> {


    private ShineCallback mShineCallback;


    public ShineAnimator() {
        child = this;
    }

    @Override
    public ValueAnimator initDefaultValueAnimator() {
        return ValueAnimatorProxy.ofFloat(0F, 1F)
                .setRepeatCount(ValueAnimator.INFINITE)
//                .setRepeatMode(ValueAnimator.REVERSE)
                .setDuration(1000).source();
    }

    public ShineAnimator setShineView(ShineView mShineImageView) {
        if (mShineImageView instanceof ShineImageView)
            mShineCallback = new ImageViewShineImpl();
        if (mShineImageView instanceof ShineTextView)
            mShineCallback = new TextViewShineImpl();
        mShineCallback.setShineView(mShineImageView);
        return this;
    }

    @Override
    public ShineAnimator start() {
        mShineCallback.initSweepLight();
        addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float percent = (float) animation.getAnimatedValue();
                mShineCallback.onAnimationUpdate(percent);
            }
        });
        addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mShineCallback.onAnimationEnd();
            }
        });
        return super.start();
    }
}
