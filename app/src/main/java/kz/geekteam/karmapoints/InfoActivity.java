package kz.geekteam.karmapoints;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import kz.geekteam.karmapoints.util.LevelResponse;
import kz.geekteam.karmapoints.util.Util;


public class InfoActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_info);

        TableLayout infoTable = (TableLayout) findViewById(R.id.InfoTable);

        Intent intent = getIntent();

        String json = intent.getStringExtra(Util.APP_JSON);

        LevelResponse gson = new Gson().fromJson(json, LevelResponse.class);
        List<LevelResponse> result = gson.results;

        for(LevelResponse res : result){
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            lp.setMargins( (int) getResources().getDimension(R.dimen.paddingLayout),
                    (int) getResources().getDimension(R.dimen.paddingLayout),
                    (int) getResources().getDimension(R.dimen.paddingLayout),
                    (int) getResources().getDimension(R.dimen.paddingLayout));
            row.setLayoutParams(lp);
            TextView level = new TextView(this);
            TextView score = new TextView(this);
            TextView point = new TextView(this);
            level.setText(Integer.toString(res.level));
            score.setText(Integer.toString(res.score));
            point.setText(Integer.toString(res.point));
            row.addView(level);
            row.addView(score);
            row.addView(point);
            row.setGravity(Gravity.CENTER);
            infoTable.addView(row);
        }

    }
}
