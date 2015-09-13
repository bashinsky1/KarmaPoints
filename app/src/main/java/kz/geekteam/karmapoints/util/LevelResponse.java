package kz.geekteam.karmapoints.util;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LevelResponse {

    public List<LevelResponse> results;

    @SerializedName("level")
    public int level;

    @SerializedName("score")
    public int score;

    @SerializedName("point")
    public int point;

}
