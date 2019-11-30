package com.mohamed.englishleague;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TeamAdapter adapter;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_teams);
        progressBar = findViewById(R.id.pb_progressbar);
        layoutManager = new GridLayoutManager(this,2);

        LeagueService service = Client.getService();
        service.getAllTeams(Constants.Token,Constants.LeagueId).enqueue(new Callback<LeagueResponse>() {
            @Override
            public void onResponse(Call<LeagueResponse> call, Response<LeagueResponse> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    adapter = new TeamAdapter(response.body().getTeams(),MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(Call<LeagueResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Log.d("res",t.getMessage());

                System.out.println("Res : "+t);
            }
        });

    }
}
