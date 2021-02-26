package sysmobile.usthb.dz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

public class GameView extends View implements Runnable{

    private Thread thread;

    private boolean isPlaying = true, endGame = false;
    private final int screenX, screenY;
    public static float screenRatioX, screenRatioY;

    private final Paint paint;
    private static Player player;
    public TextView score;
    public RelativeLayout layout;
    private Drawable background = getResources().getDrawable(R.drawable.custom_border_end_game);

    private final Path path;
    private PathMeasure pathMeasure;
    private int iCurStep = 0, animSteps = 15;
    private float[] afP = {0f, 0f}, p1 = {0f, 0f}, p2 = {0f, 0f};
    private float fSegmentLen;


    @RequiresApi(api = Build.VERSION_CODES.M)
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1080f / screenX;
        screenRatioY = 1920f / screenY;

        player = new Player(getResources(), getContext(), 120, 120);
        player.mediaPlayer.setOnCompletionListener(mp -> {
            try {
                pause();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PortalAnimActivity.endGame = true;
            Activity activity = (Activity)getContext();
            Intent intent = new Intent(activity, PortalAnimActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });

        paint = new Paint();
        path = new Path();
        path.moveTo(player.x, player.y);
    }

    @Override
    public void run() {
        while(isPlaying) {
            try {
                update();
                sleep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (iCurStep <= animSteps) {
            pathMeasure.getPosTan(fSegmentLen * iCurStep, afP, null);
            canvas.drawBitmap(player.player, afP[0], afP[1], paint);

            iCurStep++;

            player.x = afP[0];
            player.y = afP[1];

            if (player.x <= - player.width / 3f ||
                    player.y <= - player.height / 3f ||
                    player.x >= screenX - player.width / 3f ||
                    player.y >= screenY - player.height / 3f) {

                layout.setBackground(background);
                abortPathAnimation();
                endGame = true;
                resume();
            }
            else
                invalidate();
        } else {

            iCurStep = 0;
            score.setText("Score " + String.valueOf(Player.score));

            // EndGame
            if (!endGame) {
                // Velocity
                if ((p2[0] > p1[0] && player.vx < 0) || (p2[0] < p1[0] && player.vx > 0))
                    player.vx = -player.vx;

                if ((p2[1] > p1[1] && player.vy < 0) || (p2[1] < p1[1] && player.vy > 0))
                    player.vy = -player.vy;

                resume();
            }
        }
    }


    private void getPath() {

        path.reset();
        path.moveTo(p1[0], p1[1]);
        path.lineTo(p2[0], p2[1]);
        pathMeasure = new PathMeasure(path, false);
        fSegmentLen = pathMeasure.getLength() / animSteps;

        iCurStep = 0;
    }

    private void abortPathAnimation() {
        path.reset();
        pathMeasure = new PathMeasure();
        fSegmentLen = 0;

        iCurStep = animSteps + 1;
    }

    private void update() throws InterruptedException {

        if (endGame)
            endGame();
        else {
            p1[0] = player.x;
            p1[1] = player.y;

            p2[0] = player.x + player.vx;
            p2[1] = player.y + player.vy;

            getPath();
            postInvalidate();

            pause();
        }
    }

    private void sleep() throws InterruptedException {
        Thread.sleep(17);
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() throws InterruptedException {
        isPlaying = false;
        thread.join();
    }

    public void endGame() {

        System.out.println("EndGame");
        if (Player.highScore < Player.score)
            Player.highScore = Player.score;

        player.mediaPlayer.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN && !endGame) {

            p1[0] = player.x;
            p1[1] = player.y;

            p2[0] = event.getX();
            p2[1] = event.getY();

            Player.score++;

            getPath();

            try {
                if (isPlaying)
                    pause();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            invalidate();
        }

        return true;
    }
}
