package zone.com.lightsweep.callback;

import zone.com.lightsweep.ShineView;

/**
 * Created by fuzhipeng on 16/8/10.
 */
public interface  ShineCallback {
     void setShineView(ShineView shineView);
     void initSweepLight();
     void onAnimationUpdate(float percent);
     void onAnimationEnd();
}
