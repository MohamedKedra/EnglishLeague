package com.mohamed.englishleague.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.mohamed.englishleague.API.Client;
import com.mohamed.englishleague.API.LeagueService;
import com.mohamed.englishleague.Adapters.TeamAdapter;
import com.mohamed.englishleague.Models.Team;
import com.mohamed.englishleague.Utils.Constants;
import com.mohamed.englishleague.Models.LeagueResponse;
import com.mohamed.englishleague.R;
import com.mohamed.englishleague.ViewModels.MainViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TeamAdapter adapter;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        displayAllTeams();
    }

    public void initViews(){
        recyclerView = findViewById(R.id.rv_teams);
        progressBar = findViewById(R.id.pb_progressbar);
        layoutManager = new GridLayoutManager(this,2);
    }

    public void displayAllTeams(){
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getAllTeams().observe(this,observer);
    }

    Observer<List<Team>> observer = new Observer<List<Team>>() {
        @Override
        public void onChanged(List<Team> teams) {
            progressBar.setVisibility(View.GONE);
            if (teams != null){
                adapter = new TeamAdapter(teams,MainActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
            }
        }
    };

}