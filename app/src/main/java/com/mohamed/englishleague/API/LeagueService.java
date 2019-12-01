package com.mohamed.englishleague.API;

import androidx.lifecycle.LiveData;

import com.mohamed.englishleague.Models.LeagueResponse;
import com.mohamed.englishleague.Models.TeamResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface LeagueService {


    @GET("competitions/{id}/teams")
    Call<LeagueResponse> getAllTeams(@Header("X-Auth-Token")String token, @Path("id") int id);

    @GET("teams/{id}")
    Call<TeamResponse> getTeamPlayers(@Header("X-Auth-Token") String token, @Path("id") int id);

}
