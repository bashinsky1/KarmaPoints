package kz.geekteam.karmapoints;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import kz.geekteam.karmapoints.util.LevelResponse;
import kz.geekteam.karmapoints.util.Util;


public class GameActivity extends Activity {
    Button picker;
    RelativeLayout responseLayout;
    TextView pointView;
    TextView levelView;
    TextView scoreView;
    SharedPreferences gamePreferences;

    int point;
    int level;
    int score;

    List<LevelResponse> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        picker = (Button) findViewById(R.id.btn_picker);
        responseLayout =(RelativeLayout) findViewById(R.id.response_layout);
        pointView = (TextView) findViewById(R.id.textPoint);
        levelView = (TextView) findViewById(R.id.levelView);
        scoreView = (TextView) findViewById(R.id.scoreView);
        Button infoTable = (Button) findViewById(R.id.btn_infoTable);

        Intent intent = getIntent();

        final String json = intent.getStringExtra(Util.APP_JSON);

        LevelResponse gson = new Gson().fromJson(json, LevelResponse.class);
        result = gson.results;

        gamePreferences = getSharedPreferences(Util.APP_PREFERENCES, Context.MODE_PRIVATE);
        int[] gameInfo = Util.getPreferences(gamePreferences);
        level = gameInfo[0];
        if (level == 0)
            level = 1;

        score = gameInfo[1];
        point = getPoint(level, result);

        levelView.setText(getResources().getString(R.string.level)+level);
        scoreView.setText(getResources().getString(R.string.score)+score);
        picker.setText(getResources().getString(R.string.plus)+Integer.toString(point));



        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result.get(result.size() - 1).score <= score) {
                    new AlertDialog.Builder(GameActivity.this)
                            .setTitle(getResources().getString(R.string.finish))
                            .setMessage(getResources().getString(R.string.refresh))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    CleanAll();
                                }
                            })
                            .show();

                } else {

                    for (LevelResponse res : result) {
                        if (res.level == level) {
                            Util.outputPoint(responseLayout, pointView, point);
                            if (res.score <= score) {
                                level += 1;
                                point = getPoint(level, result);
                                picker.setText(getResources().getString(R.string.plus)+Integer.toString(point));
                                levelView.setText(getResources().getString(R.string.level) + level);
                                break;
                            }
                        }
                    }
                    score += point;
                    scoreView.setText(getResources().getString(R.string.score)+score);
                }
            }
        });
        infoTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameActivity.this, InfoActivity.class);
                i.putExtra(Util.APP_JSON,json);
                startActivity(i);
            }
        });
    }



    @Override
    protected void onStop() {
        super.onStop();
        Util.setPreferences(level, score, gamePreferences);
    }

    public void CleanAll(){
        gamePreferences.edit().clear();
        level = 0;
        score = 0;
        levelView.setText(getResources().getString(R.string.level)+level);
        scoreView.setText(getResources().getString(R.string.score)+score);
    }
    public int getPoint(int level, List<LevelResponse> result){
        int point = 0;
        for (LevelResponse res: result){
                if(res.level == level)
                    return res.point;
        }
        return point;
    };


}
