package zone.com.shine;
import android.graphics.Color;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import zone.com.lightsweep.ShineAnimator;
import zone.com.lightsweep.ShineImageView;
import zone.com.lightsweep.ShineTextView;
import zone.com.lightsweep.ShineView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ShineImageView shine = (ShineImageView) findViewById(R.id.abc);
        final ShineTextView shineText = (ShineTextView) findViewById(R.id.tv_shine);
        final ShineTextView tv_shine_colors = (ShineTextView) findViewById(R.id.tv_shine_colors);
        tv_shine_colors.setReflectColors(new int[]{Color.RED,Color.YELLOW,Color.BLUE,Color.CYAN},Shader.TileMode.MIRROR);
//        shine.setImageResource(R.drawable.aa);
//        shine.setReflectColor(Color.BLUE);
//        shine.setReflectRorate(30);
//        shine.setReflectWidth(100);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            public ShineAnimator s1;
            public ShineAnimator s;
            public ShineAnimator s3;

            @Override
            public void onClick(View view) {
                run(s,shine);
                run(s1,shineText);
                run(s3,tv_shine_colors);
            }

            private void run(ShineAnimator s, ShineView shine) {
                if(s!=null)
                    s.cancel();
                s=new ShineAnimator();
                s.setShineView(shine);
                s.start();
            }
        });
    }
}
