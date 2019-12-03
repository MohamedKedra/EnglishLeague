package com.mohamed.englishleague.Repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mohamed.englishleague.API.Client;
import com.mohamed.englishleague.API.LeagueService;
import com.mohamed.englishleague.DB.AppDao;
import com.mohamed.englishleague.DB.AppDatabase;
import com.mohamed.englishleague.Models.LeagueResponse;
import com.mohamed.englishleague.Models.Player;
import com.mohamed.englishleague.Models.Team;
import com.mohamed.englishleague.Models.TeamResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsRepository {

    LeagueService service;
    AppDatabase instance;
    AppDao dao;
    LiveData<List<Team>> teams;

    public DetailsRepository(Context context){

        service = Client.getService(context);
        instance = AppDatabase.getInstance(context);
        dao = instance.appDao();
        teams = dao.getFavoriteTeams();
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

    public void addTeam(Team team){
        new InsertTask(dao).execute(team);
    }

    public void deleteTeam(Team team){
        new DeleteTask(dao).execute(team);
    }

    public LiveData<List<Team>> getTeams() {

        return teams;
    }

    public class InsertTask extends AsyncTask<Team,Void,Void>{

        private AppDao dao;

        public InsertTask(AppDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            dao.addTeam(teams[0]);
            return null;
        }
    }

    public class DeleteTask extends AsyncTask<Team,Void,Void>{

        private AppDao dao;

        public DeleteTask(AppDao dao){
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            dao.deleteTeam(teams[0]);
            return null;
        }
    }

}