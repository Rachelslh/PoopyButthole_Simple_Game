package sysmobile.usthb.dz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import pl.droidsonroids.gif.GifImageView;

public class PortalAnimActivity extends AppCompatActivity {

    Animation upAnim, downAnim;
    Animation.AnimationListener animationListener;
    GifImageView gifImageView;

    public static boolean endGame = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_portal_anim);

        // Animations
        upAnim = AnimationUtils.loadAnimation(this, R.anim.scale_up_animation);
        downAnim = AnimationUtils.loadAnimation(this, R.anim.scale_down_animation);

        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (endGame) {
                    startActivity(new Intent(PortalAnimActivity.this, EndGameActivity.class));
                    gifImageView.setScaleX((float) 3.5);
                    gifImageView.setScaleY((float) 3.5);
                }
                else {
                    startActivity(new Intent(PortalAnimActivity.this, GameActivity.class));
                    gifImageView.setScaleX(0);
                    gifImageView.setScaleY(0);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        };

        downAnim.setAnimationListener(animationListener);
        upAnim.setAnimationListener(animationListener);

        // Hooks
        gifImageView = findViewById(R.id.gif);

        if (endGame)
            gifImageView.setAnimation(upAnim);
        else
            gifImageView.setAnimation(downAnim);
    }
}