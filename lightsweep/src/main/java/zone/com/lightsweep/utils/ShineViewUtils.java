package zone.com.lightsweep.utils;

import android.graphics.Color;

/**
 * Created by fuzhipeng on 16/8/10.
 */
public class ShineViewUtils {
    public static void checkReflectRorate(float degree) {
        if (degree >= 90 || degree < 0)
            throw new IllegalArgumentException("reflectDegree must be [0,90)!");
    }

    public static int getEdgeColor(int color) {
        String reflectColorStr = Integer.toHexString(color);
        String rgbStr = reflectColorStr.substring(2);
        return Color.parseColor("#00" + rgbStr);
    }
}
