package com.mohamed.englishleague;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface LeagueService {


    @GET("competitions/{id}/teams")
    Call<LeagueResponse> getAllTeams(@Header("X-Auth-Token")String token, @Path("id") int id);

}
