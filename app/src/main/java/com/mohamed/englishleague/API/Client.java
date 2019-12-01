package com.mohamed.englishleague.API;

import com.mohamed.englishleague.Utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static LeagueService getService() {

        return new Retrofit.Builder()
                .baseUrl(Constants.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(LeagueService.class);
    }
}
