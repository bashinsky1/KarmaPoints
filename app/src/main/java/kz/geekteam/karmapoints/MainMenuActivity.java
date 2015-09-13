package kz.geekteam.karmapoints;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import kz.geekteam.karmapoints.util.Util;


public class MainMenuActivity extends Activity {
    Button gameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        gameBtn = (Button) findViewById(R.id.startGameBtn);

        Intent intent = getIntent();

        final String json = intent.getStringExtra(Util.APP_JSON);

        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(MainMenuActivity.this, GameActivity.class);
                game.putExtra(Util.APP_JSON, json);
                startActivity(game);
            }
        });
    }

}
