package zone.com.shine;

import android.graphics.Color;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import zone.com.lightsweep.ShineAnimator;
import zone.com.lightsweep.ShineImageView;
import zone.com.lightsweep.ShineTextView;
import zone.com.lightsweep.ShineView;

public class MainActivity extends AppCompatActivity {

    private ShineImageView shine;
    private ShineTextView shineText;
    private ShineTextView tv_shine_colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shine = (ShineImageView) findViewById(R.id.abc);
        shineText = (ShineTextView) findViewById(R.id.tv_shine);
        tv_shine_colors = (ShineTextView) findViewById(R.id.tv_shine_colors);
        tv_shine_colors.setReflectColors(new int[]{Color.RED, Color.YELLOW, Color.BLUE, Color.CYAN}, Shader.TileMode.MIRROR);
//        shine.setImageResource(R.drawable.aa);
//        shine.setReflectColor(Color.BLUE);
//        shine.setReflectRorate(30);
//        shine.setReflectWidth(100);
        findViewById(R.id.button).setOnClickListener(onclickListener);
        findViewById(R.id.stop).setOnClickListener(onclickListener);
    }

    public ShineAnimator s1;
    public ShineAnimator s;
    public ShineAnimator s3;

    View.OnClickListener onclickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button:
                    s = run(s, shine);//不知道为啥必须s=  不等就不赋值 ；是坑
                    s1 = run(s1, shineText);
                    s3 = run(s3, tv_shine_colors);
                    break;
                case R.id.stop:
                    stop(s);
                    stop(s1);
                    stop(s3);
                    break;
            }

        }

        private ShineAnimator run(ShineAnimator sP, ShineView shine) {
            if (sP != null) {
                if (!sP.isRunning())
                    sP.start();
            } else {
//                sP = new ShineAnimator().setShineView(shine).setDelayEvery(2000).setInterpolator(new LinearInterpolator()).start();
//                sP = new ShineAnimator().setShineView(shine).setDelayEvery(2000, 1000).setInterpolator(new LinearInterpolator()).start();
                sP = new ShineAnimator().setShineView(shine).setDelayEvery(2000, -500).setInterpolator(new LinearInterpolator()).start();
//                sP = new ShineAnimator().setShineView(shine).setInterpolator(new LinearInterpolator()).start();
            }
            return sP;
        }

        private void stop(ShineAnimator sP) {
            if (sP != null&&sP.isRunning())
                sP.cancel();
        }
    };
}
