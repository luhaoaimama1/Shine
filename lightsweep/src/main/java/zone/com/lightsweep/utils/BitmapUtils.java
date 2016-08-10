package zone.com.lightsweep.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class BitmapUtils {
    /**
     * Drawable转Bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        if (bd == null)
            throw new IllegalStateException("must  set src ,not  backgroud!");
        return bd.getBitmap();
    }


}
