package com.mohamed.englishleague.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mohamed.englishleague.API.Client;
import com.mohamed.englishleague.API.LeagueService;
import com.mohamed.englishleague.Models.LeagueResponse;
import com.mohamed.englishleague.Models.Player;
import com.mohamed.englishleague.Models.TeamResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsRepository {

    LeagueService service;

    public DetailsRepository(){

        service = Client.getService();
    }

    public LiveData<List<Player>> getTeamPlayers(String token , int id){

        final MutableLiveData<List<Player>> players = new MutableLiveData<>();
        service.getTeamPlayers(token,id).enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(Call<TeamResponse> call, Response<TeamResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    players.setValue(response.body().getSquad());
                }
            }

            @Override
            public void onFailure(Call<TeamResponse> call, Throwable t) {
                players.setValue(null);
            }
        });

        return players;
    }
}