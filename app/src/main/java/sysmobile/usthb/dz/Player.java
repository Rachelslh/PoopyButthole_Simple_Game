package sysmobile.usthb.dz;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

import static sysmobile.usthb.dz.GameView.screenRatioX;
import static sysmobile.usthb.dz.GameView.screenRatioY;

public class Player {

    public static String username;
    float x, y, vx, vy;
    int width, height;
    public static int score = 0, highScore = 0;
    Bitmap player;
    MediaPlayer mediaPlayer;

    public Player(Resources res, Context context, int vx, int vy) {

        player = BitmapFactory.decodeResource(res, R.drawable.poopy0);
        mediaPlayer = MediaPlayer.create(context, R.raw.ooooweee);

        width = player.getWidth();
        height = player.getHeight();

        this.vx = vx;
        this.vy = vy;

        width /= 4;
        height /= 4;

        width *= (int) screenRatioX;
        height *= (int) screenRatioY;

        player = Bitmap.createScaledBitmap(player, width, height, false);

        x = (int) (30 * screenRatioX);
        y = 30;
    }

    Bitmap getPlayer() {
        return player;
    }
}
