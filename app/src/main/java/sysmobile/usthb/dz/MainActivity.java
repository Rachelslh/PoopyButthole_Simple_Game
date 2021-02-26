package sysmobile.usthb.dz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.google.android.material.textfield.*;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // VARS
    Animation topAnim, bottomAnim;
    ImageView image;
    TextInputLayout usernameLayout;
    Button getIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        // Hooks
        image = findViewById(R.id.imageView);
        usernameLayout = findViewById(R.id.usernameLayout);
        getIN = findViewById(R.id.play);

        image.setAnimation(topAnim);
        usernameLayout.setAnimation(bottomAnim);
        getIN.setAnimation(bottomAnim);

        findViewById(R.id.play).setOnClickListener(v -> {
            Player.username = usernameLayout.getEditText().getText().toString();
            startActivity(new Intent(MainActivity.this, PortalAnimActivity.class));
        });
    }
}