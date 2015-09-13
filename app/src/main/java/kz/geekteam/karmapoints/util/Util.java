package kz.geekteam.karmapoints.util;


import android.content.SharedPreferences;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class Util {

    public static final String APP_PREFERENCES = "settings";
    public static final String APP_LEVEL = "level";
    public static final String APP_SCORE = "score";
    public static final String APP_JSON = "json";

    public static void setPreferences(int lv, int sc, SharedPreferences gamePreferences){
        SharedPreferences.Editor editor = gamePreferences.edit();
        editor.putInt(APP_LEVEL, lv);
        editor.putInt(APP_SCORE, sc);
        editor.apply();

    }
    public static int[] getPreferences(SharedPreferences gamePreferences){
        int[] result = new int[2];
        if(gamePreferences.contains(APP_LEVEL) && gamePreferences.contains(APP_SCORE)){
            result[0]= gamePreferences.getInt(APP_LEVEL, 0);
            result[1]= gamePreferences.getInt(APP_SCORE, 0);
        }

        return result;
    }

    public static void outputPoint(RelativeLayout view, final TextView pointView, int point){

        float x = view.getWidth();
        float y = view.getHeight();

        Random rand = new Random();

        float finalX = rand.nextFloat() * (x-pointView.getWidth());
        float finalY= y / 2;

        pointView.setText("+"+Integer.toString(point));

        pointView.setX(finalX);
        pointView.setY(finalY);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(500);

        final Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);

        pointView.startAnimation(fadeIn);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pointView.startAnimation(fadeOut);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
