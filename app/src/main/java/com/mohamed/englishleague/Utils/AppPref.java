package com.mohamed.englishleague.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mohamed.englishleague.Models.Team;

import java.util.List;

public class AppPref {

    SharedPreferences preferences;

    public AppPref(Context context){
        preferences = context.getSharedPreferences("appPref",Context.MODE_PRIVATE);
    }

    public void  addTeam(Team team){

        SharedPreferences.Editor editor = preferences.edit();
    }

}
