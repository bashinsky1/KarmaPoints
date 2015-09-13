package kz.geekteam.karmapoints;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import java.io.IOException;

import kz.geekteam.karmapoints.util.Util;


public class SplashscreenActivity extends Activity {

    ImageView splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        splash = (ImageView) findViewById(R.id.imageKarma);

        final Animation Animate = AnimationUtils.loadAnimation(this, R.anim.routs_karm);
        splash.startAnimation(Animate);

        if(isNetworkAvailable())
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://brainfuck.kz/json/")
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            goToGame(jsonData);
                        } else {
                            Toast.makeText(SplashscreenActivity.this, getString(R.string.ERROR), Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        Log.v("", "Exception caught : ", e);
                    }
                }
            });

        }
        else {
            Toast.makeText(this, getString(R.string.network_unavailable_message), Toast.LENGTH_SHORT).show();

        }

    }

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;

        }
        return isAvailable;
    }

    public void goToGame(String jsonData){
        Intent intent = new Intent(SplashscreenActivity.this, MainMenuActivity.class);
        intent.putExtra(Util.APP_JSON,jsonData);
        startActivity(intent);
        finish();
    }
}
