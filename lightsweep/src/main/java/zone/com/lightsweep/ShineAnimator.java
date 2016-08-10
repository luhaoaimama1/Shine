package zone.com.lightsweep;
import android.view.animation.Interpolator;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import zone.com.lightsweep.callback.ImageViewShineImpl;
import zone.com.lightsweep.callback.ShineCallback;
import zone.com.lightsweep.callback.TextViewShineImpl;

/**
 * Created by fuzhipeng on 16/8/9.
 * todo 休息时间
 */
public class ShineAnimator {
    private static final int DEFAULT_REPEAT_COUNT = android.animation.ValueAnimator.INFINITE;
    private static final long DEFAULT_DURATION = 1000;
    private static final long DEFAULT_START_DELAY = 0;

    private int repeatCount;
    private long startDelay;
    private long duration;

    private ValueAnimator animator;

    private ShineCallback mShineCallback;
    private Interpolator mInterpolator;


    public ShineAnimator() {
        animator = ValueAnimator.ofFloat(0F, 1F);
        startDelay = DEFAULT_START_DELAY;
        duration = DEFAULT_DURATION;
        repeatCount = DEFAULT_REPEAT_COUNT;
    }

    public void setShineView(ShineView mShineImageView) {
        if (mShineImageView instanceof ShineImageView)
            mShineCallback = new ImageViewShineImpl();
        if (mShineImageView instanceof ShineTextView)
            mShineCallback = new TextViewShineImpl();
        mShineCallback.setShineView(mShineImageView);
    }

    public void start() {
        mShineCallback.initSweepLight();

        animator.setInterpolator(mInterpolator);
        animator.setStartDelay(startDelay);
        animator.setDuration(duration);
        animator.setRepeatCount(repeatCount);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float percent = (float) animation.getAnimatedValue();
                mShineCallback.onAnimationUpdate(percent);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mShineCallback.onAnimationEnd();
            }
        });
        animator.start();
    }

    public void cancel() {
        animator.cancel();
    }


    public int getRepeatCount() {
        return repeatCount;
    }

    public ShineAnimator setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public ShineAnimator setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public long getStartDelay() {
        return startDelay;
    }

    public ShineAnimator setStartDelay(long startDelay) {
        this.startDelay = startDelay;
        return this;
    }
    public ShineAnimator setInterpolator(/*Time*/Interpolator value) {
        mInterpolator    = value;
        return this;
    }

    public Interpolator getInterpolator() {
        return mInterpolator;
    }
}
