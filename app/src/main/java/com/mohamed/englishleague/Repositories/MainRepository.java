package com.mohamed.englishleague.Repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mohamed.englishleague.API.Client;
import com.mohamed.englishleague.API.LeagueService;
import com.mohamed.englishleague.Models.LeagueResponse;
import com.mohamed.englishleague.Models.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    LeagueService service;

    public MainRepository(Context context){
        service = Client.getService(context);
    }

    public LiveData<List<Team>> getAllTeams(String token, int leagueId){

        final MutableLiveData<List<Team>> teams = new MutableLiveData<>();
        service.getAllTeams(token,leagueId).enqueue(new Callback<LeagueResponse>() {
            @Override
            public void onResponse(Call<LeagueResponse> call, Response<LeagueResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    teams.setValue(response.body().getTeams());
                }
            }

            @Override
            public void onFailure(Call<LeagueResponse> call, Throwable t) {
                teams.setValue(null);
            }
        });
        return teams;
    }


}