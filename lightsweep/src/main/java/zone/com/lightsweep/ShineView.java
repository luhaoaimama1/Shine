package zone.com.lightsweep;

import android.graphics.Color;

/**
 * Created by fuzhipeng on 16/8/10.
 */
public interface ShineView {
    int DEFAULT_REFLECTION_COLOR = Color.WHITE;
    float DEFAULT_REFLECTION_WIDTH = -1;
    float DEFAULT_REFLECTION_RORATE = 0;


    int getReflectColor();

    float getReflectDegree();

    float getReflectWidth();


     void setReflectColor(int reflectColor);

    /**
     * [0,-90] 0表示竖直 90表示横向,顺时针旋转
     *
     * @param degree
     */
     void setReflectRorate(float degree) ;


     void setReflectWidth(float reflectWidth);
}
