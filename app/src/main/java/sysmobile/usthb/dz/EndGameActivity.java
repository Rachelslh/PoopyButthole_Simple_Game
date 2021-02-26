package sysmobile.usthb.dz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class EndGameActivity extends AppCompatActivity {

    TextView username, score, highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_end_game);

        // Hooks
        username = findViewById(R.id.username);
        score = findViewById(R.id.score);
        highscore = findViewById(R.id.highscore);

        username.setText(Player.username);
        score.setText("Score " + String.valueOf(Player.score));
        highscore.setText("HighScore " + String.valueOf(Player.highScore));

        findViewById(R.id.replay).setOnClickListener(v -> {
            Player.score= 0;
            PortalAnimActivity.endGame = false;
            startActivity(new Intent(EndGameActivity.this, PortalAnimActivity.class));
        });

    }
}