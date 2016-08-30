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

    private boolean isDelay = false;
    private long delayDuration = 0, riseExtra = 0;
    private int delayCount = 0;
    private ValueAnimator delayAnimator;
    private int repeatCountOld;

    /**
     * 每次的delay时间=delayDuration + 动画运行次数 * riseExtra;
     *
     * @param delayDuration
     * @param riseExtra     正负都可以
     * @return
     */
    public ShineAnimator setDelayEvery(long delayDuration, long riseExtra) {
        this.delayDuration = delayDuration;
        this.riseExtra = riseExtra;
        isDelay = true;
        delayAnimator = ValueAnimatorProxy.ofFloat(0F, 1F).setDuration(delayDuration).source();

        repeatCountOld = getRepeatCount();
        setRepeatCount(0);
        addListener(listener);
        return this;

    }

    @Override
    public boolean isRunning() {
        if (delayAnimator != null)
            return super.isRunning() || delayAnimator.isRunning();
        else
            return super.isRunning();
    }

    public ShineAnimator setDelayEvery(long delayDuration) {
        return setDelayEvery(delayDuration, 0);
    }

    public ShineAnimator closeDelayEvery() {
        isDelay = false;
        delayDuration = 0;
        riseExtra = 0;
        delayCount = 0;
        delayAnimator = null;
        setRepeatCount(repeatCountOld);
        return this;
    }

    ;

    //play after 添加坚挺  with不需要
    private AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);

            if (animation == delayAnimator) {//delay结束开始动画
                System.out.println("onAnimationEnd:delayAnimator");
                addListener(listener);
                start();
            } else {//动画结束 另一个动画监听开始;
                System.out.println("onAnimationEnd:this");
                removeListener(listener);
                if (isDelay) {
                    long delayTotal = delayDuration + delayCount * riseExtra;
                    System.out.println("delayTotal:" + delayTotal);
                    if (delayTotal > 0) {
                        delayAnimator.setDuration(delayTotal);
                        delayAnimator.removeListener(listener);
                        delayAnimator.addListener(listener);
                        delayAnimator.start();
                        delayCount++;
                    } else {//小于0的时候
                        closeDelayEvery();
                        start(); //delay结束开始动画
                    }

                }
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            super.onAnimationCancel(animation);
            System.out.println("onAnimationCancel:" + animation);
            if (delayAnimator != null) {
                removeListener(listener);
                delayAnimator.removeListener(listener);
                delayAnimator.cancel();
                closeDelayEvery();
            }
        }
    };


    public ShineAnimator setShineView(ShineView mShineImageView) {
        if (mShineImageView instanceof ShineImageView)
            mShineCallback = new ImageViewShineImpl();
        if (mShineImageView instanceof ShineTextView)
            mShineCallback = new TextViewShineImpl();
        mShineCallback.setShineView(mShineImageView);
        return this;
    }

    @Override
    public ShineAnimator cancel() {
        super.cancel();
        if (delayAnimator != null)
            delayAnimator.cancel();
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
